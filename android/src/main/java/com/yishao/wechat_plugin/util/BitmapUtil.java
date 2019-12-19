package com.yishao.wechat_plugin.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class BitmapUtil {
    public static Bitmap gtLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new FileInputStream(new File(url)), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();

            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

            in.close();
            out.close();
            dataStream.close();

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in = null;
            }
            if (out != null) {
                out = null;
            }
        }
        return bitmap;
    }

    public static byte[] bitmap2ByteArray(final Bitmap bitmap, final boolean needRecycle) {
        if (bitmap == null) {
            return null;
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, output);

        if (needRecycle) {
            bitmap.recycle();
        }

        byte[] result = output.toByteArray();

        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
