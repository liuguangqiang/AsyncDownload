AsyncDownload
=============

A simple library for asynchronous downloading.

##Configuration
    DownloadConfiguration configuration=new DownloadConfiguration.Builder()
            .setFixedThreadPool(5)
            .setSingleThreadExecutor()
            .setCachedThreadPool()
            .setConnectionTimeout(10*1000)
            .setSocketTimeout(60*1000)
            .build();
    AsyncDownload.getInstance().init(configuration);

##Sample
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

## License

    Copyright 2014 Eric Liu

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
