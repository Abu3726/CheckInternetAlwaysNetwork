package com.conamobile.checkinternetalwaysnetwork

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("StaticFieldLeak")
lateinit var textChecker: TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        textChecker = findViewById(R.id.textCheck)

        backThread()
    }


    private fun backThread() {
        val thread: Thread = object : Thread() {
            @SuppressLint("SetTextI18n")
            override fun run() {
                try {
                    while (true) {
                        runOnUiThread {
                            if (isInternetAvailable())
                                textChecker.text = "Online"
                            else
                                textChecker.text = "Offline"
                        }
                        sleep(3000)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        thread.start()
    }

    private fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }
}