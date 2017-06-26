package com.able.screensshotssharelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.able.screensshotssharelibrary.R;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dongping-yuan on 2017/6/13.
 *
 * 获取本机可以分享的APP，
 * 获取本机可以分享的APP的包名和activity
 */

public class ShareAppUtils {


    public final static String weChatPackageName = "com.tencent.mm";
    public final static String qqPackageName = "com.tencent.mobileqq";
    public final static String sinaPackageName = "com.sina.weibo";
    public final static String googlePackageName = "com.google.android.talk";
    public final static String facebookPackageName = "com.facebook.orca";
    public final static String twitterPackageName = "com.twitter.android";
    public final static String whatsAppPackageName = "com.whatsapp";

    public final static String weChatShareClassName = "com.tencent.mm.ui.tools.ShareImgUI";
    public final static String weChatCircleShareClassName = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
    public final static String qqShareClassName = "com.tencent.mobileqq.activity.JumpActivity";
    public final static String sinaShareClassName = "com.sina.weibo.composerinde.ComposerDispatchActivity";
    public final static String googleShareClassName = "com.google.android.apps.hangouts.phone.ShareIntentActivity";
    public final static String facebookShareClassName = "com.facebook.messenger.intents.ShareIntentHandler";
    public final static String twitterShareClassName = "com.twitter.android.composer.ComposerActivity";
    public final static String whatsAppShareClassName = "com.whatsapp.ContactPicker";

    private static final String TAG = ShareAppUtils.class.getSimpleName();

    /**查找本机可以分享的app*/
    public static Map<String,ResolveInfo> getShareAppName(Activity mActivity) {
        Map<String,ResolveInfo> appInfo = new HashMap<String, ResolveInfo>();
        List<ResolveInfo> appList = getShareTargets(mActivity);
        String[]items = null;
        if(appList.size()>0) {
            for(int i = 0; i < appList.size(); i++) {
                ResolveInfo tmp_ri = (ResolveInfo)appList.get(i);
                ApplicationInfo apinfo = tmp_ri.activityInfo.applicationInfo;
                String tmp_appName = apinfo.loadLabel(mActivity.getPackageManager()).toString();
                Log.e(TAG, "getShareList"+tmp_appName);
                appInfo.put(tmp_appName, tmp_ri);
            }
        }
        return appInfo;
    }

    private static List<ResolveInfo> getShareTargets(Activity activity) {
        Intent intent=new Intent(Intent.ACTION_SEND,null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("image/*"); //text/plain
        PackageManager pm = activity.getPackageManager();
        return pm.queryIntentActivities(intent,PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    }

    /**获取可以分享的app的界面，和包名。（这个厉害了）*/
    public static void getShareAvaiable(Context context) {
        Intent intent = new Intent(); // 创建分享图片的意图
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
//        通过PackageManager查询到所有的能分享图片的Acitivity
        List<ResolveInfo> resolveInfos = ((Activity) context).getPackageManager().queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER);
        List<ResolveInfo> activityInfos = new ArrayList<ResolveInfo>();
        for (ResolveInfo resolveInfo : resolveInfos) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (null != activityInfo) {
                String activityName = activityInfo.name;
                String packageName = activityInfo.packageName;
                Log.e(TAG, "packageName="+packageName+"-----activityName="+activityName);
                if (null != packageName) {
                    activityInfos.add(resolveInfo);
                }
            }
        }
    }

    //判断是否安装
    public static boolean isSheAvaiable(Context context, String pName) {
        Intent intent = new Intent(); // 创建分享图片的意图
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
// 通过PackageManager查询到所有的能分享图片的Acitivity
        List<ResolveInfo> resolveInfos = ((Activity) context)
                .getPackageManager().queryIntentActivities(intent,
                        PackageManager.GET_RESOLVED_FILTER);
        List<ResolveInfo> activityInfos = new ArrayList<ResolveInfo>();
        for (ResolveInfo resolveInfo : resolveInfos) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (null != activityInfo) {
                String activityName = activityInfo.name;
                String packageName = activityInfo.packageName;
                // 通过包名对比判断是否相应的程序
                if (null != packageName && packageName.equals(pName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void shareToApp(Activity activity, String shareClassName) {
        Bitmap b = BitmapUtils.shotScreen(activity, null, null);
        Uri uri = FileUtils.saveBitmapToFile("wristband" + System.currentTimeMillis() + ".png", b);
        b = null;
        if(TextUtils.equals(weChatShareClassName, shareClassName)){
            shareToWeChatFriend(activity, uri);
        }else if(TextUtils.equals(weChatCircleShareClassName, shareClassName)){
            shareToWeChatCircle(activity, uri);
        }else if(TextUtils.equals(qqShareClassName, shareClassName)){
            shareToQQFriend(activity, uri);
        }else if(TextUtils.equals(sinaShareClassName, shareClassName)){
            shareToSina(activity, uri);
        }else if(TextUtils.equals(facebookShareClassName, shareClassName)){
            shareToFacebook(activity, uri);
        }else if(TextUtils.equals(twitterShareClassName, shareClassName)){
            shareToTwitter(activity, uri);
        }else if(TextUtils.equals(whatsAppShareClassName, shareClassName)){
            shareToWhatsApp(activity, uri);
        }else if(TextUtils.equals("more", shareClassName)){
            shareToAll(activity, uri);
        }else if(TextUtils.equals("download", shareClassName)){
            try {
                File file = new File(new URI(uri.toString()));
                FileUtils.albumScan(activity, file.getAbsolutePath());
                Toast.makeText(activity, activity.getResources().getString(R.string.image_saved_successfully), Toast.LENGTH_SHORT).show();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

    }

    /**微信朋友好友*/
    private static void shareToWeChatFriend(Context context, Uri uri) {
        Intent sendIntent = new Intent();
        sendIntent.setClassName(weChatPackageName, weChatShareClassName);//微信朋友
        publicShare(sendIntent, context, uri);
    }

    /**微信收藏*/
    private static void shareToWeChatAddFavoriteUI(Context context, Uri uri) {
        Intent sendIntent = new Intent();
        sendIntent.setClassName(weChatPackageName, "com.tencent.mm.ui.tools.AddFavoriteUI");//微信收藏
        publicShare(sendIntent, context, uri);
    }

    /**微信朋友圈*/
    private static void shareToWeChatCircle(Context context, Uri uri) {
        Intent sendIntent = new Intent();
        sendIntent.setClassName(weChatPackageName, weChatCircleShareClassName);//微信朋友圈，仅支持分享图片
        publicShare(sendIntent, context, uri);
    }

    /**QQ好友*/
    private static void shareToQQFriend(Context context, Uri uri) {
        Intent sendIntent = new Intent();
        sendIntent.setClassName(qqPackageName, qqShareClassName);//QQ好友或QQ群
//        sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qqfav.widget.QfavJumpActivity");//保存到QQ收藏
//        sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qlink.QlinkShareJumpActivity");//QQ面对面快传
//        sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.qfileJumpActivity");//传给我的电脑
        publicShare(sendIntent, context, uri);
    }

    /**sina*/
    private static void shareToSina(Context context, Uri uri) {
        Intent sendIntent = new Intent();
        sendIntent.setClassName(sinaPackageName, sinaShareClassName);//sina 微博
        publicShare(sendIntent, context, uri);
    }

    /**Google,环聊*/
    private static void shareToGoogle(Context context, Uri uri) {
        Intent sendIntent = new Intent();
        sendIntent.setClassName(googlePackageName, googleShareClassName);
        publicShare(sendIntent, context, uri);
    }

    /**facebook*/
    public static void shareToFacebook(Context context, Uri uri) {
        Intent sendIntent = new Intent();
        sendIntent.setClassName(facebookPackageName, facebookShareClassName);
        publicShare(sendIntent, context, uri);
    }

    /**twitter*/
    private static void shareToTwitter(Context context, Uri uri) {
        Intent sendIntent = new Intent();
        sendIntent.setClassName(twitterPackageName, twitterShareClassName);
        publicShare(sendIntent, context, uri);
    }

    /**whatsApp*/
    private static void shareToWhatsApp(Context context, Uri uri) {
        Intent sendIntent = new Intent();
        sendIntent.setClassName(whatsAppPackageName, whatsAppShareClassName);
        publicShare(sendIntent, context, uri);
    }

    /**all*/
    private static void shareToAll(Context context, Uri uri) {
        Intent sendIntent = new Intent();
        publicShare(sendIntent, context, uri);
    }

    private static void publicShare(Intent sendIntent, Context context, Uri uri) {
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.setType("image/jpg");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);//uri为你要分享的图片的uri
        context.startActivity(sendIntent);
    }


//    sina微博
//packageName=com.sina.weibo-----activityName=com.sina.weibo.composerinde.ComposerDispatchActivity

//    微信
//packageName=com.tencent.mm-----activityName=com.tencent.mm.ui.tools.ShareImgUI
//packageName=com.tencent.mm-----activityName=com.tencent.mm.ui.tools.AddFavoriteUI
//packageName=com.tencent.mm-----activityName=com.tencent.mm.ui.tools.ShareToTimeLineUI

//    QQ
//packageName=com.tencent.mobileqq-----activityName=com.tencent.mobileqq.activity.JumpActivity
//packageName=com.tencent.mobileqq-----activityName=com.tencent.mobileqq.activity.qfileJumpActivity
//packageName=com.tencent.mobileqq-----activityName=cooperation.qlink.QlinkShareJumpActivity
//packageName=com.tencent.mobileqq-----activityName=cooperation.qqfav.widget.QfavJumpActivity

//    Google
//packageName=com.google.android.talk-----activityName=com.google.android.apps.hangouts.phone.ShareIntentActivity
//packageName=com.google.android.apps.photos-----activityName=com.google.android.apps.photos.uploadtoalbum.UploadContentActivity
//packageName=com.google.android.apps.maps-----activityName=com.google.android.maps.MapsActivity
//packageName=com.google.android.gm-----activityName=com.google.android.gm.ComposeActivityGmailExternal
//packageName=com.google.android.apps.docs-----activityName=com.google.android.apps.docs.shareitem.UploadMenuActivity

//    facebook
//packageName=com.facebook.orca-----activityName=com.facebook.messenger.intents.ShareIntentHandler
//packageName=com.facebook.katana-----activityName=com.facebook.composer.shareintent.ImplicitShareIntentHandlerDefaultAlias
//packageName=com.facebook.katana-----activityName=com.facebook.timeline.stagingground.ProfilePictureShareActivityAlias

//    twitter
//packageName=com.twitter.android-----activityName=com.twitter.android.composer.ComposerActivity
//packageName=com.twitter.android-----activityName=com.twitter.app.dm.DMActivity

//    whatsapp
//packageName=com.whatsapp-----activityName=com.whatsapp.ContactPicker


}
