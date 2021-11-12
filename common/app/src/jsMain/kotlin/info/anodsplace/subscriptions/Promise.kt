package info.anodsplace.subscriptions

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlin.js.Promise

fun <T : Any> Promise<T>.asFlow(): Flow<T> = flow {
    val channel = Channel<T>(Channel.CONFLATED)

    then(
        onFulfilled = { value ->
            channel.trySend(value)
            channel.close()
        },
        onRejected = {
            channel.close(it)
        }
    )

    for (item in channel) {
        emit(item)
    }
}

suspend fun <T : Any> Promise<T>.single() = this.asFlow().single()