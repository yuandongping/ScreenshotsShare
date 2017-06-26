package com.able.screensshotssharelibrary.mvp;

import android.content.Context;

import com.able.screensshotssharelibrary.R;
import com.able.screensshotssharelibrary.bean.ShareApp;
import com.able.screensshotssharelibrary.utils.ShareAppUtils;

import java.util.ArrayList;

/**
 * Created by dongping-yuan on 2017/6/14.
 */
public class ScreenShotShareModelImpl implements ScreenShotShareModel {

    @Override
    public void getShareApp(Context context, OnScreenShotShareListener listener) {
        ArrayList<ShareApp> shareAppList = new ArrayList<>();
        ShareApp shareAppItem01 = new ShareApp();
        shareAppItem01.appName = context.getResources().getString(R.string.wechat);
        shareAppItem01.packageName = ShareAppUtils.weChatPackageName;
        shareAppItem01.shareClassName = ShareAppUtils.weChatShareClassName;
        shareAppItem01.appIcon = R.drawable.wechat;
        shareAppItem01.enable = ShareAppUtils.isSheAvaiable(context, shareAppItem01.packageName);
        if(shareAppItem01.enable){
            shareAppItem01.appIconBg = R.drawable.drawable_round_wechat;
        }else {
            shareAppItem01.appIconBg = R.drawable.drawable_round_gray;
        }

        ShareApp shareAppItem02 = new ShareApp();
        shareAppItem02.appName = context.getResources().getString(R.string.wechat_circle);
        shareAppItem02.packageName = ShareAppUtils.weChatPackageName;
        shareAppItem02.shareClassName = ShareAppUtils.weChatCircleShareClassName;
        shareAppItem02.appIcon = R.drawable.wechat_circle;
        shareAppItem02.enable = shareAppItem01.enable;
        if(shareAppItem02.enable){
            shareAppItem02.appIconBg = R.drawable.drawable_round_wechat_circle;
        }else {
            shareAppItem02.appIconBg = R.drawable.drawable_round_gray;
        }

        ShareApp shareAppItem03 = new ShareApp();
        shareAppItem03.appName = context.getResources().getString(R.string.qq);
        shareAppItem03.packageName = ShareAppUtils.qqPackageName;
        shareAppItem03.shareClassName = ShareAppUtils.qqShareClassName;
        shareAppItem03.appIcon = R.drawable.qq;
        shareAppItem03.enable = ShareAppUtils.isSheAvaiable(context, shareAppItem03.packageName);
        if(shareAppItem03.enable){
            shareAppItem03.appIconBg = R.drawable.drawable_round_qq;
        }else {
            shareAppItem03.appIconBg = R.drawable.drawable_round_gray;
        }

        ShareApp shareAppItem04 = new ShareApp();
        shareAppItem04.appName = context.getResources().getString(R.string.sina);
        shareAppItem04.packageName = ShareAppUtils.sinaPackageName;
        shareAppItem04.shareClassName = ShareAppUtils.sinaShareClassName;
        shareAppItem04.appIcon = R.drawable.sina;
        shareAppItem04.enable = ShareAppUtils.isSheAvaiable(context, shareAppItem04.packageName);
        if(shareAppItem04.enable){
            shareAppItem04.appIconBg = R.drawable.drawable_round_sina;
        }else {
            shareAppItem04.appIconBg = R.drawable.drawable_round_gray;
        }

        ShareApp shareAppItem05 = new ShareApp();
        shareAppItem05.appName = context.getResources().getString(R.string.facebook);
        shareAppItem05.packageName = ShareAppUtils.facebookPackageName;
        shareAppItem05.shareClassName = ShareAppUtils.facebookShareClassName;
        shareAppItem05.appIcon = R.drawable.facebook;
        shareAppItem05.enable = ShareAppUtils.isSheAvaiable(context, shareAppItem05.packageName);
        if(shareAppItem05.enable){
            shareAppItem05.appIconBg = R.drawable.drawable_round_facebook;
        }else {
            shareAppItem05.appIconBg = R.drawable.drawable_round_gray;
        }

        ShareApp shareAppItem06 = new ShareApp();
        shareAppItem06.appName = context.getResources().getString(R.string.twitter);
        shareAppItem06.packageName = ShareAppUtils.twitterPackageName;
        shareAppItem06.shareClassName = ShareAppUtils.twitterShareClassName;
        shareAppItem06.appIcon = R.drawable.twitter;
        shareAppItem06.enable = ShareAppUtils.isSheAvaiable(context, shareAppItem06.packageName);
        if(shareAppItem06.enable){
            shareAppItem06.appIconBg = R.drawable.drawable_round_twitter;
        }else {
            shareAppItem06.appIconBg = R.drawable.drawable_round_gray;
        }

        ShareApp shareAppItem07 = new ShareApp();
        shareAppItem07.appName = context.getResources().getString(R.string.whatsapp);
        shareAppItem07.packageName = ShareAppUtils.whatsAppPackageName;
        shareAppItem07.shareClassName = ShareAppUtils.whatsAppShareClassName;
        shareAppItem07.appIcon = R.drawable.whatsapp;
        shareAppItem07.enable = ShareAppUtils.isSheAvaiable(context, shareAppItem07.packageName);
        if(shareAppItem07.enable){
            shareAppItem07.appIconBg = R.drawable.drawable_round_whatsapp;
        }else {
            shareAppItem07.appIconBg = R.drawable.drawable_round_gray;
        }


        ShareApp shareAppItem08 = new ShareApp();
        shareAppItem08.appName = context.getResources().getString(R.string.more);
        shareAppItem08.packageName = "more";
        shareAppItem08.shareClassName = "more";
        shareAppItem08.appIcon = R.drawable.more;
        shareAppItem08.enable = true;
        shareAppItem08.appIconBg = R.drawable.drawable_round_more;


        ShareApp shareAppItem09 = new ShareApp();
        shareAppItem09.appName = context.getResources().getString(R.string.download);
        shareAppItem09.packageName = "download";
        shareAppItem09.shareClassName = "download";
        shareAppItem09.appIcon = R.drawable.download;
        shareAppItem09.enable = true;
        shareAppItem09.appIconBg = R.drawable.drawable_round_download;

        shareAppList.add(shareAppItem01);
        shareAppList.add(shareAppItem02);
        shareAppList.add(shareAppItem03);
        shareAppList.add(shareAppItem04);
        shareAppList.add(shareAppItem05);
        shareAppList.add(shareAppItem06);
        shareAppList.add(shareAppItem07);
        shareAppList.add(shareAppItem08);
        shareAppList.add(shareAppItem09);
        listener.setShareApp(shareAppList);

    }

}
