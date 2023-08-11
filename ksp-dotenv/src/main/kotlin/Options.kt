
data class Options(
    val path: String,
    val allowedKeys: Collection<String>,
    val packageName: String,
    val className: String,
    val camelCase: Boolean,
) {
    companion object {
        const val optPath = "info.anodsplace.dotenv.path"
        const val optAllowedKeys = "info.anodsplace.dotenv.allowedKeys"
        const val optPackage = "info.anodsplace.dotenv.package"
        const val optClass = "info.anodsplace.dotenv.class"
        const val optCamelCase = "info.anodsplace.dotenv.camelCase"

        const val defaultFile = ".env"
        const val defaultPackage = "info.anodsplace.dotenv.generated"
        const val defaultClass = "DotEnv"
    }

    constructor(options: Map<String, String>) : this(
        path = options[optPath]?.ifEmpty { defaultFile } ?: defaultFile,
        allowedKeys = options[optAllowedKeys]?.split(";")?.map { it.trim() } ?: emptyList(),
        packageName = options[optPackage] ?: defaultPackage,
        className = options[optClass] ?: defaultClass,
        camelCase = options[optCamelCase]?.lowercase() == "true",
    )
}