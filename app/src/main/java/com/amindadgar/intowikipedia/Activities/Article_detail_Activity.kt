package com.amindadgar.intowikipedia.Activities

import Models.WikiPage
import Models.WikiSearchResult
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.Toast
import application.WikiApplication
import com.amindadgar.intowikipedia.R
import com.google.gson.Gson
import com.irozon.alertview.AlertActionStyle
import com.irozon.alertview.AlertStyle
import com.irozon.alertview.AlertView
import com.irozon.alertview.objects.AlertAction
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlinx.android.synthetic.main.article_detail_activity.*
import managers.WikiManager
import java.lang.Exception

class Article_detail_Activity : AppCompatActivity(){

    private var wikiManager:WikiManager?=null
    private var currentPage:WikiPage?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_detail_activity)


        wikiManager = (applicationContext as WikiApplication).wikiManager

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_back_button)

        val wikipageJson = intent.getStringExtra("page")
        val alarmManager:String? = intent.getStringExtra("AlarmManager")
        currentPage = Gson().fromJson<WikiPage>(wikipageJson,WikiPage::class.java)

        if (alarmManager == "yes"){
            wikiManager!!.getRandom(this,2,{result: WikiSearchResult ->
                currentPage = result.query!!.pages[1]
            })
        }

        var ErrorCheck = false
        supportActionBar!!.title = currentPage!!.title
        WebView.webViewClient = object :WebViewClient() {

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                val alert = AlertView("Error","Unable to connect to internet\n" +
                        "please check your internet connection", AlertStyle.DIALOG)
                alert.addAction(AlertAction("OK", AlertActionStyle.DEFAULT,{}))
                alert.show(this@Article_detail_Activity)
                ErrorCheck =true
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                 progressBar.visibility = View.VISIBLE
                  return ErrorCheck
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE

            }
        }
        addhistory(currentPage!!)
        WebView.loadUrl(currentPage!!.fullurl)

        WebView.freeMemory()


    }

    fun addhistory(page: WikiPage){
        var check = true
        var pages = wikiManager!!.gethistory()
        pages!!.forEach {
            if (it.pageid == page.pageid) check =false
        }
        if (check) wikiManager!!.addhistory(page)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {



        if (item!!.itemId == android.R.id.home){
            finish()
        }
        else if (item.itemId == R.id.share){

        }
        else if (item.itemId == R.id.action_favourite){
            val favourite = item.setIcon(R.drawable.icons_heart_filled)
            try {
                if (wikiManager!!.getIsfavourite(currentPage!!.pageid!!)){

                    wikiManager!!.removepage(currentPage!!.pageid!!)
                    favourite.setIcon(R.drawable.icons_heart_white)

                    DynamicToast.make(this, "Article removed from favourites",
                        getDrawable(R.drawable.icon_unheart),
                        ContextCompat.getColor(this,android.R.color.black),
                        ContextCompat.getColor(this,R.color.BlueLight)
                    ,Toast.LENGTH_LONG).show()

                }else{
                    wikiManager!!.addfavourites(currentPage!!)
                    favourite.setIcon(R.drawable.icons_heart_filled)

                    DynamicToast.make(this,"Article added to favourite",
                        getDrawable(R.drawable.icons_heart_filled),
                        ContextCompat.getColor(this,android.R.color.black),
                        ContextCompat.getColor(this,R.color.BlueLight)
                        ,Toast.LENGTH_LONG).show()

                }
            }catch (ex:Exception){
                Log.d("favourite Exception",ex.toString())
                DynamicToast.makeError(this,"Unable to update favourite",Toast.LENGTH_LONG).show()
            }

        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_webview_menu,menu)

        val Favourite = menu!!.findItem(R.id.action_favourite)
        if (wikiManager!!.getIsfavourite(currentPage!!.pageid as Int)){
            Favourite.setIcon(R.drawable.icons_heart_filled)
        }
        else{
            Favourite.setIcon(R.drawable.icons_heart_white)
        }
        return super.onCreateOptionsMenu(menu)
    }

}