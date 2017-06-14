package com.able.screenshotsshare;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.able.screensshotssharelibrary.adapter.AppAdapter;
import com.able.screensshotssharelibrary.bean.ShareApp;
import com.able.screensshotssharelibrary.mvp.ScreenShotSharePresenter;
import com.able.screensshotssharelibrary.mvp.ScreenShotSharePresenterImpl;
import com.able.screensshotssharelibrary.mvp.ScreenShotShareView;
import com.able.screensshotssharelibrary.utils.BitmapUtils;
import com.able.screensshotssharelibrary.utils.FileUtils;
import com.able.screensshotssharelibrary.utils.ShareAppUtils;
import com.able.screensshotssharelibrary.view.HorizontalListView;

import java.util.ArrayList;

/**
 * 截图，分享
 * http://blog.csdn.net/qq_29078329/article/details/71405493
 * */
public class ScreenShotShareActivity extends AppCompatActivity implements ScreenShotShareView, AdapterView.OnItemClickListener {

    private static final String TAG = ScreenShotShareActivity.class.getSimpleName();

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;


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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
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
        if(!shareAppList.get(i).enable){
            Toast.makeText(this, "未安装"+shareAppList.get(i).appName+"，不能分享", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }else {
                Bitmap b = BitmapUtils.shotScreen(ScreenShotShareActivity.this, null, null);
                Uri uri = FileUtils.saveBitmapToFile("wristband" + System.currentTimeMillis() + ".png", b);
                b = null;
                ShareAppUtils.shareToApp(this, uri, shareAppList.get(i).shareClassName);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                // Permission Denied
                Toast.makeText(ScreenShotShareActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
