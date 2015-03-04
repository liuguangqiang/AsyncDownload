AsyncDownload
=============

A simple library for asynchronous downloading.

##Usage

###Gradle
```
dependencies {
   	compile 'com.github.liuguangqiang:asyncdownload:0.1.0'
   }
```

###Maven
```
<dependency>
  	<groupId>com.github.liuguangqiang</groupId>
  	<artifactId>asyncdownload</artifactId>
  	<version>0.1.0</version>
  	<type>aar</type>
</dependency>
```

###Configuration
    DownloadConfiguration configuration=new DownloadConfiguration.Builder()
            .setFixedThreadPool(5)
            .setSingleThreadExecutor()
            .setCachedThreadPool()
            .setConnectionTimeout(10*1000)
            .setSocketTimeout(60*1000)
            .build();
    AsyncDownload.getInstance().init(configuration);

###Sample
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


