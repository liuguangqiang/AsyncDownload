package com.liuguangqiang.download.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;

/**
 * 
 * DownloadTask
 *
 * Created by Eric on 2014年8月12日
 */
public class DownloadTask implements Runnable {

    private AndroidHttpClient mHttpClient;
    private HttpGet httpGet;
    private HttpResponse httpResponse;
    private long totalSize = 0;
    private int progress = 0;

    private DownloadListener mListener;

    private DownloadParams mParams;


    public DownloadTask(AndroidHttpClient httpClient, DownloadParams params,
            DownloadListener listener) {
        this.mHttpClient = httpClient;
        this.mParams = params;
        this.mListener = listener;
    }

    @Override
    public void run() {
        download();
    }

    private boolean download() {
        if (mListener != null) mListener.onStart(mParams);
        httpGet = new HttpGet(mParams.getUrl());
        try {
            httpResponse = mHttpClient.execute(httpGet);
            totalSize = httpResponse.getEntity().getContentLength();
            File file = new File(mParams.getSavePath());
            InputStream io = httpResponse.getEntity().getContent();
            BufferedInputStream bufferIo = new BufferedInputStream(io);
            OutputStream out = new FileOutputStream(file);
            BufferedOutputStream bufferOut = new BufferedOutputStream(out);
            byte[] buf = new byte[1];
            int len;
            int percent = 0;
            int lastPercent = -1;
            while ((len = bufferIo.read(buf)) > 0) {
                bufferOut.write(buf, 0, len);
                progress++;
                percent = (int) (progress * 100 / totalSize);
                if (percent > lastPercent) {
                    lastPercent = percent;
                    if (mListener != null) mListener.onProgressUpdate(percent, mParams);
                }
                if (percent == 100 && mListener != null) {
                    mListener.onSuccess(mParams);
                }
            }
            bufferOut.flush();
            bufferOut.close();
            out.close();
            io.close();
            bufferIo.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (mListener != null) mListener.onFailure(e.getMessage(), mParams);
            return false;
        }  
    }

}
