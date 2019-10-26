package Fragments.ButtonNavigationFragments


import Adapters.ArticleListItemRecyclerAdapter
import Models.WikiPage
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import application.WikiApplication
import com.amindadgar.intowikipedia.R
import com.irozon.alertview.AlertActionStyle
import com.irozon.alertview.AlertStyle
import com.irozon.alertview.AlertView
import com.irozon.alertview.objects.AlertAction
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import managers.WikiManager
import org.jetbrains.anko.doAsync


class HistoryFragment : Fragment() {

    var historyRecycler:RecyclerView?=null
    private var clearHistory:TextView?=null
    private var wikiManager:WikiManager?=null
    private var adapter = ArticleListItemRecyclerAdapter()
    var nav: SlidingRootNav?=null
    var inhistoryFragment:Boolean?=null

    override fun onStart() {
        super.onStart()
        inhistoryFragment = true
    }

    override fun onPause() {
        super.onPause()
        inhistoryFragment = false
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_history, container, false)
        historyRecycler =view.findViewById(R.id.history_recycler)
        clearHistory = view.findViewById(R.id.clear_history_id)
        val drawer_icon = view.findViewById<ImageView>(R.id.drawer_icon)

        nav = SlidingRootNavBuilder(activity).withMenuLayout(R.layout.left_menu_drawer)
            .withDragDistance(180)
            .withRootViewScale(0.7f)
            .withRootViewElevation(10)
            .withRootViewYTranslation(4)
            .withSavedState(savedInstanceState)
            .inject()

        drawer_icon.setOnClickListener {
            if (!nav!!.isMenuOpened)
                nav!!.openMenu()
            else
                nav!!.closeMenu()
        }


        clearHistory!!.setOnClickListener {
            val alert = AlertView("Clear History","Are you sure to clear all history?",AlertStyle.DIALOG)
            alert.addAction(AlertAction("Yes",AlertActionStyle.NEGATIVE,{
                adapter.currentResult.clear()
                doAsync {
                    wikiManager?.clearhistory()
                }
                activity!!.runOnUiThread { adapter.notifyDataSetChanged() }
            }))
            alert.addAction(AlertAction("No",AlertActionStyle.DEFAULT,{}))
            alert.show(context as AppCompatActivity)
        }

        historyRecycler?.layoutManager = LinearLayoutManager(context)
        historyRecycler?.adapter = adapter
        return view
    }
    override fun onResume() {
        super.onResume()
        doAsync {
            val histories = wikiManager!!.gethistory()
            adapter.currentResult.clear()
            adapter.currentResult.addAll(histories as ArrayList<WikiPage>)
            activity!!.runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }
    fun RemoveListItem(position:Int){
        adapter.delete(position)
    }
    fun RemovehistoryINhistioryfragment(pageid: Int){
        wikiManager!!.removehistory(pageid)
    }


}
