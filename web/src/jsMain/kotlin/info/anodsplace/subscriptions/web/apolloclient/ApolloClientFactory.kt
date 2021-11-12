package info.anodsplace.subscriptions.web.apolloclient

@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
fun ApolloClientOptions(
    url: String
): ApolloClientModule.ApolloClientOptions = (js("{}") as ApolloClientModule.ApolloClientOptions).apply {
    uri = url
    cache = ApolloClientModule.InMemoryCache()
}

@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
fun QueryOptions(
    query: Any,
    token: String
): ApolloClientModule.QueryOptions = (js("{}") as ApolloClientModule.QueryOptions).apply {
    this.query = query
    this.context = (js("{}") as ApolloClientModule.QueryContext).apply {
        headers = (js("{}") as ApolloClientModule.Headers).apply {
            Authorization = "Bearer $token"
        }
    }
}


fun createApolloClient(url: String): ApolloClientModule.ApolloClient {
    return ApolloClientModule.ApolloClient(ApolloClientOptions(url = url))
}