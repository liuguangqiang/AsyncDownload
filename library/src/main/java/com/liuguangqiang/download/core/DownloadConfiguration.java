package com.liuguangqiang.download.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * DownloadConfiguration
 *
 * Created by Eric on 2014-8-12
 */
public final class DownloadConfiguration {

    final ExecutorService executorService;

    private DownloadConfiguration(Builder builder) {
        this.executorService = builder.executorService;
    }

    public static DownloadConfiguration createDefault() {
        return new Builder().build();
    }

    public static class Builder {

        private ExecutorService executorService;

        public DownloadConfiguration build() {
            initWithDefault();
            return new DownloadConfiguration(this);
        }

        private void initWithDefault() {
            if (executorService == null) executorService = Executors.newFixedThreadPool(5);
        }

        public Builder setExecutorService(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public Builder newSingleThreadExecutor() {
            this.executorService = Executors.newSingleThreadExecutor();
            return this;
        }

        public Builder newCachedThreadPool() {
            this.executorService = Executors.newCachedThreadPool();
            return this;
        }

        public Builder newFixedThreadPool(int nThreads) {
            this.executorService = Executors.newFixedThreadPool(nThreads);
            return this;
        }

    }

}
