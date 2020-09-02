package kr.hs.dgsw.stac.semo.widget.extension

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.startActivityNoFinish(context: Context, activity: Class<*>) {
    startActivity(Intent(context, activity).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
}

fun AppCompatActivity.startActivityWithFinish(context: Context, activity: Class<*>) {
    startActivity(Intent(context, activity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    this.finish()
}

fun AppCompatActivity.startActivityWithExtra(intent: Intent) {
    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    this.finish()
}

fun AppCompatActivity.startActivityWithExtraNoFinish(intent: Intent) {
    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
}