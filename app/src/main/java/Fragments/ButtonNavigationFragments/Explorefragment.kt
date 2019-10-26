package Fragments.ButtonNavigationFragments


import Adapters.ArticleCardRecyclerAdapter
import Models.WikiSearchResult
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.transition.Fade
import android.transition.Slide
import android.view.*
import android.widget.ImageView
import managers.WikiManager
import java.lang.Exception
import application.WikiApplication
import com.amindadgar.intowikipedia.Activities.SearchActivity
import com.amindadgar.intowikipedia.R
import com.google.gson.Gson
import com.irozon.alertview.AlertActionStyle
import com.irozon.alertview.AlertStyle
import com.irozon.alertview.AlertView
import com.irozon.alertview.objects.AlertAction
import com.leochuan.CenterSnapHelper
import com.leochuan.CircleLayoutManager
import com.leochuan.GalleryLayoutManager
import com.leochuan.ScaleLayoutManager
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import com.yarolegovich.slidingrootnav.transform.CompositeTransformation
import com.yarolegovich.slidingrootnav.transform.ElevationTransformation


class Explorefragment : Fragment() {
    private var WikiManager:WikiManager?=null


    var ExploreRecycler:RecyclerView? = null
    var refresher:SwipeRefreshLayout?=null
    var searchImage:ImageView?= null
    var drawer_icon:ImageView?=null
    var nav:SlidingRootNav?=null
    var inExplorefragment:Boolean?=null

    private var adapter = ArticleCardRecyclerAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        WikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }


    override fun onPause() {
        super.onPause()
        inExplorefragment = false
    }

    override fun onStart() {
        super.onStart()
        inExplorefragment = true
        val sharedPreferences = activity!!.getSharedPreferences("LayoutMode",Context.MODE_PRIVATE)
        val RecyclerViewLayoutMode = sharedPreferences.getString("LayoutManager","1")
        if (RecyclerViewLayoutMode == "0"){
            ExploreRecycler?.layoutManager = LinearLayoutManager(activity)
        }
        else if (RecyclerViewLayoutMode == "1"){
            ExploreRecycler?.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        else if(RecyclerViewLayoutMode == "2"){
            ExploreRecycler?.layoutManager = CircleLayoutManager.Builder(activity)
                .setAngleInterval(50)
                .setDistanceToBottom(5)
                .build()
            CenterSnapHelper().attachToRecyclerView(ExploreRecycler)
        }
        else if (RecyclerViewLayoutMode == "3"){
            ExploreRecycler?.layoutManager = ScaleLayoutManager.Builder(activity,4)
                .setMinScale((0.3).toFloat())
                .build()
            CenterSnapHelper().attachToRecyclerView(ExploreRecycler)
        }
        else if (RecyclerViewLayoutMode == "4"){
            ExploreRecycler?.layoutManager = ScaleLayoutManager.Builder(activity,2)
                .setMinScale((0.6).toFloat())
                .setOrientation(1)
                .build()
        }
        else if(RecyclerViewLayoutMode == "5"){
            ExploreRecycler?.layoutManager = GalleryLayoutManager.Builder(activity,2)
                .build()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        setuptransition()

        val view = inflater.inflate(R.layout.fragment_explorefragment, container, false)
        ExploreRecycler =view?.findViewById(R.id.Explore_recyclerView)
        drawer_icon = view.findViewById(R.id.drawer_icon)
        refresher = view.findViewById(R.id.Refresher)
        searchImage = view.findViewById(R.id.search_image)



        searchImage!!.setOnClickListener {
            val intent = Intent(context,SearchActivity::class.java)

            val TransitionActivityOptions:ActivityOptions = ActivityOptions
                .makeSceneTransitionAnimation(activity,searchImage, getString(R.string.SearchIconTransition))

            startActivity(intent,TransitionActivityOptions.toBundle())
        }

        nav = SlidingRootNavBuilder(activity).withMenuLayout(R.layout.left_menu_drawer)
            .withDragDistance(180)
            .withRootViewScale(0.7f)
            .withRootViewElevation(10)
            .withRootViewYTranslation(4)
            .withSavedState(savedInstanceState)
            .withContentClickableWhenMenuOpened(false)
            .inject()

        

        drawer_icon!!.setOnClickListener {
           if (!nav!!.isMenuOpened)
                nav!!.openMenu()
           else
                nav!!.closeMenu()
        }

        ExploreRecycler?.adapter =adapter

        refresher?.setOnRefreshListener {
            getRandomArticles()
        }

        if (adapter.currentResult.isEmpty()) getRandomArticles()



        return view
    }



    private fun getRandomArticles(){
        refresher?.isRefreshing =true
        try {
           WikiManager!!.getRandom(activity as FragmentActivity,25,{ wikiResult:WikiSearchResult ->
                adapter.currentResult.clear()
                adapter.currentResult.addAll(wikiResult.query!!.pages)
                activity!!.runOnUiThread {
                    adapter.notifyDataSetChanged()
                    refresher?.isRefreshing = false
                }
            })

        }
        catch (ex:Exception){
            val alert = AlertView("Error Exception","$ex", AlertStyle.DIALOG)
            alert.addAction(AlertAction("OK", AlertActionStyle.DEFAULT,{}))
            alert.show(context as AppCompatActivity)
        }

    }
    private fun setuptransition(){
        val slide = Fade()
        slide.setDuration(1000)
        activity!!.window.enterTransition = slide
    }
}
