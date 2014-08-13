package com.liuguangqiang.download.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DownloadConfiguration
 * <p/>
 * Created by Eric on 2014-8-12
 */
public final class DownloadConfiguration {

    private ExecutorService executorService;

    private long connectionTimeout = 0;

    private long socketTimeout = 0;

    private DownloadConfiguration(Builder builder) {
        this.executorService = builder.executorService;
        this.connectionTimeout = builder.connectionTimeout;
        this.socketTimeout = builder.socketTimeout;
    }

    public static DownloadConfiguration createDefault() {
        return new Builder().build();
    }

    public static class Builder {

        private ExecutorService executorService;

        private long connectionTimeout;

        private long socketTimeout;

        public DownloadConfiguration build() {
            initDefault();
            return new DownloadConfiguration(this);
        }

        private void initDefault() {
            if (executorService == null) executorService = Executors.newFixedThreadPool(5);
            if (connectionTimeout == 0) connectionTimeout = Constants.DEFAULT_TIMEOUT_CONNECTION;
            if (socketTimeout == 0) socketTimeout = Constants.DEFAULT_TIMEOUT_SOCKET;
        }

        public Builder setConnectionTimeout(long milliseconds) {
            this.connectionTimeout = milliseconds;
            return this;
        }

        public Builder setSocketTimeout(long millisencds) {
            this.socketTimeout = millisencds;
            return this;
        }

        public Builder setExecutorService(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public Builder setSingleThreadExecutor() {
            this.executorService = Executors.newSingleThreadExecutor();
            return this;
        }

        public Builder setCachedThreadPool() {
            this.executorService = Executors.newCachedThreadPool();
            return this;
        }

        public Builder setFixedThreadPool(int nThreads) {
            this.executorService = Executors.newFixedThreadPool(nThreads);
            return this;
        }

    }

}
