package com.acg12.lib.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * @ClassName: CacheUtils
 * @Description: 存储路径 内存中
 * @author 
 * @date
 */
public class CacheUtils {

	private static final String TAG = "CacheUtils";
	private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

	public static File getFileDirectory(Context context) {
		File appCacheDir = null;
		if (appCacheDir == null) {
			appCacheDir = context.getFilesDir();
		}
		if (appCacheDir == null) {
			String cacheDirPath = "/data/data/" + context.getPackageName()+ "/files/";
			appCacheDir = new File(cacheDirPath);
		}
		return appCacheDir;
	}

	public static File getCacheDirectory(Context context, String dirName) {
		return getCacheDirectory(context , true , dirName);
	}

	public static File getCacheDirectory(Context context, boolean preferExternal, String dirName) {
		File appCacheDir = null;
		if (preferExternal&& MEDIA_MOUNTED.equals(Environment.getExternalStorageState())&& hasExternalStoragePermission(context)) {
			appCacheDir = getExternalCacheDir(context, dirName);
			Log.e("appCacheDir", appCacheDir.toString());
		}
		if (appCacheDir == null) {
			appCacheDir = context.getCacheDir();
		}
		if (appCacheDir == null) {
			String cacheDirPath = "/data/data/" + context.getPackageName()+ "/cache/";
			//Log.w("Can't define system cache directory! '%s' will be used.",cacheDirPath);
			appCacheDir = new File(cacheDirPath);
		}
		return appCacheDir;
	}

	private static File getExternalCacheDir(Context context, String dirName) {
		File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
		File appCacheDir = new File(new File(dataDir, context.getPackageName()), dirName);
//		File appCacheDir = new File(appCacheDir2, dirName);
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				Log.w(TAG, "Unable to create external cache directory");
				return null;
			}
			try {
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (IOException e) {
				Log.i(TAG, "Can't create \".nomedia\" file in application external cache directory");
			}
		}
		return appCacheDir;
	}
	
	private static boolean hasExternalStoragePermission(Context context) {
		int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
		return perm == PackageManager.PERMISSION_GRANTED;
	}

}
