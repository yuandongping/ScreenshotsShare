package com.able.screenshotsshare.mvp;

import com.able.screenshotsshare.bean.ShareApp;

import java.util.ArrayList;

/**
 * Created by dongping-yuan on 2017/6/14.
 */

public interface OnScreenShotShareListener {

    void setShareApp(ArrayList<ShareApp> shareAppList);
}
