package `in`.languageplay.meowguru.data

data class Lang(val name: String, val native: String, val tts: String, val indian: Boolean)

object Langs {

    val INDIAN = listOf(
        Lang("English", "English", "en-IN", true),
        Lang("Hindi", "हिन्दी", "hi-IN", true),
        Lang("Assamese", "অসমীয়া", "as-IN", true),
        Lang("Bengali", "বাংলা", "bn-IN", true),
        Lang("Bodo", "बड़ो", "hi-IN", true),
        Lang("Dogri", "डोगरी", "hi-IN", true),
        Lang("Gujarati", "ગુજરાતી", "gu-IN", true),
        Lang("Kannada", "ಕನ್ನಡ", "kn-IN", true),
        Lang("Kashmiri", "کٲشُر", "ur-IN", true),
        Lang("Konkani", "कोंकणी", "mr-IN", true),
        Lang("Maithili", "मैथिली", "hi-IN", true),
        Lang("Malayalam", "മലയാളം", "ml-IN", true),
        Lang("Manipuri", "মেইতেই", "bn-IN", true),
        Lang("Marathi", "मराठी", "mr-IN", true),
        Lang("Nepali", "नेपाली", "ne-NP", true),
        Lang("Odia", "ଓଡ଼ିଆ", "or-IN", true),
        Lang("Punjabi", "ਪੰਜਾਬੀ", "pa-IN", true),
        Lang("Sanskrit", "संस्कृतम्", "hi-IN", true),
        Lang("Santali", "ᱥᱟᱱᱛᱟᱲᱤ", "hi-IN", true),
        Lang("Sindhi", "سنڌي", "ur-IN", true),
        Lang("Tamil", "தமிழ்", "ta-IN", true),
        Lang("Telugu", "తెలుగు", "te-IN", true),
        Lang("Urdu", "اردو", "ur-IN", true)
    )

    val WORLD = listOf(
        Lang("Spanish", "Español", "es-ES", false),
        Lang("French", "Français", "fr-FR", false),
        Lang("German", "Deutsch", "de-DE", false),
        Lang("Italian", "Italiano", "it-IT", false),
        Lang("Portuguese", "Português", "pt-BR", false),
        Lang("Russian", "Русский", "ru-RU", false),
        Lang("Japanese", "日本語", "ja-JP", false),
        Lang("Korean", "한국어", "ko-KR", false),
        Lang("Chinese (Mandarin)", "中文", "zh-CN", false),
        Lang("Arabic", "العربية", "ar-SA", false),
        Lang("Turkish", "Türkçe", "tr-TR", false),
        Lang("Dutch", "Nederlands", "nl-NL", false),
        Lang("Greek", "Ελληνικά", "el-GR", false),
        Lang("Hebrew", "עברית", "he-IL", false),
        Lang("Thai", "ไทย", "th-TH", false),
        Lang("Vietnamese", "Tiếng Việt", "vi-VN", false),
        Lang("Indonesian", "Bahasa Indonesia", "id-ID", false),
        Lang("Malay", "Bahasa Melayu", "ms-MY", false),
        Lang("Filipino", "Filipino", "fil-PH", false),
        Lang("Swahili", "Kiswahili", "sw-KE", false),
        Lang("Polish", "Polski", "pl-PL", false),
        Lang("Ukrainian", "Українська", "uk-UA", false),
        Lang("Czech", "Čeština", "cs-CZ", false),
        Lang("Swedish", "Svenska", "sv-SE", false),
        Lang("Danish", "Dansk", "da-DK", false),
        Lang("Norwegian", "Norsk", "nb-NO", false),
        Lang("Finnish", "Suomi", "fi-FI", false),
        Lang("Hungarian", "Magyar", "hu-HU", false),
        Lang("Romanian", "Română", "ro-RO", false),
        Lang("Persian", "فارسی", "fa-IR", false),
        Lang("Nepali (Nepal)", "नेपाली", "ne-NP", false),
        Lang("Sinhala", "සිංහල", "si-LK", false),
        Lang("Burmese", "မြန်မာ", "my-MM", false),
        Lang("Khmer", "ខ្មែរ", "km-KH", false),
        Lang("Zulu", "isiZulu", "zu-ZA", false),
        Lang("Afrikaans", "Afrikaans", "af-ZA", false)
    )

    val ALL = INDIAN + WORLD

    fun byName(n: String): Lang? = ALL.firstOrNull { it.name == n }
    fun ttsOf(n: String): String = byName(n)?.tts ?: "en-IN"
}
