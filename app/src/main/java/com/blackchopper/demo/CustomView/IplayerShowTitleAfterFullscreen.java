package com.blackchopper.demo.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.blackchopper.iplayer.DataSource;
import com.blackchopper.iplayer.Iplayer;

/**
 * Created by Nathen
 * On 2016/04/27 10:49
 */
public class IplayerShowTitleAfterFullscreen extends Iplayer {
    public IplayerShowTitleAfterFullscreen(Context context) {
        super(context);
    }

    public IplayerShowTitleAfterFullscreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setUp(DataSource jzDataSource, int screen) {
        super.setUp(jzDataSource, screen);
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            titleTextView.setVisibility(View.VISIBLE);
        } else {
            titleTextView.setVisibility(View.INVISIBLE);
        }
    }
}
