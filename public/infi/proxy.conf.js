const PROXY_CONFIG = [
    {
        context: [
            "/login-form",
            "/dashboard",
            "/header",
            "/footer"
        ],
        target: "http://localhost:3000",
        secure: false
    }
]

module.exports = PROXY_CONFIG;