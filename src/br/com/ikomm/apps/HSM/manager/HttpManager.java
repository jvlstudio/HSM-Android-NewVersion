package br.com.ikomm.apps.HSM.manager;

import java.net.URI;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.api.OperationResult;
import br.com.ikomm.apps.HSM.api.OperationResult.ResultType;

/**
 * HttpManager.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Set 9, 2012
 */
public class HttpManager {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
    // All HTTP connections share the same HTTP client object.
    private HttpClient mHttpClient;

	// Application context reference used to check Internet connections.
	private Context mContext;

	// Singleton instance.
	private static HttpManager sInstance = null;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	// Private constructor.
	private HttpManager() {
        mHttpClient = createHttpClient();
	}
	
	/**
	 * Return singleton instance.
	 * 
	 * @return
	 */
	public static HttpManager getInstance() {
		if(sInstance == null) {
			sInstance = new HttpManager();
		}
		return sInstance;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Return common HTTP client object.
	 * @return
	 */
    public HttpClient getHttpClient() {
        return mHttpClient;
    }
	
    /** 
     * Set application context.
     * @param context
     */
	public void setContext(Context context) {
		mContext = context;
	}

	/**
	 * Return HTTP resources to memory.
	 */
    public void shutdownHttpClient() {
        if(mHttpClient != null && mHttpClient.getConnectionManager() != null) {
            mHttpClient.getConnectionManager().shutdown();
        }
    }
    
    /**
     * Check for Internet connectivity. Return true if Internet is accessible.
     * 
     * @return
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    
    /**
     * Indicates if the user is using a WiFi connection.
     * 
     * @return
     */
    public boolean isOnWiFi() {
    	ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    	if (info != null && info.isConnectedOrConnecting()) {
    		return true;
    	}
    	return false;
    }

    /**
     * Send Post GET request with parameters in URL string.
     * If the request reaches a timeout or throws an exception,
     * it will attempt to retry for a max of 3 times.
     * 
     * @param url
     * @return
     * @throws Exception
     */
    public OperationResult executeHttpGetWithRetry(String url) throws Exception {
        int attemptCount = 0;
        while (attemptCount < AppConfiguration.MAX_HTTP_RETRY_COUNT) {
            try {
            	OperationResult response = executeHttpGet(url);
            	// If content is simply not cached yet, retry after a small timeout.
            	if (ResultType.PROBLEMATIC.equals(response.getResultType())) {
            		if (++attemptCount < AppConfiguration.MAX_HTTP_RETRY_COUNT) {
            			Thread.sleep(AppConfiguration.HTTP_CACHE_RETRY_WAIT);
            			continue;
            		}
            		return response;
            	}
            	// If we get here, that means we were successful and we can stop.
                return response;
            } catch (Exception e) {
            	attemptCount++;
                // If we have exhausted our retry limit.
                if (attemptCount < AppConfiguration.MAX_HTTP_RETRY_COUNT) {
                	// We have retries remaining, so log the message and go again.
                	Log.e(AppConfiguration.COMMON_LOGGING_TAG, "HTTP error, will try again", e);
                } else {
                	Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Could not succeed with retry...", e);
            		return new OperationResult(ResultType.CONNECTION_FAILED);
                }
            }
			Thread.sleep(AppConfiguration.HTTP_SERVER_RETRY_WAIT);
        }
        
        // Should not reach here unless MAX_RETRY_COUNT was set to 0 or less.
        OperationResult result = new OperationResult();
		result.setResultType(OperationResult.ResultType.UNKNOWN);
		return result;
    }

    /**
     * Send Post GET request with parameters in URL string
     * 
     * @param url
     * @return
     * @throws Exception
     */
    public OperationResult executeHttpGet(String url) throws Exception {
    	// First check for Internet connection.
    	// TODO: Discover why the line below throw errors.
//    	if (!isOnline()) {
//    		return new OperationResult(ResultType.NO_INTERNET);
//    	}
    	
    	// Set up HTTP request.
        HttpGet request = new HttpGet(new URI(url));
        request.setHeader("Accept", "application/json");

        // Execute request and capture response.
        HttpResponse response = mHttpClient.execute(request);
        // Obtain response string from result.
        String entity = EntityUtils.toString(response.getEntity());
        // Set result response based on status.
        int statusCode = response.getStatusLine().getStatusCode();
        OperationResult result = validateStatusCode(statusCode);
        // Set result and return.
		result.setResponseString(entity);
		return result;
    }
    
    public OperationResult executeHttpPost(String url, List<NameValuePair> params) throws Exception {
    	// First check for Internet connection.
    	// TODO: Discover why the line below throw errors.
//    	if (!isOnline()) {
//    		return new OperationResult(ResultType.NO_INTERNET);
//    	}
    	// Set up HTTP request.
    	HttpPost request = new HttpPost(url);
    	request.setHeader("Accept", "application/json");
    	request.setEntity(new UrlEncodedFormEntity(params));
        // Execute request and capture response.
        HttpResponse response = mHttpClient.execute(request);
        // Obtain response string from result
        String entity = EntityUtils.toString(response.getEntity());
        // Set result response based on status.
        int statusCode = response.getStatusLine().getStatusCode();
        OperationResult result = validateStatusCode(statusCode);
        // Set result and return.
		result.setResponseString(entity);
		return result;
    }
    
    public OperationResult executeHttpPostWithRetry(String url, List<NameValuePair> params) throws Exception {
        int attemptCount = 0;
        while (attemptCount < AppConfiguration.MAX_HTTP_RETRY_COUNT) {
            try {
            	OperationResult response = executeHttpPost(url, params);
            	// If content is simply not cached yet, retry after a small timeout.
            	if (ResultType.PROBLEMATIC.equals(response.getResultType())) {
            		if (++attemptCount < AppConfiguration.MAX_HTTP_RETRY_COUNT) {
            			Thread.sleep(AppConfiguration.HTTP_CACHE_RETRY_WAIT);
            			continue;
            		}
            		return response;
            	}
            	// If we get here, that means we were successful and we can stop.
                return response;
            } catch (Exception e) {
                // If we have exhausted our retry limit.
                if (attemptCount < AppConfiguration.MAX_HTTP_RETRY_COUNT) {
                	// We have retries remaining, so log the message and go again.
                	Log.e(AppConfiguration.COMMON_LOGGING_TAG, "HTTP error, will try again", e);
                } else {
                	Log.w(AppConfiguration.COMMON_LOGGING_TAG, "Could not succeed with retry...");
            		return new OperationResult(ResultType.CONNECTION_FAILED);
                }
            }
			Thread.sleep(AppConfiguration.HTTP_SERVER_RETRY_WAIT);
            attemptCount++;
        }
        
        // Should not reach here unless MAX_RETRY_COUNT was set to 0 or less.
        OperationResult result = new OperationResult();
		result.setResultType(OperationResult.ResultType.UNKNOWN);
		return result;
    }
    
    /**
     * Initialize common HTTP client object.
     * 
     * @return
     */
    private HttpClient createHttpClient() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);
        HttpProtocolParams.setUserAgent(params, AppConfiguration.USER_AGENT);
        
        // This is to help against out of memory errors.
        HttpConnectionParams.setSocketBufferSize(params, AppConfiguration.SOCKET_BUFFER_SIZE);
        // Set the timeout in milliseconds until a connection is established.
        HttpConnectionParams.setConnectionTimeout(params, AppConfiguration.CONNECTION_TIMEOUT);
        // Set the default socket timeout in milliseconds (waiting for data).
        HttpConnectionParams.setSoTimeout(params, AppConfiguration.SOCKET_TIMEOUT);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);

        return new DefaultHttpClient(conMgr, params);
    }
    
    private OperationResult validateStatusCode(int statusCode) {
    	OperationResult result = new OperationResult();
    	
    	// Response Code 0xx : Unknown.
        if(statusCode < 100) {
        	result.setResultType(OperationResult.ResultType.UNKNOWN);
        }
        // Response Code 1xx : Informational.
        else if(statusCode < 200) {
        	result.setResultType(OperationResult.ResultType.INFORMATIONAL);
        }
        // Response Code 202 : Either upload error or cache generation error.
        else if(statusCode == 202) {
        	result.setResultType(OperationResult.ResultType.PROBLEMATIC);
        }
        // Response Code 2xx (Not 202) : Success
        else if(statusCode < 300) {
        	result.setResultType(OperationResult.ResultType.SUCCESS);
        }
        // Response Code 3xx : Redirection.
        else if(statusCode < 400) {
        	result.setResultType(OperationResult.ResultType.REDIRECTION);
        } 
        // Response Code 4xx: Bad request.
        else if(statusCode < 500) {
        	result.setResultType(OperationResult.ResultType.BAD_REQUEST);
        }
        // Response Code 401: Invalid token.
        else if(statusCode < 401) {
        	result.setResultType(OperationResult.ResultType.INVALID_TOKEN);
        }
        // Response Code 503: Service Unavailable.
        else if(statusCode == 503) {
        	result.setResultType(OperationResult.ResultType.SERVICE_UNAVAILABLE);
        }
        // Response Code 5xx (Not 503) : Internal Server Error.
        else {
        	result.setResultType(OperationResult.ResultType.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}