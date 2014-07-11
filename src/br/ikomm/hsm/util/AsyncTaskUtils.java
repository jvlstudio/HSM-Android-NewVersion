package br.ikomm.hsm.util;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

/**
 * AsyncTaskUtils class.
 * 
 * @author Rodrigo Cericatto
 * @since Apr 4, 2014
 */
public abstract class AsyncTaskUtils {

	@SuppressLint("NewApi")
	public static <T> void execute(AsyncTask<T, ?, ?> task, T... params) {
		int currentApiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentApiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		} else {
			task.execute(params);
		}
	}
}