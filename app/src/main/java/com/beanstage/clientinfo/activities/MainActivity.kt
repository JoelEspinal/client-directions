package com.beanstage.clientinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beanstage.clientinfo.ui.client.ClientFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ClientFragment.newInstance())
                .commitNow()
        }
    }
}