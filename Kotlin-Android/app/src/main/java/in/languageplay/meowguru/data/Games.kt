package `in`.languageplay.meowguru.data

/**
 * Poori LanguagePlay.in game ecosystem ka static index.
 * Ye games server pe already WebGL/HTML5 me bane hue hain (soloroadio, grammar,
 * sroad, lc, verb, metro sub-apps + examica.in kids arcade). App unhe native
 * WebView player me kholta hai — koi game-logic dobara nahi likha, bas
 * app ke navigation / theme / XP flow ke andar la diya gaya hai.
 */
data class GameItem(
    val emoji: String,
    val title: String,
    val subtitle: String,
    val url: String
)

data class GameCategory(
    val emoji: String,
    val name: String,
    val tagline: String,
    val hubUrl: String,
    val games: List<GameItem>
)

object Games {

    val ROAD_SERIES = GameCategory(
        emoji = "🚗",
        name = "Road Series",
        tagline = "11 3D driving games · gaadi chalao, sahi block se takrao · koi game over nahi",
        hubUrl = "https://languageplay.in/soloroadio/",
        games = listOf(
            GameItem("1️⃣", "Number Road", "0–9 · Eng + Hindi", "https://languageplay.in/soloroadio/games/numbers.html"),
            GameItem("🔤", "Alphabet Road", "A–Z · caps/small", "https://languageplay.in/soloroadio/games/alphabet.html"),
            GameItem("🪷", "वर्णमाला Road", "स्वर + व्यंजन (49)", "https://languageplay.in/soloroadio/games/varnamala.html"),
            GameItem("🥷", "बारहखड़ी Road", "31 × 12 मात्रा", "https://languageplay.in/soloroadio/games/barakhadi.html"),
            GameItem("💯", "Hundred Road", "1–100 · 1 2 3 / १ २ ३", "https://languageplay.in/soloroadio/games/hundred.html"),
            GameItem("அ", "தமிழ் Road", "உயிர் · மெய்", "https://languageplay.in/soloroadio/games/tamil.html"),
            GameItem("🐘", "Animal Road", "जानवर + आवाज़", "https://languageplay.in/soloroadio/games/animals.html"),
            GameItem("🥭", "Fruit & Veg Road", "juice splash + sort", "https://languageplay.in/soloroadio/games/fruits.html"),
            GameItem("🎨", "Colour Road", "रंग सीखो", "https://languageplay.in/soloroadio/games/colours.html"),
            GameItem("🔺", "Shape Road", "2D + 3D solids", "https://languageplay.in/soloroadio/games/shapes.html"),
            GameItem("👁️", "Body Parts Road", "शरीर के अंग", "https://languageplay.in/soloroadio/games/body.html"),
        )
    )

    val GRAMMAR_BOOK = GameCategory(
        emoji = "📖",
        name = "Grammar Book",
        tagline = "10 3D driving games · Articles se Punctuation tak",
        hubUrl = "https://languageplay.in/grammar/",
        games = listOf(
            GameItem("📝", "Articles", "a / an / the · forest drive", "https://languageplay.in/grammar/games/articles.html"),
            GameItem("🏙️", "Prepositions", "in / on / under · neon city", "https://languageplay.in/grammar/games/prepositions.html"),
            GameItem("🌊", "Modals", "can / could / must · sea beach", "https://languageplay.in/grammar/games/modals.html"),
            GameItem("⏳", "Tenses", "is/was · has/have · will", "https://languageplay.in/grammar/games/tenses.html"),
            GameItem("⛰️", "Pronouns", "I/me/my · he/him/his · hills", "https://languageplay.in/grammar/games/pronouns.html"),
            GameItem("🏁", "Comparatives", "big/bigger/biggest · race track", "https://languageplay.in/grammar/games/comparatives.html"),
            GameItem("🐄", "Plurals", "cat/cats · box/boxes · farm", "https://languageplay.in/grammar/games/plurals.html"),
            GameItem("🔎", "Question Words", "who/what/when · mansion", "https://languageplay.in/grammar/games/question-words.html"),
            GameItem("🌉", "Conjunctions", "and/but/or/because · river", "https://languageplay.in/grammar/games/conjunctions.html"),
            GameItem("💥", "Punctuation", ". , ? ! · comic-book city", "https://languageplay.in/grammar/games/punctuation.html"),
        )
    )

    val KIDS_TRAIL = GameCategory(
        emoji = "🐾",
        name = "Kids Trail",
        tagline = "5 games, ek trail · tracing se barakhadi tak · chhote bacchon ke liye",
        hubUrl = "https://examica.in/kidgames",
        games = listOf(
            GameItem("✍️", "Tracing Star", "A B C · 1 2 3 · अ आ इ — ungli se likho", "https://examica.in/trace"),
            GameItem("🍎", "ABC Ninja", "A → Z · 26 letters · English voice", "https://examica.in/kidgames/abc-ninja.html"),
            GameItem("🍒", "123 Ninja", "1 → 10 counting · fruit math", "https://examica.in/kidgames/123-ninja.html"),
            GameItem("🪷", "वर्णमाला Ninja", "49 अक्षर · हिन्दी आवाज़", "https://examica.in/kidgames/varnamala-ninja.html"),
            GameItem("🥷", "बारहखड़ी Ninja", "33 अक्षर × 12 मात्राएँ", "https://examica.in/barakhadi-ninja/"),
        )
    )

    // Single-game categories — koi sub-arcade nahi, seedha game
    val SENTENCE_ROAD = GameItem("🛣️", "Sentence Road", "120 daily-use sentences · 12 topics · word-blocks sahi order me thoko", "https://languageplay.in/sroad/")
    val LANGUAGE_CARDS = GameItem("🎴", "Language Cards", "Word ya sentence likho · WhatsApp/Telegram card banao", "https://languageplay.in/lc/")
    val DAILY_VERBS = GameItem("🗣️", "Daily Hindi Verbs", "100+ roz-marra verbs · tap karke seekho · TTS ke saath", "https://languageplay.in/verb/")
    val METRO_ABC = GameItem("🚈", "Metro ABC Adventure", "3D metro train chalao · A–Z stations · umar 3–8", "https://languageplay.in/metro/")

    val singles = listOf(SENTENCE_ROAD, LANGUAGE_CARDS, DAILY_VERBS, METRO_ABC)
    val categories = listOf(ROAD_SERIES, GRAMMAR_BOOK, KIDS_TRAIL)
}
