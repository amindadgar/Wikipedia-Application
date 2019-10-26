package com.amindadgar.intowikipedia.Activities

import Adapters.ArticleListItemRecyclerAdapter
import android.app.SearchManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import application.WikiApplication
import com.amindadgar.intowikipedia.R
import com.irozon.alertview.AlertActionStyle
import com.irozon.alertview.AlertStyle
import com.irozon.alertview.AlertView
import com.irozon.alertview.objects.AlertAction
import kotlinx.android.synthetic.main.activity_search.*
import managers.WikiManager

class SearchActivity : AppCompatActivity() {

    private var wikiManager:WikiManager?=null
    private var adapter = ArticleListItemRecyclerAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        wikiManager = (applicationContext as WikiApplication).wikiManager

        setSupportActionBar(search_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.icon_back_button)
        search_activity_recyclerview.layoutManager = LinearLayoutManager(this)
        search_activity_recyclerview.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val searchItem = menu!!.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem!!.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        searchView.requestFocus()

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) : Boolean{
                //TODO("make another format of search for searching in articles description NOT THE TITLE!!")

                wikiManager?.search(this@SearchActivity,query,0,20,
                    {WikiResult->
                    adapter.currentResult.clear()
                    if (WikiResult.query != null){
                        noResultImage.visibility = View.GONE
                        adapter.currentResult.addAll(WikiResult.query.pages)
                    }
                    else{
                        noResultImage.visibility = View.VISIBLE
                        searchView.setQuery("",true)
                    }
                    runOnUiThread { adapter.notifyDataSetChanged() }
                })
                return false
            }
            override fun onQueryTextChange(query: String?): Boolean {
               if(query!="") {
                   wikiManager?.search(this@SearchActivity, query as String, 0, 20,
                       { WikiResult ->
                           adapter.currentResult.clear()
                           if (WikiResult.query != null){
                               noResultImage.visibility = View.GONE
                               adapter.currentResult.addAll(WikiResult.query.pages)
                           }
                           else{
                               noResultImage.visibility = View.VISIBLE
                               searchView.setQuery("",true)
                           }
                           runOnUiThread { adapter.notifyDataSetChanged() }
                       })
               }
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}
