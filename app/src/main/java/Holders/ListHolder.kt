package Holders

import Fragments.ButtonNavigationFragments.HistoryFragment
import Models.WikiPage
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import application.WikiApplication
import com.amindadgar.intowikipedia.Activities.Article_detail_Activity
import com.amindadgar.intowikipedia.Activities.MainActivity
import com.amindadgar.intowikipedia.R
import com.google.gson.Gson
import com.irozon.alertview.AlertActionStyle
import com.irozon.alertview.AlertStyle
import com.irozon.alertview.AlertView
import com.irozon.alertview.objects.AlertAction
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_card_item.*
import managers.WikiManager
import org.jetbrains.anko.doAsync

class ListHolder(itemView:View) :RecyclerView.ViewHolder(itemView) {
    private val article_image:ImageView = itemView.findViewById(R.id.result_icon)
    private val articleTitle :TextView = itemView.findViewById(R.id.result_title)
    private var currentpage :WikiPage?=null
//    private var wikiManager:WikiManager?=null

    init {
        itemView.setOnClickListener { view: View ->
            val PageIntent = Intent(itemView.context, Article_detail_Activity::class.java)
            val pageJson = Gson().toJson(currentpage)
            PageIntent.putExtra("page", pageJson)
            itemView.context.startActivity(PageIntent)
        }
        itemView.setOnLongClickListener { view:View ->
//            wikiManager = (view.context as WikiApplication).wikiManager
            val alert = AlertView("Delete item","Are you sure to delete item",AlertStyle.DIALOG)
            alert.addAction(AlertAction("Yes",AlertActionStyle.NEGATIVE,{
                doAsync {
//                    wikiManager!!.removepage(currentpage!!.pageid as Int)
//                    HistoryFragment().RemoveListItem(position = 2)
//                    HistoryFragment().RemovehistoryINhistioryfragment(currentpage!!.pageid as Int)
                }

            }))

            alert.addAction(AlertAction("No",AlertActionStyle.DEFAULT,{}))
            alert.show(view.context as AppCompatActivity)
            true
        }
    }

    fun updateWithPage(page: WikiPage) {
        currentpage = page

        articleTitle.text = page.title
        if (page.thumbnail != null) {
            Picasso.get().load(page.thumbnail!!.source).into(article_image)
        }
        else {
            article_image.setImageResource(R.drawable.no_image_small)
        }

    }

}