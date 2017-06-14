package com.able.screenshotsshare.mvp;

import android.content.Context;

/**
 * Created by dongping-yuan on 2017/6/13.
 */

public interface ScreenShotShareModel {

    void getShareApp(Context context, OnScreenShotShareListener listener);

}
