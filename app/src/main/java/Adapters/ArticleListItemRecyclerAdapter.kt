package Adapters

import Holders.ListHolder
import Models.WikiPage
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.amindadgar.intowikipedia.R

class ArticleListItemRecyclerAdapter : RecyclerView.Adapter<ListHolder> (){
    val currentResult = ArrayList<WikiPage>()
    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): ListHolder {
        val carditem = LayoutInflater.from(parent.context).inflate(R.layout.aricle_list_item,parent,false)
        return ListHolder(carditem)
    }

    override fun getItemCount(): Int {
        return currentResult.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val page = currentResult[position]
        holder.updateWithPage(page)
    }
    fun delete(position: Int){
        currentResult.removeAt(position)
        notifyItemRemoved(position)
    }
    
}