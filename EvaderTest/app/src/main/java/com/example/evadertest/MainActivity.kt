package com.example.evadertest

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {

    private val TARGET_PKG = "com.example.targetapk"
    private lateinit var logView: TextView
    private val handler = Handler(Looper.getMainLooper())

    private val intentLauncher =
        registerForActivityResult(
            androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val tag = lastIntentTag
            if (result.resultCode == RESULT_OK) {
                append("$tag: foydalanuvchi TASDIQLADI")
            } else {
                append("$tag: o'rnatish bekor qilindi (kod ${result.resultCode})")
            }
            checkInstalledDelayed(tag)
        }

    private var lastIntentTag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logView = TextView(this).apply { textSize = 15f }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 80, 50, 50)
        }
        layout.addView(Button(this).apply {
            text = "1-zaiflik: Session API"
            setOnClickListener { vector1Session() }
        })
        layout.addView(Button(this).apply {
            text = "2-zaiflik: Boshqa installer"
            setOnClickListener { vector2AltInstaller() }
        })
        layout.addView(Button(this).apply {
            text = "3-zaiflik: Split APK"
            setOnClickListener { vector3Split() }
        })
        layout.addView(Button(this).apply {
            text = "4-zaiflik: Fayl nomi o'zgartirilgan"
            setOnClickListener { vector4Renamed() }
        })
        layout.addView(ScrollView(this).apply { addView(logView) })
        setContentView(layout)
        append("EvaderTest tayyor. Target paket: $TARGET_PKG")
    }

    private fun vector1Session() {
        val f = copyAsset("target.apk", "session_install.pkg")
        val pi = packageManager.packageInstaller
        val params = PackageInstaller.SessionParams(
            PackageInstaller.SessionParams.MODE_FULL_INSTALL
        ).apply { setAppPackageName(TARGET_PKG) }

        val sessionId = pi.createSession(params)
        val session = pi.openSession(sessionId)
        session.openWrite("session_install.pkg", 0, f.length()).use { out ->
            f.inputStream().use { it.copyTo(out) }
            session.fsync(out)
        }
        val pending = PendingIntent.getBroadcast(
            this, sessionId,
            Intent(this, StatusReceiver::class.java)
                .putExtra("tag", "1-zaiflik (Session API)"),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        session.commit(pending.intentSender)
        session.close()
        append("1-zaiflik: session so'rovi yuborildi...")
        checkInstalledDelayed("1-zaiflik (Session API)", 5000)
    }

    private fun vector2AltInstaller() {
        val f = copyAsset("target.apk", "alt_install.apk")
        val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", f)

        val candidates = listOf(
            "com.android.packageinstaller",
            "com.google.android.packageinstaller",
            "com.miui.packageinstaller",
            "com.samsung.android.packageinstaller"
        )
        val target = candidates.firstOrNull { isPackageInstalled(it) }
        if (target == null) {
            append("2-zaiflik: muqobil installer topilmadi")
            return
        }
        lastIntentTag = "2-zaiflik (installer: $target)"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.android.package-archive")
            setPackage(target)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        append("2-zaiflik: $target orqali yo'naltirildi")
        intentLauncher.launch(intent)
    }

    private fun vector3Split() {
        try {
            val pi = packageManager.packageInstaller
            val params = PackageInstaller.SessionParams(
                PackageInstaller.SessionParams.MODE_INHERIT_EXISTING
            ).apply { setAppPackageName(TARGET_PKG) }

            val sessionId = pi.createSession(params)
            val session = pi.openSession(sessionId)
            assets.open("split.apk").use { input ->
                session.openWrite("split.apk", 0, -1).use { output ->
                    input.copyTo(output)
                    session.fsync(output)
                }
            }
            val pending = PendingIntent.getBroadcast(
                this, sessionId,
                Intent(this, StatusReceiver::class.java)
                    .putExtra("tag", "3-zaiflik (Split APK)"),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
            session.commit(pending.intentSender)
            session.close()
            append("3-zaiflik: split-sessiya yuborildi...")
            checkInstalledDelayed("3-zaiflik (Split APK)", 5000)
        } catch (e: Exception) {
            append("3-zaiflik: ishga tushmadi - avval targetni 1-zaiflik bilan o'rnating. (${e.message})")
        }
    }

    private fun vector4Renamed() {
        val rand = (1000..9999).random()
        val f = copyAsset("target.apk", "sys_update_$rand.bin")
        val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", f)

        lastIntentTag = "4-zaiflik (fayl: sys_update_$rand.bin)"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.android.package-archive")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        append("4-zaiflik: 'sys_update_$rand.bin' nomi bilan yuborildi")
        intentLauncher.launch(intent)
    }

    private fun copyAsset(assetName: String, outName: String): File {
        val f = File(cacheDir, outName)
        assets.open(assetName).use { input ->
            f.outputStream().use { input.copyTo(it) }
        }
        return f
    }

    private fun isPackageInstalled(pkg: String) = try {
        packageManager.getPackageInfo(pkg, 0); true
    } catch (e: PackageManager.NameNotFoundException) { false }

    private fun isTargetInstalled() = isPackageInstalled(TARGET_PKG)

    private fun checkInstalledDelayed(tag: String, delayMs: Long = 2500) {
        handler.postDelayed({
            if (isTargetInstalled()) {
                append("✅✅✅ $tag: TARGET O'RNATILDI - himoya bu oqimni PAYQAMADI!")
            } else {
                append("⛔ $tag: target o'rnatilmadi - himoya ishlayapti yoki foydalanuvchi bekor qildi")
            }
        }, delayMs)
    }

    private fun append(msg: String) {
        logView.append("\n• $msg")
    }
}
