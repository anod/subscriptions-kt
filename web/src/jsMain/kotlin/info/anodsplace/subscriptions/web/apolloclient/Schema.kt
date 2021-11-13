package info.anodsplace.subscriptions.web.apolloclient

external interface Subscription {
    val id: Int
    val objectId: String
    val name: String
    val link: String
    val created: String
    val userId: Int
}

external interface GetSubscriptions {
    val subscriptions: dynamic
}
