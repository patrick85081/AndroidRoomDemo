package com.example.androidroomdemo

import android.app.Application
import android.content.Context
import android.widget.Toast
import java.lang.reflect.Method

/**
 * Created by user on 2018/1/17.
 */
class App : Application()
{
    override fun onCreate()
    {
        super.onCreate()
        showDebugDBAddressLogToast(this);
    }

    private fun showDebugDBAddressLogToast(context: Context)
    {
        if (BuildConfig.DEBUG)
        {
            try
            {
                val debugDB: Class<*> = Class.forName("com.amitshekhar.DebugDB");
                val getAddressLog: Method = debugDB.getMethod("getAddressLog");
                val value: Any = getAddressLog.invoke(null);
                Toast.makeText(context, value.toString(), Toast.LENGTH_LONG).show();
            }
            catch (ignore: Exception)
            {

            }
        }
    }
}