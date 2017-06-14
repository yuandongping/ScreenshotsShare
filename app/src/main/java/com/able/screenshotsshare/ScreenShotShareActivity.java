package com.able.screenshotsshare;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.able.screenshotsshare.adapter.AppAdapter;
import com.able.screenshotsshare.bean.ShareApp;
import com.able.screenshotsshare.mvp.ScreenShotSharePresenter;
import com.able.screenshotsshare.mvp.ScreenShotSharePresenterImpl;
import com.able.screenshotsshare.mvp.ScreenShotShareView;
import com.able.screenshotsshare.utils.BitmapUtils;
import com.able.screenshotsshare.utils.FileUtils;
import com.able.screenshotsshare.utils.ShareAppUtils;
import com.able.screenshotsshare.view.HorizontalListView;

import java.util.ArrayList;

/**
 * 截图，分享
 * http://blog.csdn.net/qq_29078329/article/details/71405493
 * */
public class ScreenShotShareActivity extends AppCompatActivity implements ScreenShotShareView, AdapterView.OnItemClickListener {

    private static final String TAG = ScreenShotShareActivity.class.getSimpleName();
    ArrayList<ShareApp> shareAppList = new ArrayList<>();
    HorizontalListView horizontalListView;
    ScreenShotSharePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot_share);
        initView();
        requestPermissions();
        init();
    }

    /**申请权限*/
    private void requestPermissions() {

    }

    private void initView() {
        horizontalListView = (HorizontalListView) findViewById(R.id.horizontalListView);
        horizontalListView.setOnItemClickListener(this);
    }

    private void init() {
        presenter = new ScreenShotSharePresenterImpl(this);
        presenter.getShareApp(this);
    }

    @Override
    public void setShareApp(ArrayList<ShareApp> shareAppList) {
        this.shareAppList = shareAppList;
        // Make an array adapter using the built in android layout to render a list of strings
        AppAdapter adapter = new AppAdapter(this, shareAppList);

        // Assign adapter to HorizontalListView
        horizontalListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bitmap b = BitmapUtils.shotScreen(ScreenShotShareActivity.this, null, null);
        Uri uri = FileUtils.saveBitmapToFile("wristband" + System.currentTimeMillis() + ".png", b);
        b = null;
        if(!shareAppList.get(i).enable){
            Toast.makeText(this, "未安装"+shareAppList.get(i).appName+"，不能分享", Toast.LENGTH_SHORT).show();
            return;
        }
        ShareAppUtils.shareToApp(this, uri, shareAppList.get(i).shareClassName);
    }

}