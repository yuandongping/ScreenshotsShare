package com.able.screensshotssharelibrary.mvp;

import android.content.Context;

import com.able.screensshotssharelibrary.bean.ShareApp;

import java.util.ArrayList;

/**
 * Created by dongping-yuan on 2017/6/13.
 */

public class ScreenShotSharePresenterImpl implements ScreenShotSharePresenter, OnScreenShotShareListener {

    ScreenShotShareView screenShotShareView;
    ScreenShotShareModel screenShotShareModel;

    public ScreenShotSharePresenterImpl(ScreenShotShareView screenShotShareView){
        this.screenShotShareView = screenShotShareView;
        screenShotShareModel = new ScreenShotShareModelImpl();
    }

    @Override
    public void getShareApp(Context context) {
        screenShotShareModel.getShareApp(context, this);
    }

    @Override
    public void setShareApp(ArrayList<ShareApp> shareAppList) {
        screenShotShareView.setShareApp(shareAppList);
    }
}
