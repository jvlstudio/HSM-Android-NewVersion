package br.com.ikomm.apps.HSM.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.Book;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Home;
import br.com.ikomm.apps.HSM.model.Magazine;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.model.Passe;
import br.com.ikomm.apps.HSM.model.ws.AgendaWS;
import br.com.ikomm.apps.HSM.model.ws.BookWS;
import br.com.ikomm.apps.HSM.model.ws.EventWS;
import br.com.ikomm.apps.HSM.model.ws.HomeWS;
import br.com.ikomm.apps.HSM.model.ws.MagazineWS;
import br.com.ikomm.apps.HSM.model.ws.PanelistWS;
import br.com.ikomm.apps.HSM.model.ws.PasseWS;
import br.com.ikomm.apps.HSM.utils.Utils;

import com.google.gson.Gson;

/**
 * WSCommunication.java class.
 * Modified by Rodrigo Cericatto at July 16, 2014.
 */
public class WSCommunication {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String ERROR = "HSM_ws_error";
	public static final String URL_SERVER = "http://apps.ikomm.com.br/hsm5/rest-android";
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets all {@link Home} from the web service.
	 */
	public HomeWS wsHome() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/home.php";

			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);

			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream content = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));

			String responseBody = reader.readLine();
			HomeWS list = gson.fromJson(responseBody, HomeWS.class);
			
			Utils.fileLog("WSCommunication.wsHome() -> " + list.data.size());
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error in method WsHome: " + ex);
			return null;
		}
	}

	/**
	 * Gets all {@link Event} from the web service.
	 */
	public EventWS wsEvent() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/events.php";

			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);

			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream content = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));

			String responseBody = reader.readLine();
			EventWS list = gson.fromJson(responseBody, EventWS.class);

			Utils.fileLog("WSCommunication.wsEvent() -> " + list.data.size());
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error in method WsEvent: " + ex);
			return null;
		}
	}

	/**
	 * Gets all {@link Agenda} from the web service.
	 */
	public AgendaWS wsAgenda() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/agenda.php";

			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);

			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream content = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));

			String responseBody = reader.readLine();
			AgendaWS list = gson.fromJson(responseBody, AgendaWS.class);

			Utils.fileLog("WSCommunication.wsAgenda() -> " + list.data.size());
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error in method WsAgenda: " + ex);
			return null;
		}
	}

	/**
	 * Gets all {@link Panelist} from the web service.
	 */
	public PanelistWS wsPanelist() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/panelist.php";

			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);

			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream content = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));

			String responseBody = reader.readLine();
			PanelistWS list = gson.fromJson(responseBody, PanelistWS.class);

			Utils.fileLog("WSCommunication.wsPanelist() -> " + list.data.size());
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error in method WsPanelist: " + ex);
			return null;
		}
	}

	/**
	 * Gets all {@link Passe} from the web service.
	 */
	public PasseWS wsPasse() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/passes.php";

			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);

			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream content = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));

			String responseBody = reader.readLine();
			PasseWS list = gson.fromJson(responseBody, PasseWS.class);

			Utils.fileLog("WSCommunication.wsPasse() -> " + list.data.size());
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error in method WsPasse: " + ex);
			return null;
		}
	}

	/**
	 * Gets all {@link Magazine} from the web service.
	 */
	public MagazineWS wsMagazine() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/magazines.php";

			HttpGet httpGet = new HttpGet(url);
			HttpResponse _httpResponse = httpClient.execute(httpGet);

			HttpEntity httpEntity = _httpResponse.getEntity();
			InputStream content = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));

			String responseBody = reader.readLine();
			MagazineWS list = gson.fromJson(responseBody, MagazineWS.class);

			Utils.fileLog("WSCommunication.wsMagazine() -> " + list.data.size());
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error in method WsMagazine: " + ex);
			return null;
		}
	}

	/**
	 * Gets all {@link Book} from the web service.
	 */
	public BookWS wsBook() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/books.php";

			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);

			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream content = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));

			String responseBody = reader.readLine();
			BookWS list = gson.fromJson(responseBody, BookWS.class);

			Utils.fileLog("WSCommunication.wsBook() -> " + list.data.size());
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error in method WsBook: " + ex);
			return null;
		}
	}
}