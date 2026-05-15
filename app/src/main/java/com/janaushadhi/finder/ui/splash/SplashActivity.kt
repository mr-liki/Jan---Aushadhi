package com.janaushadhi.finder.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.janaushadhi.finder.R
import com.janaushadhi.finder.ui.main.MainActivity
import com.janaushadhi.finder.utils.NotificationUtils
import com.janaushadhi.finder.worker.ReminderWorker

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize notification channels
        NotificationUtils.createNotificationChannels(this)

        // Schedule reminder worker
        ReminderWorker.schedule(this)

        // Navigate to main activity after 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}
