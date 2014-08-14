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

import java.util.concurrent.ExecutorService;

import org.apache.http.client.params.ClientPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.net.http.AndroidHttpClient;

/**
 * Singleton for downloading.
 * <p/>
 * Created by Eric on 2014-8-12
 */
public class AsyncDownload {

    private DownloadConfiguration mConfiguration;
    private ExecutorService executorService;
    private AndroidHttpClient mHttpClient;
    private HttpParams mHttpParams;


    private AsyncDownload() {
    }

    ;


    private volatile static AsyncDownload instance;

    /**
     * Return singleton.
     *
     * @return
     */
    public static AsyncDownload getInstance() {
        if (instance == null) {
            synchronized (AsyncDownload.class) {
                if (instance == null) {
                    instance = new AsyncDownload();
                }
            }
        }
        return instance;
    }

    public synchronized void init(DownloadConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException(
                    "AsyncDownload configuration can not be initialized with null");
        } else if (mConfiguration == null) {
            mConfiguration = configuration;
            init();
        }
    }

    private void init() {
        executorService = mConfiguration.executorService;
        mHttpClient = AndroidHttpClient.newInstance(Constants.USER_AGENT);
        mHttpParams = mHttpClient.getParams();
        mHttpParams.setParameter(ClientPNames.HANDLE_REDIRECTS, true);
        HttpConnectionParams.setConnectionTimeout(mHttpParams, mConfiguration.connectionTimeout);
        HttpConnectionParams.setSoTimeout(mHttpParams, mConfiguration.socketTimeout);
    }

    private boolean isInited() {
        return mConfiguration != null;
    }

    public void download(DownloadParams params) {
        download(params, null);
    }

    /**
     * start download.
     *
     * @param params
     * @param listener
     */
    public void download(DownloadParams params, DownloadListener listener) {
        if (!isInited()) {
            init(DownloadConfiguration.createDefault());
        }
        if (params == null) {
            throw new NullPointerException("DownloadParams must not be null.");
        } else {
            DownloadTask task = new DownloadTask(mHttpClient, params, listener);
            executorService.submit(task);
        }
    }

}
