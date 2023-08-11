package info.anodsplace.subscriptions

import info.anodsplace.subscriptions.app.Config

class Config(
    override val graphQlEndpoint: String,
    override val serverEndpoint: String,
    override val env: String
) : Config {
    constructor() : this(
        graphQlEndpoint = DotEnv.hasuraGraphqlEndpoint,
        serverEndpoint = DotEnv.sbsServerEndpoint,
        env = DotEnv.sbsEnv
    )
}

