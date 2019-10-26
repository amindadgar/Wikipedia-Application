package Holders

import Models.WikiPage
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.amindadgar.intowikipedia.Activities.Article_detail_Activity
import com.amindadgar.intowikipedia.R
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync


class CardHolder(itemView:View) :RecyclerView.ViewHolder(itemView) {

    private val article_image:ImageView = itemView.findViewById(R.id.article_image)
    private val articleTitle :TextView = itemView.findViewById(R.id.article_title)

    private var currentpage :WikiPage?=null
    init {
        itemView.setOnClickListener {
            val PageIntent = Intent(itemView.context,Article_detail_Activity::class.java)
            val pageJson = Gson().toJson(currentpage)
            PageIntent.putExtra("page",pageJson)
            itemView.context.startActivity(PageIntent)
        }
    }

    fun updateWithPage(page:WikiPage){

        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT)

        currentpage = page
        articleTitle.text = page.title

        if(page.thumbnail!=null){
            Picasso.get().load(page.thumbnail!!.source)
                .placeholder(R.drawable.loading).into(article_image)
            doAsync {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE)
                articleTitle.layoutParams = layoutParams
                articleTitle.setTypeface(articleTitle.typeface,Typeface.NORMAL)
                articleTitle.setBackgroundResource(R.drawable.shape_rectangle)
            }



        }
        else{
            article_image.setImageResource(R.drawable.article_no_title_background)

            doAsync {
                articleTitle.gravity = Gravity.CENTER
                layoutParams.removeRule(RelativeLayout.ALIGN_BOTTOM)
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE)
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE)
                articleTitle.layoutParams = layoutParams
                articleTitle.setTypeface(articleTitle.typeface,Typeface.BOLD)
                articleTitle.setBackgroundResource(0)
            }


        }

    }

}


