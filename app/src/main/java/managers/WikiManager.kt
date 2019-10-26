package managers

import Models.WikiPage
import Models.WikiSearchResult
import Providers.ArticleDataProvider
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.util.Log
import respositories.FavouritesRepository
import respositories.HistoryRepository

class WikiManager(private val provider: ArticleDataProvider,
                  private val favouritesRepository: FavouritesRepository,
                  private val historyRepository: HistoryRepository,val context: Context){
    private var historyCache: ArrayList<WikiPage> = ArrayList<WikiPage>()
    private var favouriteCache: ArrayList<WikiPage> = ArrayList<WikiPage>()

    fun search(context: Context,term:String,skip:Int,take:Int,handler: (result :WikiSearchResult)->Unit){
        return provider.search(context,term,skip,take,handler)
    }
    fun getRandom(context:Context,take: Int,handler: (result: WikiSearchResult) -> Unit){
        return provider.getRandom(context as FragmentActivity,take,handler)
    }

    fun gethistory():ArrayList<WikiPage>?{
        if (historyCache.isEmpty())
            historyCache = historyRepository.getAllHistory()

        return historyCache
    }

    fun getfavorites():ArrayList<WikiPage>?{
        if (favouriteCache.isEmpty())
            favouriteCache = favouritesRepository.getAllfavourites()
        return favouriteCache
    }

    fun addfavourites(page: WikiPage){
        favouriteCache.add(page)
        favouritesRepository.addfavourite(page)
    }

    fun removepage(pageId:Int){
        favouritesRepository.removePageByid(pageId)
        favouriteCache = favouriteCache.filter { it.pageid !=pageId } as ArrayList<WikiPage>
    }
    fun removehistory(pageid:Int){
        if (historyCache.isNotEmpty()){
            historyCache = historyCache.filter { it.pageid != pageid} as ArrayList<WikiPage>
        }
        historyRepository.removePageByid(pageid)
    }

    fun getIsfavourite(pageId: Int):Boolean{
        return favouritesRepository.isArticleFavourite(pageId)
    }

    fun addhistory(Page:WikiPage){
        historyCache.add(Page)
        historyRepository.addHistory(Page)
    }

    fun clearhistory(){
        if (historyCache.isNotEmpty()) historyCache.clear()
        val allhistory = historyRepository.getAllHistory()
        allhistory.forEach { page:WikiPage ->
            Log.d("pagenum ${page.pageid}","${page.title}")
        }
        allhistory.forEach { historyRepository.removePageByid(it.pageid!!) }
    }


}