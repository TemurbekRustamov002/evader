package com.example.targetapk

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this).apply {
            text = "\u2705 TEST MUVAFFAQIYATLI!\n\nUshbu ilova o'rnatildi —" +
                   " demak himoya chetlab o'tilgan.\n\n" +
                   "Paket: com.example.targetapk"
            textSize = 24f
            setTextColor(Color.parseColor("#1B5E20"))
            setBackgroundColor(Color.parseColor("#C8E6C9"))
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            setPadding(60, 200, 60, 200)
        }
        setContentView(textView)
    }
}
