package Adapters

import Fragments.ButtonNavigationFragments.Explorefragment
import Fragments.ButtonNavigationFragments.HistoryFragment
import Fragments.ButtonNavigationFragments.favouriteFragment
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter

class ScreenSlidePagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager){
    override fun getItem(count: Int): Fragment {
        var fragment:Fragment?= null
        when(count){
            0 -> {
                fragment = Explorefragment()
            }
            1 -> {
                fragment = favouriteFragment()
            }
            2 -> {
                fragment = HistoryFragment()
            }
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return 3
    }

    override fun saveState(): Parcelable? {
        return super.saveState()
    }

}