AsyncDownload
=============

A simple library for asynchronous downloading.

###Configuration
    DownloadConfiguration configuration=new DownloadConfiguration.Builder()
            .setFixedThreadPool(5)
            .setSingleThreadExecutor()
            .setCachedThreadPool()
            .setConnectionTimeout(10*1000)
            .setSocketTimeout(60*1000)
            .build();
    AsyncDownload.getInstance().init(configuration);

###Simple
    //Download with AsyncDownload
    AsyncDownload.getInstance().download(params, new DownloadListener() {

        @Override
        public void onStart() {
        }

        @Override
        public void onSuccess() {
        }

        @Override
        public void onProgressUpdate(int progress) {
        }

        @Override
        public void onFailure(String msg) {
        }
        
        @Override
        public void onCancel() {                           
        }
        
        @Override
        public void onFinish() {                           
        }
        
    });


