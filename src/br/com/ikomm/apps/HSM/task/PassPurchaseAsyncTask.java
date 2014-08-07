package br.com.ikomm.apps.HSM.task;

import java.net.URLEncoder;

import android.os.AsyncTask;
import android.util.Log;
import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.api.OperationResult;
import br.com.ikomm.apps.HSM.api.OperationResult.ResultType;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.manager.HttpManager;
import br.com.ikomm.apps.HSM.model.Passe;
import br.com.ikomm.apps.HSM.utils.StringUtils;

/**
 * PassPurchaseAsyncTask.java class.
 * 
 * @author Rodrigo Cericatto
 * @since August 7, 2014
 */
public class PassPurchaseAsyncTask extends AsyncTask<Void, Integer, OperationResult> {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final Integer HTTP_STATUS_OK = 200;
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// Data.
	private String mName;
	private String mEmail;
	private String mCompany;
	private String mRole;
	private String mCpf;
	private Integer mPassId;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	/**
	 * Creates a new {@link PassPurchaseAsyncTask} instance.
	 */
	public PassPurchaseAsyncTask(String name, String email, String company, String role, String cpf, Integer passId) {
		mName = name;
		mEmail = email;
		mCompany = company;
		mRole = role;
		mCpf = cpf;
		mPassId = passId;
	}

	//----------------------------------------------
	// AsyncTask
	//----------------------------------------------
	
	@Override
	protected OperationResult doInBackground(Void... params) {
		// Updating Home list.
		try {
			return sendPurchaseForm();
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
	 * Sends the {@link Passe} to the server.
	 */
	public OperationResult sendPurchaseForm() {
		OperationResult result = new OperationResult(ResultType.BAD_REQUEST);
		try {
			String url = "http://apps.ikomm.com.br/hsm5/rest-android/passes.php?";

			if (!mName.isEmpty()) {
				url = url.concat("nome=");
				url = url.concat(URLEncoder.encode(mName, "UTF-8"));
				url = url.concat("&");
			}
			if (!mEmail.isEmpty()) {
				url = url.concat("email=");
				url = url.concat(URLEncoder.encode(mEmail, "UTF-8"));
				url = url.concat("&");
			}
			if (!mCompany.isEmpty()) {
				url = url.concat("empresa=");
				url = url.concat(URLEncoder.encode(mCompany, "UTF-8"));
				url = url.concat("&");
			}
			if (!mRole.isEmpty()) {
				url = url.concat("cargo=");
				url = url.concat(URLEncoder.encode(mRole, "UTF-8"));
				url = url.concat("&");
			}
			if (!mCpf.isEmpty()) {
				url = url.concat("cpf=");
				url = url.concat(URLEncoder.encode(mCpf, "UTF-8"));
				url = url.concat("&");
			}
			if (mPassId != null) {
				url = url.concat("passe_id=");
				url = url.concat(String.valueOf(mPassId));
			}

			Log.i(AppConfiguration.COMMON_LOGGING_TAG, "The add pass URL is " + url + ".");
			
			// Set success result and return.
			result = callHttpRequest(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Calls the Http request.
	 * 
	 * @param serviceUrl 
	 * 
	 * @throws Exception
	 */
	public OperationResult callHttpRequest(String serviceUrl) {
		// Holds the operation result.
		OperationResult result = new OperationResult(ResultType.BAD_REQUEST);
		
		// Getting data from server.
		try {
			result = HttpManager.getInstance().executeHttpGetWithRetry(serviceUrl);
			
			// Getting JSON string.
			String jsonString = result.getResponseString();
			if (StringUtils.isEmpty(jsonString)) {
				// Set the result as parsing error.
				result.setResultType(ResultType.PARSING_ERROR);
				return result;
			} else {
				// Checks the return status.
				String parts[] = jsonString.split(",");
				String code[] = parts[0].split(":");
				if (Integer.valueOf(code[1]) == HTTP_STATUS_OK) {
					result.setResultType(ResultType.SUCCESS);
				}
			}
			
			// Checking for errors.
			if (!ResultType.SUCCESS.equals(result.getResultType())) {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}