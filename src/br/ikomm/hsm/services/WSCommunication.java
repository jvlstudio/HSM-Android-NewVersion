package br.ikomm.hsm.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import br.ikomm.hsm.model.ws.AgendaWS;
import br.ikomm.hsm.model.ws.BookWS;
import br.ikomm.hsm.model.ws.EventWS;
import br.ikomm.hsm.model.ws.HomeWS;
import br.ikomm.hsm.model.ws.MagazineWS;
import br.ikomm.hsm.model.ws.PanelistWS;
import br.ikomm.hsm.model.ws.PasseWS;

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
			
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(ERROR, "erro no met. wsHome: " + ex);
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

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(ERROR, "erro no met. wsEvent: " + ex);
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

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(ERROR, "erro no met. wsAgenda: " + ex);
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

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(ERROR, "erro no met. wsPanelist: " + ex);
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

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(ERROR, "erro no met. wsPasse: " + ex);
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

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(ERROR, "erro no met. magazines: " + ex);
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

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(ERROR, "erro no met. wsBook: " + ex);
			return null;
		}
	}
}