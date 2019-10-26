package respositories

import Models.WikiPage
import Models.thumbnail
import com.google.gson.Gson
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select

class HistoryRepository(val databaseHelper: ArticleDatabaseOpenHelper) {
    private val tableName:String="History"

    fun addHistory(page: WikiPage){
        databaseHelper.use {
            insert(tableName,"id" to page.pageid,
                "title" to page.title,
                "url" to page.fullurl,
                "thumbnailJson" to Gson().toJson(page.thumbnail))
        }
    }
    fun removePageByid(pageId:Int){
        databaseHelper.use {
            delete(tableName,"id = {pageId}","pageId" to pageId)
        }
    }
    fun getAllHistory():ArrayList<WikiPage>{
        val pages = ArrayList<WikiPage>()

        val articlcerowParser = rowParser {id:Int,title:String,url:String,thumbnailJson:String ->
            val page = WikiPage()
            page.pageid = id
            page.title = title
            page.fullurl = url
            page.thumbnail = Gson().fromJson(thumbnailJson, thumbnail::class.java)

            pages.add(page)
        }
        databaseHelper.use {
            select(tableName).parseList(articlcerowParser)
        }
        return pages
    }
}