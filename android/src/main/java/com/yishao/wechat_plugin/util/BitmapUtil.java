package com.yishao.wechat_plugin.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class BitmapUtil {
    public static Bitmap getLocalOrNetBitmap(String url) {
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

    public static byte[] compressBitmap(Bitmap bitmap, int sizeLimit) {
        if (bitmap == null) {
            return  null;
        }

        Bitmap dstBitmap = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] resultArray = null;

        if (byteArrayOutputStream.toByteArray().length/1024 <= sizeLimit) {
            resultArray = byteArrayOutputStream.toByteArray();
        } else {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 1;
            do {
                if (dstBitmap != null && !dstBitmap.isRecycled()) {
                    dstBitmap.recycle();
                }
                opts.inSampleSize = opts.inSampleSize * 2;
                dstBitmap = BitmapFactory.decodeStream(byteArrayInputStream, null, opts);
                byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            } while (bitmap2ByteArray(dstBitmap, false) != null && bitmap2ByteArray(dstBitmap, false).length/1024 > sizeLimit);
        }

        try {
            byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultArray;
    }
}
