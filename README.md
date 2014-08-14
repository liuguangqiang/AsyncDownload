AsyncDownload
=============

A simple libray for download.

###Simple

```Download with AsyncDownload.
            
            AsyncDownload.getInstance().download(params, new DownloadListener() {

                @Override
                public void onStart() {
                    Log.i(TAG, getDownloadParams().getTag() + " download start");
                }

                @Override
                public void onSuccess() {
                    Log.i(TAG, getDownloadParams().getTag() + " download success");
                }

                @Override
                public void onProgressUpdate(int progress) {
                    Log.i(TAG, getDownloadParams().getTag() + " progress：" + progress);
                    tvProgress.setText("progress--->" + progress);
                }

                @Override
                public void onFailure(String msg) {
                    Log.i(TAG, getDownloadParams().getTag() + "下载失败 : " + msg);
                }
            });
```


