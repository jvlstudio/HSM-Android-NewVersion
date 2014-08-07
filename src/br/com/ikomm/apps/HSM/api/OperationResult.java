package br.com.ikomm.apps.HSM.api;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import br.com.ikomm.apps.HSM.R;
import br.com.ikomm.apps.HSM.utils.DialogUtils;

/**
 * OperationResult.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Set 9, 2012
 */
public class OperationResult {
	
	//--------------------------------------------------
	// Enum
	//--------------------------------------------------
	
	/* 
	 * HTTP result types.
	 * SUCCESS - Everything went right.
	 * NO_INTERNET - No Internet connection on the device.
	 * CONNECTION_FAILED - Some connection problem occurred.
	 * UNKNOWN - Unknown error, use this for generic errors.
	 * SERVICE_UNAVAILABLE - The server is down.
	 * INTERNAL_SERVER_ERROR - An error occurred on the server.
	 * INVALID_TOKEN - The user token is invalid.
	 * BAD_REQUEST - Wrong URL request.
	 * PROBLEMATIC - Upload error or cache generation error.
	 * DATABASE_ERROR - Error while updating or creating the database.
	 * INFORMATIONAL - Informational error from server.
	 * REDIRECTION - Redirection error from server.
	 * PARSING_ERROR - JSON parsing error.
	 */
	public enum ResultType {
		SUCCESS,
		NO_INTERNET,
		CONNECTION_FAILED,
		UNKNOWN,
		SERVICE_UNAVAILABLE,
		INTERNAL_SERVER_ERROR,
		INVALID_TOKEN,
		BAD_REQUEST,
		PROBLEMATIC,
		DATABASE_ERROR, 
		INFORMATIONAL, 
		REDIRECTION,
		PARSING_ERROR, 
		INVALID_DATA;
	}
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// The result type of the operation.
	private ResultType mResultType;
	
	// Entity as parsed from response.
	private Object mEntity;
	
	// Entity list as parsed from response.
	private List<? extends Object> mEntityList;
	
	private String mResponseString;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	/**
	 * Creates a new OperationResult instance.
	 */
	public OperationResult() {
	}
	
	/**
	 * Creates a new OperationResult instance.
	 */
	public OperationResult(ResultType resultType) {
		mResultType = resultType;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * @return The result type of the operation.
	 */
	public ResultType getResultType() {
		return mResultType;
	}
	
	/**
	 * Sets the result type of the operation.
	 * 
	 * @param resultType The ResultType object.
	 */
	public void setResultType(ResultType resultType) {
		mResultType = resultType;
	}
	
	/**
	 * @return The entity data object from the operation.
	 */
	public Object getEntity() {
		return mEntity;
	}
	
	/**
	 * Sets the entity data object from the operation.
	 * 
	 * @param entity
	 */
	public void setEntity(Object entity) {
		mEntity = entity;
	}

	/**
	 * @return The entity list object from the operation.
	 */
	public Object getEntityList() {
		return mEntityList;
	}
	
	/**
	 * Sets the entity list object from the operation.
	 * 
	 * @param entity
	 */
	public void setEntityList(List<? extends Object> entityList) {
		mEntityList = entityList;
	}

	/**
	 * Returns the response string.
	 */
	public String getResponseString() {
		return mResponseString;
	}
	
	/**
	 * @param responseString
	 */
	public void setResponseString(String responseString) {
		mResponseString = responseString;
	}

	/**
	 * Validates the operation result and shows a dialog in case the operation is not successful.
	 * 
	 * @param context The context to show the dialog.
	 * @param result The OperationResult object to be evaluated.
	 * @param forceSuccessMessage Forces the SUCCESS status to be shown for the user.
	 * 
	 * @return True if the operation is valid.
	 */
	public static Boolean validateResult(Context context, OperationResult result, Boolean forceSuccessMessage) {
		return validateResult(context, result, null, forceSuccessMessage);
	}
	
	/**
	 * Validates the operation result and shows a dialog in case the operation is not successful.
	 * 
	 * @param context The context to show the dialog.
	 * @param result The OperationResult object to be evaluated.
	 * @param listener The OnClickListener callback function to be called.
	 * @param forceSuccessMessage Forces the SUCCESS status to be shown for the user.
	 * 
	 * @return True if the operation is valid.
	 */
	public static Boolean validateResult(Context context, OperationResult result, OnClickListener listener, Boolean forceSuccessMessage) {
		// Declaring resources.
		Boolean success = false;
		Integer titleResourceId = null;
		Integer messageResourceId = null;
		
		switch (result.getResultType()) {
			case NO_INTERNET:
				titleResourceId = R.string.error_title_no_internet;
				messageResourceId = R.string.error_msg_no_internet;
				break;
			case INTERNAL_SERVER_ERROR:
				titleResourceId = R.string.error_title_server;
				messageResourceId = R.string.error_msg_server;
				break;
			case CONNECTION_FAILED:
				titleResourceId = R.string.error_title_connection;
				messageResourceId = R.string.error_msg_connection;
				break;
			case PROBLEMATIC:
				titleResourceId = R.string.error_title_cache_generation;
				messageResourceId = R.string.error_msg_cache_generation;
				break;
			case BAD_REQUEST:
				titleResourceId = R.string.error_title_bad_request;
				messageResourceId = R.string.error_msg_bad_request;
				break;
			case PARSING_ERROR:
			case INVALID_DATA:
			case DATABASE_ERROR:
			case UNKNOWN:
				titleResourceId = R.string.error_title_unknown;
				messageResourceId = R.string.error_msg_unknown;
				break;
			case SUCCESS:
				if (forceSuccessMessage) {
					titleResourceId = R.string.http_title_success;
					messageResourceId = R.string.http_success;
					success = true;
				}
			default:
				success = true;
		}
		
		// If the title and message resources are set.
		if (titleResourceId != null && messageResourceId != null) {
			// If the context exists.
			if (context != null) {
				// If the context is an activity and is not finishing.
				if (!(context instanceof Activity) || !((Activity)context).isFinishing()) {
					// Showing the dialog.
					DialogUtils.showSimpleAlert(context, titleResourceId, messageResourceId, listener);
				}
			}
		}
		return success;
	}
}