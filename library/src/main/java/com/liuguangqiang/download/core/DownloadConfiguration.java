/*
 * Copyright 2014 Eric Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liuguangqiang.download.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DownloadConfiguration
 * <p/>
 * Created by Eric on 2014-8-12
 */
public final class DownloadConfiguration {

    final ExecutorService executorService;

    public int connectionTimeout=0;

    public int socketTimeout=0;

    private DownloadConfiguration(Builder builder) {
        this.executorService = builder.executorService;
        this.connectionTimeout = builder.connectionTimeout;
        this.socketTimeout = builder.socketTimeout;
    }

    /**
     * Return default DownloadConfiguration.
     * @return
     */
    public static DownloadConfiguration createDefault() {
        return new Builder().build();
    }

    public static class Builder {

        private ExecutorService executorService;

        private int connectionTimeout;

        private int socketTimeout;

        public DownloadConfiguration build() {
            initDefault();
            return new DownloadConfiguration(this);
        }

        private void initDefault() {
            if (executorService == null) executorService = Executors.newFixedThreadPool(5);
            if (connectionTimeout == 0) connectionTimeout = Constants.DEFAULT_TIMEOUT_CONNECTION;
            if (socketTimeout == 0) socketTimeout = Constants.DEFAULT_TIMEOUT_SOCKET;
        }

        public Builder setConnectionTimeout(int milliseconds) {
            this.connectionTimeout = milliseconds;
            return this;
        }

        public Builder setSocketTimeout(int millisencds) {
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
