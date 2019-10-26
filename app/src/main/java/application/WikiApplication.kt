package application

import Providers.ArticleDataProvider
import android.app.Application
import managers.WikiManager
import respositories.ArticleDatabaseOpenHelper
import respositories.FavouritesRepository
import respositories.HistoryRepository

class WikiApplication:Application() {
    private var dbHelper:ArticleDatabaseOpenHelper?=null
    private var favouriteRepository:FavouritesRepository?=null
    private var historyRepository:HistoryRepository?=null
    private var wikiProvider:ArticleDataProvider?= null
    var wikiManager:WikiManager?=null
    private set

    override fun onCreate() {
        super.onCreate()

        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favouriteRepository = FavouritesRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!,favouriteRepository!!,historyRepository!!,applicationContext)
    }
}