package hu.nemeth.android.silentmap.view.animations

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.nemeth.android.silentmap.view.callback.ScrollListCallback

class HideFloatButton(
    private val fab: FloatingActionButton,
    private val callBack: ScrollListCallback
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        // if the recycler view is scrolled
        // above hide the FAB
        if (dy > 10 && fab.isShown) {
            callBack.onScrollDownCallBack()
        }

        // if the recycler view is
        // scrolled above show the FAB
        if (dy < -10 && !fab.isShown) {
            callBack.onScrollUpCallBack()
        }

        // of the recycler view is at the first
        // item always show the FAB
        if (!recyclerView.canScrollVertically(-1)) {
            callBack.onScrollUpCallBack()
        }
    }
}