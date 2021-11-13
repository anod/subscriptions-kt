package info.anodsplace.subscriptions.web.apolloclient

import kotlin.js.Promise

external interface Error

@JsModule("@apollo/client/core")
@JsNonModule
external class ApolloClientModule {

    companion object {
        // https://github.com/graphql/graphql-js/blob/main/src/language/ast.ts
        fun gql(literals: String, vararg args: Any): Any
    }

    class InMemoryCache

    interface ApolloClientOptions {
        var uri: String
        var credentials: String?
        var headers: Headers
        var cache: Any
//    link?: ApolloLink;
//    ssrForceFetchDelay?: number;
//    ssrMode?: boolean;
//    connectToDevTools?: boolean;
//    queryDeduplication?: boolean;
//    defaultOptions?: DefaultOptions;
//    assumeImmutableResults?: boolean;
//    resolvers?: Resolvers | Resolvers[];
//    typeDefs?: string | string[] | DocumentNode | DocumentNode[];
//    fragmentMatcher?: FragmentMatcher;
//    name?: string;
//    version?: string;
    }

    interface QueryContext {
        var headers: Headers
    }

    interface Headers {
        var Authorization: String
    }

    interface QueryOptions {
        var query: Any
        var context: QueryContext
    }

    interface ApolloError: Error {
        val message : String;
        val graphQLErrors : Array<Any>
        val clientErrors : Array<Error>
        val networkError : Any?;
        val extraInfo : Any
    }

    class QueryResult<T> {
        val data: T
        val loading: Boolean
        val networkStatus: Int
        val errors: Any?
        val error: ApolloError?
        val partial: Boolean?
    }

    class ApolloClient(options: ApolloClientOptions) {
        fun <T> query(options: QueryOptions): Promise<T>
    }
}
