package com.liuguangqiang.download;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.liuguangqiang.download.core.AsyncDownload;
import com.liuguangqiang.download.core.DownloadConfiguration;
import com.liuguangqiang.download.core.DownloadListener;
import com.liuguangqiang.download.core.DownloadParams;


public class MainActivity extends Activity {

    private String TAG="AsyncDownload";

    private String filePath;

    private String testUrl = "http://dl.fishsaying.com/fishsaying_1.8.1.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        testDownload();
    }

    private void init(){
        AsyncDownload.getInstance().init(
                new DownloadConfiguration.Builder().newSingleThreadExecutor().build());

        filePath = FileUtils.getSdcardPath() + "/AsyncDownload";
        File file = new File(filePath);
        if (!file.exists()) file.mkdirs();
    }

    private void testDownload() {
        String pathFormat = filePath + "/t%s.apk";
        DownloadParams params;
        String savePath;
        for (int i = 0; i < 2; i++) {
            savePath = String.format(pathFormat, i + 1);
            params = new DownloadParams(testUrl, savePath);
            AsyncDownload.getInstance().download(params, new DownloadListener() {

                @Override
                public void onStart(DownloadParams params) {

                    Log.i("AsyncDownload",params.getSavePath() + " download start");
                }

                @Override
                public void onSuccess(DownloadParams params) {
                    Log.i("AsyncDownload",params.getSavePath() + " download success");
                }

                @Override
                public void onProgressUpdate(int progress, DownloadParams params) {
                    Log.i("AsyncDownload",params.getSavePath() + " progress--->"+progress);
                }
            });
        }
    }

}
