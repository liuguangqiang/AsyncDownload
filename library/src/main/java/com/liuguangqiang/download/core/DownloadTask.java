/*
 * Copyright 2014 Eric Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liuguangqiang.download.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.util.Log;

/**
 * DownloadTask
 * <p/>
 * Created by Eric on 2014-8-12
 */
public class DownloadTask implements Runnable {

    private AndroidHttpClient mHttpClient;
    private HttpGet httpGet;
    private HttpResponse httpResponse;
    private long totalSize = 0;
    private int progress = 0;

    private DownloadListener mListener;

    private DownloadParams mParams;

    private boolean isCanceled = false;

    public DownloadTask(AndroidHttpClient httpClient, DownloadParams params,
                        DownloadListener listener) {
        this.mHttpClient = httpClient;
        this.mParams = params;
        this.mListener = listener;
        if (this.mListener != null)
            this.mListener.setDownloadParams(params);
    }

    public DownloadParams getDownloadParams() {
        return mParams;
    }

    @Override
    public void run() {
        download();
    }

    private boolean download() {
        if (mListener != null) mListener.sendStartMessage();
        httpGet = new HttpGet(mParams.getUrl());
        try {
            httpResponse = mHttpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            Log.i("StatusCode", "" + statusCode);
            if (statusCode == HttpStatus.SC_OK) {
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
                        if (mListener != null) mListener.sendUpdateProgressMessage(percent);
                    }
                    if (percent == 100 && mListener != null) {
                        mListener.sendSuccessMessage();
                    }
                }
                bufferOut.flush();
                bufferOut.close();
                out.close();
                io.close();
                bufferIo.close();
                return true;
            } else {
                Log.i("statusCode", "下载失败:" + statusCode);
            }
            return false;
        } catch (Exception e) {
            if (mListener != null) {
                if (isCanceled) {
                    mListener.sendCancelMessage();
                } else {
                    mListener.sendFailureMessage(e.toString());
                }
            }

            return false;
        }
    }

    private void stop(){
        Thread.currentThread().interrupt();
    }

    public void cancel() {
        isCanceled = true;
        if (httpGet != null)
            httpGet.abort();
//        stop();
    }

}
