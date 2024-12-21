package com.android.localnotificationtraining

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.localnotificationtraining.Constants.CHANNEL_ID
import com.android.localnotificationtraining.Constants.CHANNEL_NAME
import com.android.localnotificationtraining.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendNotification.setOnClickListener {
            counter++
            binding.btnSendNotification.text = counter.toString()
            if(counter %5 ==0){
                startNotification()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startNotification(){
        val builder = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT)
            val manager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

            builder.apply {
                setSmallIcon(R.drawable.small_icon)
                setContentTitle("Local Notification")
                setContentText("${counter}")
            }

        } else {
            builder.apply {
                setSmallIcon(R.drawable.small_icon)
                setContentTitle("Local Notification")
                setContentText("${counter}")
                setPriority(NotificationCompat.PRIORITY_DEFAULT)
            }
        }

        val notificationMangerCompat = NotificationManagerCompat.from(this@MainActivity)
        notificationMangerCompat.notify(1,builder.build())
    }
}