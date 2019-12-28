package com.anhtam.gate9.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.util.*


class PermissionUtils {
    companion object {
        private const val PREFERENCES = "PermissionsInfo.PREFERENCES"

        fun checkPermissions(context: Context, permission: Array<String>): Boolean {
            for (per in permission) {
                if (ContextCompat.checkSelfPermission(context, per) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }

        fun requestPermissions(activity: Activity, permission: ArrayList<String>, requestCode: Int) {
            val notGrantedPermission = ArrayList<String>()
            for (per in permission) {
                if (ContextCompat.checkSelfPermission(activity, per) != PackageManager.PERMISSION_GRANTED) {
                    notGrantedPermission.add(per)
                }
            }
            if (notGrantedPermission.size > 0) {
                ActivityCompat.requestPermissions(activity, notGrantedPermission.toTypedArray(), requestCode)
            }

        }

        fun requestPermissions(fragment: Fragment?, permission: Array<String>?, requestCode: Int) {
            val unwrappedFragment = fragment ?: return
            val unwrappedContext = unwrappedFragment.context ?: return
            val unwrappedPermissions = permission ?: return

            val notGrantedPermission = ArrayList<String>()
            for (per in unwrappedPermissions) {
                if (ContextCompat.checkSelfPermission(unwrappedContext, per) != PackageManager.PERMISSION_GRANTED) {
                    notGrantedPermission.add(per)
                }
            }
            if (notGrantedPermission.size > 0) {
                unwrappedFragment.requestPermissions(unwrappedPermissions, requestCode)
            }
        }

        fun shouldShowPermissions(activity: Activity, permissions: Array<String>): Boolean {
            for (permission in permissions) {
                if (shouldShowPermission(activity, permission)) return true
            }
            return false
        }

        private fun shouldShowPermission(activity: Activity, permission: String): Boolean {
            return isFirstTimeAskingPermission(activity, permission) || ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        }

        private fun isFirstTimeAskingPermission(context: Context, permission: String): Boolean {
            val sharedPreferences = context.getSharedPreferences(PREFERENCES, 0)
            val isFirstTime = sharedPreferences.getBoolean(permission, true)
            if (isFirstTime) {
                sharedPreferences.edit().putBoolean(permission, false).apply()
            }
            return isFirstTime
        }
    }
}
