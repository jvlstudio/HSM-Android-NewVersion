package br.com.ikomm.apps.HSM.task;

import android.content.Context;
import android.os.AsyncTask;
import br.com.ikomm.apps.HSM.api.OperationResult;
import br.com.ikomm.apps.HSM.api.OperationResult.ResultType;
import br.com.ikomm.apps.HSM.database.AppInfo;
import br.com.ikomm.apps.HSM.database.QueryHelper;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.utils.Utils;

/**
 * UpdaterAsyncTask class.
 * 
 * @author Rodrigo Cericatto
 * @since Set 13, 2012
 */
public class UpdaterAsyncTask extends AsyncTask<Void, Integer, OperationResult> {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	final private Context mContext;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	/**
	 * Constructor.
	 * 
	 * @param activity Context of the application.
	 */
	public UpdaterAsyncTask(Context activity) {
		mContext = activity;
	}
	
	//--------------------------------------------------
	// Async Task
	//--------------------------------------------------

	@Override
	protected OperationResult doInBackground(Void... params) {
		// The result object.
		OperationResult result = new OperationResult(ResultType.SUCCESS);
		
		// Getting the App info and updating the App info update time.
		final AppInfo info = QueryHelper.getAppInfo(mContext);
		Boolean databaseNeeedsUpdate = QueryHelper.updateAppInfoUpdateTime(mContext, info.getId(), Utils.getCurrentInMillis());
		
		// Setting the result entity.
		result.setEntity(databaseNeeedsUpdate);
		result.setEntityList(null);
		return result;
	}
	
	@Override
	protected void onPostExecute(OperationResult result) {
		ContentManager.getInstance().taskFinished(this, result);
	}
}