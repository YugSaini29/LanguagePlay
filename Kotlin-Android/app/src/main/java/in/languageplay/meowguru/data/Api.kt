package `in`.languageplay.meowguru.data

import `in`.languageplay.meowguru.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

/* ============ REQUEST / RESPONSE MODELS ============
 * Ye bilkul wahi JSON shape hai jo aapke PHP APIs already bhejte/lete hain.
 * Backend me koi change nahi karna pada — sirf fcm.php naya hai.
 */

data class ChatReq(
    val user_key: String,
    val target: String,
    val source: String,
    val level: String,
    val history: List<Turn>,
    val text: String
)

data class Turn(val role: String, val content: String)

/** chat.php ka reply payload — {"ok":true,"data":{...}} me "data" ke andar hota hai */
data class ChatData(
    val reply_speak: String = "",
    val reply_roman: String = "",
    val reply_source: String = "",
    val reply_english: String = "",
    val user_in_target: String = "",
    val user_roman: String = "",
    val user_english: String = "",
    val corrected_sentence: String = "",
    val mini_tip: String = "",
    val praise_level: Int = 2,
    val reminder_saved: Reminder? = null,
    val reminder_offer: Boolean = false
)

data class ChatRes(
    val ok: Boolean = false,
    val error: String? = null,
    val data: ChatData? = null
)

data class Reminder(val title: String = "", val date: String = "")

data class QuizReq(
    val action: String = "gen",
    val target: String,
    val source: String,
    val level: String = "beginner",
    val avoid: List<String> = emptyList()
)

data class QuizAward(
    val action: String = "award",
    val user_key: String,
    val points: Int
)

data class QuizRes(val ok: Boolean = false, val error: String? = null, val quiz: Quiz? = null)

data class Quiz(
    val question: String = "",
    val options: List<QuizOpt> = emptyList(),
    val correct: Int = 0,
    val explain: String = ""
)

data class QuizOpt(val t: String = "", val r: String = "")

data class LbReq(val user_key: String)
data class LbRes(val ok: Boolean = false, val top: List<LbRow> = emptyList(), val me: LbMe? = null)
data class LbRow(val rank: Int = 0, val name: String = "", val xp: Int = 0, val streak: Int = 0)
data class LbMe(val rank: Int = 0, val xp: Int = 0)

data class GoogleReq(val credential: String, val user_key: String)
data class GoogleRes(
    val ok: Boolean = false,
    val error: String? = null,
    val user_key: String = "",
    val has_profile: Boolean = false,
    val name: String = "",
    val email: String = "",
    val picture: String = ""
)

data class LinkReq(val action: String = "code", val user_key: String)
data class LinkRes(val ok: Boolean = false, val code: String = "", val bot: String = "")

data class FcmReq(val user_key: String, val token: String, val platform: String = "android")
data class OkRes(val ok: Boolean = false, val error: String? = null)

/* ============ RETROFIT ============ */

interface MeowApi {
    @POST("chat.php")
    suspend fun chat(@Body body: ChatReq): ChatRes

    @POST("quiz.php")
    suspend fun quiz(@Body body: QuizReq): QuizRes

    @POST("quiz.php")
    suspend fun award(@Body body: QuizAward): OkRes

    @POST("leaderboard.php")
    suspend fun leaderboard(@Body body: LbReq): LbRes

    @POST("google-auth.php")
    suspend fun google(@Body body: GoogleReq): GoogleRes

    @POST("link.php")
    suspend fun linkCode(@Body body: LinkReq): LinkRes

    @POST("fcm.php")
    suspend fun registerFcm(@Body body: FcmReq): OkRes
}

object Net {
    val api: MeowApi by lazy {
        val log = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
            else HttpLoggingInterceptor.Level.NONE
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)   // LLM thoda time leta hai
            .addInterceptor(log)
            .build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MeowApi::class.java)
    }
}
