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

import android.os.Handler;
import android.os.Message;

/**
 * AbstractDownloadListener
 * <p/>
 * Created by Eric on 14-8-13.
 */
public abstract class AbstractDownloadListener {

    public static final int MESSAGE_START = 1;

    public static final int MESSAGE_SUCCESS = 2;

    public static final int MESSAGE_FINISH = 3;

    public static final int MESSAGE_FAILURE = 4;

    public static final int MESSAGE_UPDATE_PROGRESS = 5;

    private ResponseHandler mHandler;

    public AbstractDownloadListener() {
        if (mHandler == null) {
            mHandler = new ResponseHandler(this);
        }
    }

    public void sendStartMessage() {
        sendMessage(MESSAGE_START);
    }

    public void sendFailureMessage(String error) {
        sendMessage(MESSAGE_FAILURE, error);
    }

    public void sendUpdateProgressMessage(int progress) {
        sendMessage(MESSAGE_UPDATE_PROGRESS, progress);
    }

    public void sendFinishMessage() {
        sendMessage(MESSAGE_FINISH);
    }

    public void sendSuccessMessage() {
        sendMessage(MESSAGE_SUCCESS);
    }

    private void sendMessage(int what) {
        sendMessage(what, null);
    }

    private void sendMessage(int what, Object obj) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case MESSAGE_START:
                onStart();
                break;
            case MESSAGE_SUCCESS:
                onSuccess();
                sendFinishMessage();
                break;
            case MESSAGE_FAILURE:
                if (msg.obj != null) {
                    onFailure(msg.obj.toString());
                }
                sendFinishMessage();
                break;
            case MESSAGE_UPDATE_PROGRESS:
                if (msg.obj != null)
                    onProgressUpdate((Integer) msg.obj);
                break;
            case MESSAGE_FINISH:
                onFinish();
                break;
        }
    }

    private static class ResponseHandler extends Handler {

        private final AbstractDownloadListener listener;

        public ResponseHandler(AbstractDownloadListener listener) {
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            if (this.listener != null) this.listener.handleMessage(msg);
        }
    }

    /**
     * start to download
     */
    public abstract void onStart();

    /**
     * download success
     */
    public abstract void onSuccess();

    /**
     * update the progress of download
     *
     * @param progress
     */
    public abstract void onProgressUpdate(int progress);

    /**
     * download failure
     *
     * @param msg
     */
    public abstract void onFailure(String msg);

    public abstract void onFinish();


}
