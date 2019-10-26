package Adapters

import Holders.CardHolder
import Models.WikiPage
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.amindadgar.intowikipedia.R

class ArticleCardRecyclerAdapter : RecyclerView.Adapter<CardHolder> (){
    val currentResult = ArrayList<WikiPage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): CardHolder {
        val carditem = LayoutInflater.from(parent.context).inflate(R.layout.article_card_item,parent,false)
        return CardHolder(carditem)
    }

    override fun getItemCount(): Int {
        return currentResult.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val page=currentResult[position]
        holder.updateWithPage(page)
    }
}