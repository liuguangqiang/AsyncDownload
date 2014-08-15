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
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.net.http.AndroidHttpClient;
import android.util.Log;

/**
 * DownloadTask
 * <p/>
 * Created by Eric on 2014-8-12
 */
public class DownloadTask implements Runnable {

    private AndroidHttpClient mHttpClient;
    private HttpUriRequest httpGet;
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
        if (isCanceled()) return;
        download();
    }

    private boolean download() {
        if (mListener != null) mListener.sendStartMessage();

        if (isCanceled()) return false;

        httpGet = new HttpGet(mParams.getUrl());

        InputStream io = null;
        BufferedInputStream bufferIo = null;
        OutputStream out = null;
        BufferedOutputStream bufferOut = null;

        try {
            httpResponse = mHttpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                totalSize = httpResponse.getEntity().getContentLength();
                File file = new File(mParams.getSavePath());
                io = httpResponse.getEntity().getContent();
                bufferIo = new BufferedInputStream(io);
                out = new FileOutputStream(file);
                bufferOut = new BufferedOutputStream(out);
                byte[] buf = new byte[1];
                int len;
                int percent = 0;
                int lastPercent = -1;
                while ((len = bufferIo.read(buf)) > 0 && !isCanceled()) {
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
                return true;
            } else {
                Log.i("statusCode", "failure code :" + statusCode);
            }
            return false;
        } catch (Exception e) {
            if (mListener != null) {
                if (!isCanceled) {
                    mListener.sendFailureMessage(e.toString());
                }
            }
            return false;
        } finally {
            try {
                bufferOut.flush();
                bufferOut.close();
                out.close();
                io.close();
                bufferIo.close();
            } catch (IOException ex) {
            }
        }
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void cancel() {
        isCanceled = true;
        httpGet.abort();
        mListener.sendCancelMessage();
    }

}
