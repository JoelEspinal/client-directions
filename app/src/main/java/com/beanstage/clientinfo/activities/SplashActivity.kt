package com.beanstage.clientinfo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beanstage.clientinfo.R
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ClientInfo)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        launcheClientsActivity()
    }

     fun launcheClientsActivity() {
        val intent = Intent(this, ClientActivity::class.java)
        runBlocking {
            delay(5000)
        }

         startActivity(intent)
         finish()
    }
}