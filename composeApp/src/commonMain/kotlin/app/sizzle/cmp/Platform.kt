package app.sizzle.cmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform