package br.com.ikomm.apps.HSM.task;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import br.com.ikomm.apps.HSM.api.ApiRequest;
import br.com.ikomm.apps.HSM.api.OperationResult;
import br.com.ikomm.apps.HSM.api.OperationResult.ResultType;
import br.com.ikomm.apps.HSM.database.QueryHelper;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.model.Book;

/**
 * BookAsyncTask.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 30, 2014
 */
public class BookAsyncTask extends AsyncTask<Void, Integer, OperationResult> {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// Application context reference.
	private final Context mContext;
	
	// Flag for Home retrieval.
	private boolean mNeedsUpdate;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	/**
	 * Creates a new {@link BookAsyncTask} instance.
	 */
	public BookAsyncTask(Context context, boolean needsUpdate) {
		mContext = context;
		mNeedsUpdate = needsUpdate;
	}

	//----------------------------------------------
	// AsyncTask
	//----------------------------------------------
	
	@Override
	protected OperationResult doInBackground(Void... params) {
		// Updating Book list.
		try {
			return update(mContext, mNeedsUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(OperationResult result) {
		ContentManager.getInstance().taskFinished(this, result);
	}

	//----------------------------------------------
	// Static Methods
	//----------------------------------------------
	
	/**
	 * Updates the {@link Book} if needed or fetch it from the database.
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static OperationResult update(Context context, boolean needsUpdate) throws Exception {
		// If we need to update from the server.
		if (needsUpdate) {
			OperationResult serverResult = getBookFromServer(context);
			return serverResult;
		} else {
			// We don't need to update, just grab the data from the server.
			OperationResult databaseResult = getPersistedBook(context);
			// If we successful grab the data from the database, return the result.
			if (ResultType.SUCCESS.equals(databaseResult.getResultType())) {
				return databaseResult;
			}
			// Otherwise, we need to update from the server after all.
			return getBookFromServer(context);
		}
	}
	
	/**
	 * Fetches the {@link Book} from the server.
	 * 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private static OperationResult getBookFromServer(final Context context) throws Exception {
		// Getting the data from the online server.
		OperationResult result = ApiRequest.getApiList(Book.SERVICE_URL, ContentManager.FETCH_TASK.BOOK);
		// If successful, persist the data into the database.
		if (ResultType.SUCCESS.equals(result.getResultType())) {
			// Creating a final reference to the entity list.
			final List<Book> list = (List<Book>)result.getEntityList();
			// Replacing saved results in the list.
			result.setEntityList(list);
			// Creating a new thread to persist the data.
			(new Thread() {
				@Override
				public void run() {
					QueryHelper.persistBook(context, list);
				}
			}).start();
		}
		return result;
	}

	/**
	 * Fetches the data from the database.
	 * 
	 * @return
	 */
	private static OperationResult getPersistedBook(Context context) {
		// Pull persisted Book.
		List<Book> list = QueryHelper.getBookList(context);
		
		// If the database is empty, return an error.
		if (list == null) {
			return new OperationResult(ResultType.INVALID_DATA);
		}
		// Set success result and return.
		OperationResult result = new OperationResult(ResultType.SUCCESS);
		result.setEntityList(list);
		return result;
	}
}