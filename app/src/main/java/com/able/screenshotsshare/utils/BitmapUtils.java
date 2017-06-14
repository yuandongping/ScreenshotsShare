package com.able.screenshotsshare.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.View;

/**
 * Created by dongping-yuan on 2017/6/13.
 */

public class BitmapUtils {

    /**
     * 1 Activity截图（带空白的状态栏）
     */
    public static Bitmap shotScreen(Activity activity, View addView, View removeView) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bitmap;
    }

    /**
     * 2 Activity截图（去掉状态栏）
     */
    public static Bitmap shotActivityNoBar(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();

        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();

        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);

        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;
    }

    /**
     * 3 Fragment截图
     */
    public Bitmap getFragmentBitmap(Fragment fragment) {
        View v = fragment.getView();
        v.buildDrawingCache(false);
        return v.getDrawingCache();
    }

    /**
     * 合并bitmap
     */
    public Bitmap combineBitmapInCenter(Bitmap background, Bitmap midBitmap, Bitmap foreground) {
        if (!background.isMutable()) {
            background = background.copy(Bitmap.Config.ARGB_8888, true);
        }
        Paint paint = new Paint();
        Canvas canvas = new Canvas(background);
        int bw = background.getWidth();
        int bh = background.getHeight();

        int mw = midBitmap.getWidth();
        int mh = midBitmap.getHeight();
        int mx = (mw - bw) / 2;
        int my = (mh - bh) / 2;
        canvas.drawBitmap(midBitmap, mx, my, paint);

        int fw = foreground.getWidth();
        int fh = foreground.getHeight();
        int fx = (fw - bw) / 2;
        int fy = (fh - bh) / 2;
        canvas.drawBitmap(foreground, fx, fy, paint);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return background;
    }
}
