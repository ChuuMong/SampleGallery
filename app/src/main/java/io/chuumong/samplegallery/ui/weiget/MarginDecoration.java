package io.chuumong.samplegallery.ui.weiget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.chuumong.samplegallery.R;


/**
 * Created by LeeJongHun on 2016-06-22.
 */
public class MarginDecoration extends RecyclerView.ItemDecoration {

    private int margin;

    public MarginDecoration(Context context) {
        this.margin = context.getResources().getDimensionPixelSize(R.dimen.item_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(margin, margin, margin, margin);
    }
}
