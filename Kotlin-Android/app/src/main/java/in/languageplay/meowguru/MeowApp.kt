package `in`.languageplay.meowguru

import android.app.Application
import `in`.languageplay.meowguru.notif.Notifs

class MeowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Notifs.createChannels(this)
    }
}
