package com.able.screensshotssharelibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dongping-yuan on 2017/6/13.
 */

public class FileUtils {

    /**
     * 保存bitmap图片，到本地
     */
    public static Uri saveBitmapToFile(String fileName, Bitmap bitmap) {
        Uri uri = null;
        if (TextUtils.isEmpty(fileName) || bitmap == null) return uri;
        try {
            File sd = Environment.getExternalStorageDirectory();
            File f = new File(sd, fileName);
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            out.flush();
            out.close();
//            Uri uri = Uri.parse(f.getAbsolutePath());
            uri = Uri.fromFile(f);//获得一张图片的Uri
            return uri;
//            shareToWexinCircle(uri);
//            AlbumScan(f.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 刷新相册图库
     */
    public static void albumScan(Context context, String fileName) {
        MediaScannerConnection.scanFile(context.getApplicationContext(), new String[]{fileName}, new String[]{"image/jpeg"}, null);
    }

}
