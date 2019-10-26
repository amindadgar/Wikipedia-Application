package Providers

import Models.Urls
import Models.WikiSearchResult
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.irozon.alertview.AlertActionStyle
import com.irozon.alertview.AlertStyle
import com.irozon.alertview.AlertView
import com.irozon.alertview.objects.AlertAction
import java.io.Reader


class ArticleDataProvider {

    init {
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Amin Dadgar application")
    }
    fun search(context:Context,term:String,skip:Int,take:Int,responseHandler: (result:WikiSearchResult)->Unit?){
        Urls.getSearch(term,skip, take).httpGet()
            .responseObject(WikipediaDataDeserializer()){_,response,result ->
                if (response.statusCode!=200){
                    val alert = AlertView("Error","Unable to connect to internet\n" +
                            "please check your internet connection",AlertStyle.DIALOG)
                    alert.addAction(AlertAction("OK",AlertActionStyle.DEFAULT,{}))
                    alert.show(context as AppCompatActivity)
                }
                else {
                    val (data,_) = result
                    responseHandler(data as WikiSearchResult)
                }
        }
    }

    fun getRandom(context:FragmentActivity,take:Int,responseHandler: (result: WikiSearchResult) -> Unit?){
            Urls.getRandom(take).httpGet()
            .responseObject(WikipediaDataDeserializer()){ _, response, result ->
                if (response.statusCode!=200){
                    val alert = AlertView("Error","Unable to connect to internet\n" +
                            "please check your internet connection",AlertStyle.DIALOG)
                    alert.addAction(AlertAction("Retry",AlertActionStyle.DEFAULT,
                        {getRandom(context,take,responseHandler)}))
                    alert.show(context as AppCompatActivity)


                }
                else {
                    val (data, _) = result
                    responseHandler(data as WikiSearchResult)
                }
            }
    }
    class WikipediaDataDeserializer:ResponseDeserializable<WikiSearchResult> {
        override fun deserialize(reader: Reader):WikiSearchResult =
            Gson().fromJson(reader,WikiSearchResult::class.java)
    }
}

