AsyncDownload
=============

A simple libray for download.

###Simple
    //Download with AsyncDownload
    AsyncDownload.getInstance().download(params, new DownloadListener() {

        @Override
        public void onStart() {
            Log.i(TAG, getDownloadParams().getTag() + " start");
        }

        @Override
        public void onSuccess() {
            Log.i(TAG, getDownloadParams().getTag() + " success");
        }

        @Override
        public void onProgressUpdate(int progress) {
            Log.i(TAG, getDownloadParams().getTag() + " progress :" + progress); 
        }

        @Override
        public void onFailure(String msg) {
            Log.i(TAG, getDownloadParams().getTag() + " failure : " + msg);
        }
    });


