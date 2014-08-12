package com.liuguangqiang.download;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class FileUtils {

	public static String getSdcardPath() {
		String result = null;
		try {
			if (Environment.MEDIA_MOUNTED.equals(Environment
					.getExternalStorageState())) {
				result = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
			}
			return result;
		} catch (Exception ex) {
			return result;
		} finally {
			result = null;
		}
	}

	public static void createFolderInSDCard(Context context, String folderPath) {
		File file = null;
		String root = null;
		try {
			root = getSdcardPath();
			if (root != null) {
				file = new File(root, folderPath);
				if (file != null && !file.exists()) {
					file.mkdirs();
				}
			}
		} catch (Exception ex) {
		} finally {
			context = null;
			folderPath = null;
			file = null;
			root = null;
		}
	}

}
