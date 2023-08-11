
fun String.camelcase(): String {
    // https://hexdocs.pm/dotenvy/dotenv-file-format.html
    val lowercase = this.lowercase()
    val result = StringBuilder(lowercase.length)
    var underscore = false
    for (i in lowercase.indices) {
        if (lowercase[i] == '_') {
            underscore = true
        } else {
            if (underscore) {
                result.append(lowercase[i].uppercaseChar())
                underscore = false
            } else {
                result.append(lowercase[i])
            }
        }
    }
    return result.toString()
}