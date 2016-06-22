package com.motionblue.samplegallery.ui.weiget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by LeeJongHun on 2016-06-22.
 */
public class HeightImageView extends ImageView {

    public HeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        // 가로와 동일한 사이즈를 가져옴
        int height = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthSpec), MeasureSpec.EXACTLY);
        super.onMeasure(widthSpec, height);
    }
}
