package br.com.ikomm.apps.HSM;

/**
 * Stores the application configuration params.
 * 
 * @author Rodrigo Cericatto
 * @since Set 9, 2012
 */
public class AppConfiguration {
	
	//----------------------------------------------
	// General Constants
	//----------------------------------------------	
	
	// Application version.
	public static final String APP_VERSION = "1.0.0";
	
	// Cache refreshing.
	public static final Integer REFRESH_CACHE_INTERVAL = 5;
	
	//----------------------------------------------
	// Database Settings
	//----------------------------------------------

	// Any time you make changes to the database objects, you have to increase the database version.
	public static final int DATABASE_VERSION = 1;
	
	// The name of the database file.
	public static final String DATABASE_NAME = "hsm.db";
	
	// Flag to get data from server or from database.
	public static Boolean sDatabaseNeedsUpdate = false;
	
	//----------------------------------------------
	// Logging
	//----------------------------------------------
	
	// Tag for common log output.
	public static final String COMMON_LOGGING_TAG = "hsm";

	//----------------------------------------------
	// Network Configuration
	//----------------------------------------------
	
	// Default socket buffer size (4KB).
	public static final int SOCKET_BUFFER_SIZE = 4096;

	// Default connection timeout (5 seconds).
	public static final int CONNECTION_TIMEOUT = 5000;

	// Default socket timeout for downloading content (30 seconds).
	public static final int SOCKET_TIMEOUT = 30000;

	// Max retry count for HTTP timeouts.
	public static final int MAX_HTTP_RETRY_COUNT = 3;

	// Wait to retry the server on a cache generation error.
	public static final int HTTP_CACHE_RETRY_WAIT = 4000;

	// Wait to retry connecting to the server on a connection timeout.
	public static final int HTTP_SERVER_RETRY_WAIT = 2000;
	
	//----------------------------------------------
	// Server Settings
	//----------------------------------------------

	// Server IP.
	public static final String SERVER_IP = "http://apps.ikomm.com.br/hsm5/rest-android";
	
	// HTTP UserAgent.
	public static final String USER_AGENT = "HSM/" + APP_VERSION;
}