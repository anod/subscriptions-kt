package info.anodsplace.subscriptions

import info.anodsplace.subscriptions.app.Config

class DesktopConfig(
    override val graphQlEndpoint: String,
    override val serverEndpoint: String,
    override val env: String
) : Config {
    constructor() : this(
        graphQlEndpoint = DotEnvClient.hasuraGraphqlEndpoint,
        serverEndpoint = DotEnvClient.sbsServerEndpoint,
        env = DotEnvClient.sbsEnv
    )
}

