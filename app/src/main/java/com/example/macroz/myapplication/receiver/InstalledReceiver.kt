package com.example.macroz.myapplication.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

class InstalledReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        //接收安装广播
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            val packageName = intent.getDataString()
            Log.d("Receiver","安装了:" +packageName + "包名的程序");
        }
        //接收卸载广播
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            val packageName = intent.getDataString()
            Log.d("Receiver","卸载了:"  + packageName + "包名的程序");

        }

    }


    companion object {
        fun register(context: Context){
            Log.d("Receiver","注册广播")
            val intentFilter= IntentFilter();
            intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
            intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
            intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
            intentFilter.addDataScheme("package")
            context.registerReceiver(InstalledReceiver(),intentFilter)

        }

    }


}

