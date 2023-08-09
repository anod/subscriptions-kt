import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.writeTo
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.DotenvEntry
import java.io.File

class DotEnvProcessor(
    environment: SymbolProcessorEnvironment,
) : SymbolProcessor {
    private val codeGenerator = environment.codeGenerator
    private val options = environment.options
    private val logger: KSPLogger = DelegateLogger(environment.logger)

    companion object {
        const val optPath = "info.anodsplace.dotenv.path"
        const val optIncludeKeys = "info.anodsplace.dotenv.includeKeys"
        const val defaultFile = ".env"
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val file = resolveFile(options[optPath])
        logger.info("Resolved file: $file")
        val entries = Dotenv.configure()
            .directory(file.absolutePath)
            .load()
            .entries(Dotenv.Filter.DECLARED_IN_ENV_FILE)
        genFile(
            entries = entries
        ).writeTo(codeGenerator, Dependencies(true))
        return emptyList()
    }

    private fun resolveFile(configPath: String?): File {
        if (configPath.isNullOrEmpty()) {
            return File(defaultFile)
        }

        val file = File(configPath)
        if (file.isFile) {
            return file
        } else if (file.isDirectory) {
            return File(file, defaultFile)
        }
        return File(defaultFile)
    }

    private fun genFile(entries: Set<DotenvEntry>): FileSpec {
        val properties = entries.map { entry ->
            logger.info("  ${entry.key}=${entry.value}")
            PropertySpec.builder(entry.key, String::class)
                .mutable(mutable = false)
                .addModifiers(KModifier.PUBLIC)
                .initializer("%S", entry.value)
                .build()
        }

        return FileSpec.builder("info.anodsplace.dotenv.generated", "DotEnv")
            .addType(
                TypeSpec.objectBuilder("DotEnv")
                    .addProperties(properties)
                .build()
            )
            .build()
    }
}

private class DelegateLogger(val delegate: KSPLogger) : KSPLogger {
    var hasError = false
    override fun error(message: String, symbol: KSNode?) {
        hasError = true
        delegate.error(message, symbol)
    }
    override fun exception(e: Throwable) {
        hasError = true
        delegate.exception(e)
    }

    override fun info(message: String, symbol: KSNode?) {
        delegate.info(message, symbol)
    }

    override fun logging(message: String, symbol: KSNode?) {
        delegate.logging(message, symbol)
    }

    override fun warn(message: String, symbol: KSNode?) {
        delegate.warn(message, symbol)
    }

    private fun prefix(message: String) = "[info.anodsplace.dotenv] $message"
}

class DotEnvProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return DotEnvProcessor(environment)
    }
}
