package com.amindadgar.intowikipedia.Activities

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.amindadgar.intowikipedia.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        BackButton.setOnClickListener {
            finish()
        }

        leoshen.setOnClickListener {
            OpenUrl("https://github.com/leochuan")
        }
        karnSaheb.setOnClickListener {
            OpenUrl("https://github.com/Karn")
        }
        Yaroslav.setOnClickListener {
            OpenUrl("https://github.com/yarolegovich")
        }
        Pranav.setOnClickListener {
            OpenUrl("https://github.com/pranavpandey")
        }
        HammadAkram.setOnClickListener {
            OpenUrl("https://github.com/Hamadakram")
        }
    }
    fun OpenUrl(Url:String){
        val openurl = Intent(Intent.ACTION_VIEW)
        openurl.data = Uri.parse(Url)
        startActivity(openurl)
    }
}
