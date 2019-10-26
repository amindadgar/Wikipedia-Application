package Fragments.ButtonNavigationFragments


import Adapters.ArticleCardRecyclerAdapter
import Models.WikiPage
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import application.WikiApplication

import com.amindadgar.intowikipedia.R
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import kotlinx.android.synthetic.main.fragment_explorefragment.*
import managers.WikiManager
import org.jetbrains.anko.doAsync


class favouriteFragment : Fragment() {
    var favouriteRecycler:RecyclerView?=null

    private var wikiManager: WikiManager?=null
    private var adapter = ArticleCardRecyclerAdapter()
    var nav: SlidingRootNav?=null
    var infavouriteFragment:Boolean?=null

    override fun onStart() {
        super.onStart()
        infavouriteFragment = true
    }

    override fun onPause() {
        super.onPause()
        infavouriteFragment = false
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)
        favouriteRecycler =view.findViewById(R.id.favourite_recycler)
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

        favouriteRecycler?.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        favouriteRecycler?.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            val favourites = wikiManager!!.getfavorites()
            adapter.currentResult.clear()
            adapter.currentResult.addAll(favourites as ArrayList<WikiPage>)
            activity!!.runOnUiThread {
                adapter.notifyDataSetChanged()
            }
        }
    }


}
