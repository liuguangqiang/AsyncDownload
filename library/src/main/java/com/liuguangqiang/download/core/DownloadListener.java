package com.liuguangqiang.download.core;

/**
 *
 * DownloadListener
 *
 * Created by Eric on 2014-8-12
 */
public class DownloadListener {

	/**
	 * start to download
	 * 
	 * @param params
	 */
	public void onStart(DownloadParams params) {
	}

	/**
	 * download success
	 * 
	 * @param params
	 */
	public void onSuccess(DownloadParams params) {

	}

	/**
	 * update the progress of download
	 * 
	 * @param progress
	 * @param params
	 */
	public void onProgressUpdate(int progress, DownloadParams params) {

	}

	/**
	 * download failure
	 * 
	 * @param msg
	 * @param params
	 */
	public void onFailure(String msg, DownloadParams params) {

	}

}
