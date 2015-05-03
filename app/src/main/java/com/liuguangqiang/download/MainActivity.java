package com.liuguangqiang.download;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.liuguangqiang.download.core.AsyncDownload;
import com.liuguangqiang.download.core.DownloadConfiguration;
import com.liuguangqiang.download.core.DownloadListener;
import com.liuguangqiang.download.core.DownloadParams;


public class MainActivity extends Activity {

    private String TAG = "AsyncDownload";

    private String filePath;

    private String testUrl = "http://dl.fishsaying.com/fishsaying_2.1.0.apk";

    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvProgress = (TextView) this.findViewById(R.id.tv_progress);
        init();
        testDownload();
    }

    private void init() {
        AsyncDownload.getInstance().init(
                new DownloadConfiguration.Builder().setSingleThreadExecutor().build());

        filePath = FileUtils.getSdcardPath() + "/AsyncDownload";
        File file = new File(filePath);
        if (!file.exists()) file.mkdirs();
    }

    DownloadParams params;

    private void testDownload() {
        String pathFormat = filePath + "/t%s.apk";

        String savePath;
        for (int i = 0; i < 100; i++) {
            savePath = String.format(pathFormat, i + 1);
            params = new DownloadParams(testUrl + "?temp=" + i, savePath);
            params.setTag("Tag" + (i + 1));
            AsyncDownload.getInstance().download(params, new DownloadListener() {

                @Override
                public void onStart() {
                    Log.i(TAG, getDownloadParams().getTag() + " onStart");
                }

                @Override
                public void onSuccess() {
                    Log.i(TAG, getDownloadParams().getTag() + " onSuccess");
                }

                @Override
                public void onProgressUpdate(int progress) {
//                    Log.i(TAG, getDownloadParams().getTag() + " progress：" + progress);
                    tvProgress.setText("progress--->" + progress);
                    if (progress >= 5) {
                        AsyncDownload.getInstance().cancel(getDownloadParams().getUrl());
                    }
                }

                @Override
                public void onFailure(String msg) {
                    Log.i(TAG, getDownloadParams().getTag() + " onFailure : " + msg);
                }

                @Override
                public void onFinish() {
                    Log.i(TAG, getDownloadParams().getTag() + " onFinish");
                }

                @Override
                public void onCancel() {
                    Log.i(TAG, getDownloadParams().getTag() + " onCancel");
                }
            });
        }
    }

}
