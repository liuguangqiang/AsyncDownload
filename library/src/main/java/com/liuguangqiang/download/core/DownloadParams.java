package com.liuguangqiang.download.core;

/**
 *
 * DownloadParams
 *
 * Created by Eric on 2014-8-12
 */
public class DownloadParams {

    private String url;

    private String savePath;

    private String tag;

    public DownloadParams() {
    }

    public DownloadParams(String url, String savePath) {
        this.url = url;
        this.savePath = savePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
