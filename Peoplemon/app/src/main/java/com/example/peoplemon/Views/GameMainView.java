package com.example.peoplemon.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by JoshuaMabry on 11/7/16.
 */

public class GameMainView extends RelativeLayout {
    private Context context;

    public GameMainView(Context context, AttributeSet attrs, Context context1) {
        super(context, attrs);
        this.context = context1;
    }
}
