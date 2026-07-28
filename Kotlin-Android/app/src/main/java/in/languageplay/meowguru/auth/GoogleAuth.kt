package `in`.languageplay.meowguru.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import `in`.languageplay.meowguru.BuildConfig
import `in`.languageplay.meowguru.data.GoogleReq
import `in`.languageplay.meowguru.data.GoogleRes
import `in`.languageplay.meowguru.data.Net
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

/**
 * Google Sign-In (Credential Manager — naya, recommended tarika).
 *
 * ZAROORI SETUP:
 * 1. Google Cloud Console → Credentials → naya OAuth Client ID banao, type = ANDROID
 *    - Package: in.languageplay.meowguru
 *    - SHA-1: `./gradlew signingReport` se lo (debug + release dono add karo)
 * 2. BuildConfig.GOOGLE_WEB_CLIENT_ID me WEB client id hi rehne do (ye sahi hai —
 *    Android app WEB client id maangta hai, aur ID token usi ke naam par banta hai).
 * 3. Server ke google-auth.php me aud check pehle se WEB client id se hota hai → chalega.
 */
object GoogleAuth {

    suspend fun signIn(ctx: Context, currentUserKey: String): Result<GoogleRes> {
        return runCatching {
            val option = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)   // pehli baar wale bhi dikhein
                .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                .setAutoSelectEnabled(false)
                .build()

            val req = GetCredentialRequest.Builder()
                .addCredentialOption(option)
                .build()

            val result = CredentialManager.create(ctx).getCredential(ctx, req)
            val cred = result.credential

            val idToken = when {
                cred is CustomCredential &&
                        cred.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
                    GoogleIdTokenCredential.createFrom(cred.data).idToken
                }
                else -> throw IllegalStateException("Google credential nahi mila")
            }

            // wahi endpoint jo web app use karta hai — koi backend change nahi
            val res = Net.api.google(GoogleReq(credential = idToken, user_key = currentUserKey))
            if (!res.ok) throw IllegalStateException(res.error ?: "Google verify fail")
            res
        }
    }
}
