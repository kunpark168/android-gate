package com.anhtam.gate9.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.fragment.app.viewModels
import com.anhtam.domain.v2.Post
import com.anhtam.gate9.R
import com.anhtam.gate9.config.Config
import com.anhtam.gate9.v2.MainActivity
import com.anhtam.gate9.v2.discussion.common.rating.RatingViewModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber



class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FirebaseMessageService"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("From: ${remoteMessage.from}")

        //Check if message contains a data payload
        remoteMessage.data?.isNotEmpty()?.let {
            Timber.d("Message data payload: $it")

            //Compose and show notification
            if (!remoteMessage.data.isNullOrEmpty()) {
                val msg = remoteMessage.data ?: return@let
                sendNotification(msg)
            }
        }


    }

    override fun onNewToken(token: String?) {
        Timber.tag(TAG).d(token)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        token?.let {
            registerTokenWithServer(it)
        }

    }

    private fun registerTokenWithServer(token: String) {
        Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FCMService::class.java)
                .addTokenFCM(0, token).enqueue(object : Callback<Map<String, Any>> {
                    override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                        Timber.d(if (t.message != null) t.message else "Error Server")

                    }

                    override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                        Timber.d("Add FCM Token Successfully with token is :%s", token)
                    }

                })
    }


    private fun sendNotification(data: Map<String, String>) {
        val code = data["code"] as? Int ?: -1
        val body = data["body"]?: return
        val jsonObject = JSONObject(body)
        val content = jsonObject.get("content") ?: "Empty Content!"

        val bundle = Bundle()
        when (NotificationType.getNotificationType(code)) {
            NotificationType.COMMENT -> {
                bundle.putInt(Config.NOTIFICATION_TYPE, code)
                bundle.putSerializable(Config.COMMENT, body as Post)
            }

            NotificationType.CHAT -> {
                //TODO Handle send data Chat
            }

            NotificationType.MESSAGE -> {
                //TODO Handle send data Message
            }

            NotificationType.RATING -> {
                //TODO Handle send data Rating
            }
        }
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val chanelId = getString(R.string.chanel_id_notification)
        val notificationBuilder = NotificationCompat.Builder(this, chanelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.title_notification_comment))
                .setContentText(content.toString())
                .setAutoCancel(false)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setNumber(3)
                .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //Since Android Oreo Notification chanel id needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chanel = NotificationChannel(chanelId, getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH)
            chanel.setShowBadge(true)
            notificationManager.createNotificationChannel(chanel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}