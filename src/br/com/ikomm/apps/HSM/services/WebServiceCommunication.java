package br.com.ikomm.apps.HSM.services;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;
import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.repo.BannerRepository;

/**
 * WebServiceCommunication.java class.
 * Modified by Rodrigo Cericatto at August 5, 2014.
 */
public class WebServiceCommunication {
	
	/**
	 * Sends the Passe to the server.
	 *  
	 * @param color
	 * @param day
	 * @param name
	 * @param email
	 * @param company
	 * @param role
	 * @param cpf
	 */
	public void sendPurchaseForm(String color, String day, String name, String email, String company, String role, String cpf) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = "http://apps.ikomm.com.br/hsm/graph/pass-add.php?";
			String payment = "Cartão de Credito";

			if (!color.isEmpty()) {
				url = url.concat("cor=");
				url = url.concat(color);
				url = url.concat("&");
			}
			url = url.concat("os=android&");

			if (!day.isEmpty()) {
				url = url.concat("dia=");
				url = url.concat(day);
				url = url.concat("&");
			}
			if (!payment.isEmpty()) {
				url = url.concat("pagamento=");
				url = url.concat(payment);
				url = url.concat("&");
			}
			if (!name.isEmpty()) {
				url = url.concat("nome=");
				url = url.concat(name);
				url = url.concat("&");
			}
			if (!email.isEmpty()) {
				url = url.concat("email=");
				url = url.concat(email);
				url = url.concat("&");
			}
			if (!company.isEmpty()) {
				url = url.concat("empresa=");
				url = url.concat(company);
				url = url.concat("&");
			}
			if (!role.isEmpty()) {
				url = url.concat("cargo=");
				url = url.concat(role);
				url = url.concat("&");
			}
			if (!cpf.isEmpty()) {
				url = url.concat("cpf=");
				url = url.concat(cpf);
			}

			HttpGet httpGet = new HttpGet();
			URI uri = new URI(url);
			httpGet.setURI(uri);
			Log.i(AppConfiguration.COMMON_LOGGING_TAG, "The add pass URL is " + url);
			httpClient.execute(httpGet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates Banners.
	 * 
	 * @param context
	 */
	public void updateBanners(Context context) {
		String urlBanners = "http://apps.ikomm.com.br/hsm/graph/ad-android.php";
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(urlBanners);
		httpget.setHeader("Accept", "application/json");
		httpget.setHeader("Content-Type", "application/json");
		
		String responseBody = "";
		try {
			URI uri = new URI(urlBanners);
			httpget.setURI(uri);
			HttpResponse response = httpclient.execute(httpget);
			responseBody = EntityUtils.toString(response.getEntity());
			if (!responseBody.isEmpty()) {
				saveNewJsonBanner(responseBody, context);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the new Json of the Banner.
	 * 
	 * @param responseBody
	 * @param context
	 */
	private void saveNewJsonBanner(String responseBody, Context context) {
		BannerRepository repo = new BannerRepository(context);
		repo.setJsonShared(responseBody);
	}
}