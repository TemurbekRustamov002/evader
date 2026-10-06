package com.example.evadertest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.widget.Toast

class StatusReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val tag = intent.getStringExtra("tag") ?: "Sessiya"
        when (intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -1)) {
            PackageInstaller.STATUS_SUCCESS ->
                Toast.makeText(context,
                    "✅✅✅ $tag: MUVAFFAQIYATLI - himoya sessiya oqimini payqamadi!",
                    Toast.LENGTH_LONG).show()
            PackageInstaller.STATUS_FAILURE_ABORTED ->
                Toast.makeText(context, "⛔ $tag: bekor qilindi", Toast.LENGTH_SHORT).show()
            else ->
                Toast.makeText(context, "$tag: xatolik kodi " +
                    intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -1), Toast.LENGTH_SHORT).show()
        }
    }
}
