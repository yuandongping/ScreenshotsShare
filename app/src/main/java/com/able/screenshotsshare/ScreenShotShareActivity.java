package com.able.screenshotsshare;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.able.screensshotssharelibrary.adapter.AppAdapter;
import com.able.screensshotssharelibrary.bean.ShareApp;
import com.able.screensshotssharelibrary.mvp.ScreenShotSharePresenter;
import com.able.screensshotssharelibrary.mvp.ScreenShotSharePresenterImpl;
import com.able.screensshotssharelibrary.mvp.ScreenShotShareView;
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
//            Toast.makeText(this, "未安装"+shareAppList.get(i).appName+"，不能分享", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }else {
                ShareAppUtils.shareToApp(this, shareAppList.get(i).shareClassName, addView(), 640, 160, horizontalListView);
            }
        }
    }


    private View addView() {
        // TODO 动态添加布局(java方式)
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 160);
        LinearLayout view = new LinearLayout(this);
        view.setLayoutParams(lp);//设置布局参数
//        view.setBackgroundColor(Color.rgb(0, 0, 0));
        view.setBackgroundColor(Color.parseColor("#726C7F"));
        view.setOrientation(LinearLayout.HORIZONTAL);// 设置子View的Linearlayout// 为垂直方向布局

        //定义子View中两个元素的布局
        ImageView img01 = new ImageView(this);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(90, 90);
        param.leftMargin = 200;
        param.gravity = Gravity.CENTER;  //必须要加上这句，setMargins才会起作用，而且此句还必须在setMargins下面
        img01.setLayoutParams(param);//设置TextView的布局
        img01.setImageDrawable( ContextCompat.getDrawable(this, R.drawable.wristband_qr_code));
        view.addView(img01);//将TextView 添加到子View 中

        TextView tv2 = new TextView(this);
        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        param2.leftMargin = 20;
        param2.gravity = Gravity.CENTER;  //必须要加上这句，setMargins才会起作用，而且此句还必须在setMargins下面
        tv2.setLayoutParams(param2);
        tv2.setText("Wristband2.0");
        tv2.setTextColor(Color.WHITE);
        tv2.setTextSize(8);
        view.addView(tv2);//将TextView 添加到子View 中
        return view;
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
