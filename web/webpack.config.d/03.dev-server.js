// wrap is useful, because declaring variables in module can be already declared
// module creates own lexical environment
;(function (config) {
    const shouldRunServer = config.mode !== "production";
    if (shouldRunServer) {
        config.devServer = config.devServer || {}
        config.devServer.port = 9091;
        config.devServer.proxy = {
            '/': {
                target: 'http://localhost:9090',
                secure: false,
                bypass: function (req, res, proxyOptions) {
                    if (req.headers.accept.indexOf('.js') !== -1) {
                        return req.headers.accept;
                    }
                }
            }
        }
    }
})(config);