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
import br.ikomm.hsm.activity.SplashScreenActivity;
import br.ikomm.hsm.model.ws.AgendaWS;
import br.ikomm.hsm.model.ws.BookWS;
import br.ikomm.hsm.model.ws.EventWS;
import br.ikomm.hsm.model.ws.HomeWS;
import br.ikomm.hsm.model.ws.MagazineWS;
import br.ikomm.hsm.model.ws.PanelistWS;
import br.ikomm.hsm.model.ws.PasseWS;

import com.google.gson.Gson;

public class WSCommunication {

	private static final String ERROR = "HSM_ws_error";
	private static final String URL_SERVER = "http://apps.ikomm.com.br/hsm5/rest-android";
	private Gson gson;

	/**
	 * HOME
	 */
	public HomeWS wsHome() {
		try {
			HttpClient _httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/home.php";

			HttpGet _httpGet = new HttpGet(url);
			HttpResponse _httpResponse = _httpClient.execute(_httpGet);

			HttpEntity _httpEntity = _httpResponse.getEntity();
			InputStream content = _httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));

			String responseBody = reader.readLine();
			HomeWS list = gson.fromJson(responseBody, HomeWS.class);

			return list;

		} catch (Exception ex) {
			// TODO: handle exception
			Log.e(ERROR, "erro no met. wsHome: " + ex);
			return null;
		}
	}

	/**
	 * EVENTS
	 */
	public EventWS wsEvent() {
		try {
			HttpClient _httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/events.php";

			HttpGet _httpGet = new HttpGet(url);
			HttpResponse _httpResponse = _httpClient.execute(_httpGet);

			HttpEntity _httpEntity = _httpResponse.getEntity();
			InputStream content = _httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));

			String responseBody = reader.readLine();
			EventWS list = gson.fromJson(responseBody, EventWS.class);

			return list;

		} catch (Exception ex) {
			// TODO: handle exception
			Log.e(ERROR, "erro no met. wsEvent: " + ex);
			return null;
		}
	}

	/**
	 * AGENDA
	 */
	public AgendaWS wsAgenda() {
		try {
			HttpClient _httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/agenda.php";

			HttpGet _httpGet = new HttpGet(url);
			HttpResponse _httpResponse = _httpClient.execute(_httpGet);

			HttpEntity _httpEntity = _httpResponse.getEntity();
			InputStream content = _httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));

			String responseBody = reader.readLine();
			AgendaWS list = gson.fromJson(responseBody, AgendaWS.class);
//			AgendaWS list = mGson.fromJson(SplashScreenActivity.FAKE_AGENDA_JSON, AgendaWS.class);

			return list;
		} catch (Exception ex) {
			// TODO: handle exception
			Log.e(ERROR, "erro no met. wsAgenda: " + ex);
			return null;
		}
	}
	
	/**
	 * AGENDA
	 */
	public AgendaWS wsFakeAgenda() {
		try {
			HttpClient _httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/agenda.php";

			HttpGet _httpGet = new HttpGet(url);
			HttpResponse _httpResponse = _httpClient.execute(_httpGet);

			HttpEntity _httpEntity = _httpResponse.getEntity();
			InputStream content = _httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));

			String responseBody = reader.readLine();
			AgendaWS list = gson.fromJson(responseBody, AgendaWS.class);

			return list;
		} catch (Exception ex) {
			// TODO: handle exception
			Log.e(ERROR, "erro no met. wsAgenda: " + ex);
			return null;
		}
	}

	/**
	 * PANELISTS
	 */
	public PanelistWS wsPanelist() {
		try {
			HttpClient _httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/panelist.php";

			HttpGet _httpGet = new HttpGet(url);
			HttpResponse _httpResponse = _httpClient.execute(_httpGet);

			HttpEntity _httpEntity = _httpResponse.getEntity();
			InputStream content = _httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));

			String responseBody = reader.readLine();
			PanelistWS list = gson.fromJson(responseBody, PanelistWS.class);

			return list;

		} catch (Exception ex) {
			// TODO: handle exception
			Log.e(ERROR, "erro no met. wsPanelist: " + ex);
			return null;
		}
	}

	/**
	 * PASSES
	 */
	public PasseWS wsPasse() {
		try {
			HttpClient _httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/passes.php";

			HttpGet _httpGet = new HttpGet(url);
			HttpResponse _httpResponse = _httpClient.execute(_httpGet);

			HttpEntity _httpEntity = _httpResponse.getEntity();
			InputStream content = _httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));

			String responseBody = reader.readLine();
			PasseWS list = gson.fromJson(responseBody, PasseWS.class);

			return list;

		} catch (Exception ex) {
			// TODO: handle exception
			Log.e(ERROR, "erro no met. wsPasse: " + ex);
			return null;
		}
	}

	/**
	 * MAGAZINES
	 */
	public MagazineWS wsMagazine() {
		try {
			HttpClient _httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/mMagazineList.php";

			HttpGet _httpGet = new HttpGet(url);
			HttpResponse _httpResponse = _httpClient.execute(_httpGet);

			HttpEntity _httpEntity = _httpResponse.getEntity();
			InputStream content = _httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));

			String responseBody = reader.readLine();
			MagazineWS list = gson.fromJson(responseBody, MagazineWS.class);

			return list;

		} catch (Exception ex) {
			// TODO: handle exception
			Log.e(ERROR, "erro no met. wsMagazine: " + ex);
			return null;
		}
	}

	/**
	 * BOOKS
	 */
	public BookWS wsBook() {
		try {
			HttpClient _httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			String url = URL_SERVER + "/books.php";

			HttpGet _httpGet = new HttpGet(url);
			HttpResponse _httpResponse = _httpClient.execute(_httpGet);

			HttpEntity _httpEntity = _httpResponse.getEntity();
			InputStream content = _httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));

			String responseBody = reader.readLine();
			BookWS list = gson.fromJson(responseBody, BookWS.class);

			return list;

		} catch (Exception ex) {
			// TODO: handle exception
			Log.e(ERROR, "erro no met. wsBook: " + ex);
			return null;
		}
	}
}
