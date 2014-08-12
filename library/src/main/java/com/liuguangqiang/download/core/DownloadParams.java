package com.liuguangqiang.download.core;

public class DownloadParams {

    private String url;

    private String savePath;

    public DownloadParams() {}

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

}
