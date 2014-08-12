package com.liuguangqiang.download.core;

import java.util.concurrent.ExecutorService;

import org.apache.http.client.params.ClientPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.net.http.AndroidHttpClient;

/**
 * 
 * AsyncDownload
 *
 * Created by Eric on 2014-8-12.
 */
public class AsyncDownload {

    private int timeoutConnection = Config.DEFAULT_TIMEOUT_CONNECTION;
    private int timeoutSokect = Config.DEFAULT_TIMEOUT_SOCKET;

    private DownloadConfiguration mConfiguration;
    private ExecutorService executorService;
    private AndroidHttpClient mHttpClient;
    private HttpParams mHttpParams;


    protected AsyncDownload() {};


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
        mHttpClient = AndroidHttpClient.newInstance(Config.USER_AGENT);
        mHttpParams = mHttpClient.getParams();
        mHttpParams.setParameter(ClientPNames.HANDLE_REDIRECTS, true);
        HttpConnectionParams.setConnectionTimeout(mHttpParams, timeoutConnection);
        HttpConnectionParams.setSoTimeout(mHttpParams, timeoutSokect);
    }

    private boolean isInited() {
        return mConfiguration != null;
    }

    public void download(DownloadParams params) {
        download(params, null);
    }

    public void download(DownloadParams params, DownloadListener listener) {
        if (!isInited()) {
            throw new IllegalStateException(
                    "AsyncDownload must be inited with configuration before using");
        }
        if (params == null) {
            throw new NullPointerException("call download() with null parameter.");
        } else {
            DownloadTask task = new DownloadTask(mHttpClient, params, listener);
            executorService.submit(task);
        }
    }

}
