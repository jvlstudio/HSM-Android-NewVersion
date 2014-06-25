package br.ikomm.hsm.util;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import br.ikomm.hsm.model.BannerRepository;

import android.content.Context;

public class WebServiceCommunication {

	public void sendFormularioCompra(String cor, String dia, String nome,
			String email, String empresa, String cargo, String cpf) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			String url = "http://apps.ikomm.com.br/hsm/graph/pass-add.php?";
			String pagamento = "Cartao de Credito";

			if (!cor.isEmpty()) {
				url = url.concat("color=");
				url = url.concat(cor);
				url = url.concat("&");
			}
			url = url.concat("os=android&");

			if (!dia.isEmpty()) {
				url = url.concat("day=");
				url = url.concat(dia);
				url = url.concat("&");
			}
			if (!pagamento.isEmpty()) {
				url = url.concat("payment=");
				url = url.concat(pagamento);
				url = url.concat("&");
			}
			if (!nome.isEmpty()) {
				url = url.concat("name=");
				url = url.concat(nome);
				url = url.concat("&");
			}
			if (!email.isEmpty()) {
				url = url.concat("email=");
				url = url.concat(email);
				url = url.concat("&");
			}
			if (!empresa.isEmpty()) {
				url = url.concat("company=");
				url = url.concat(empresa);
				url = url.concat("&");
			}
			if (!cargo.isEmpty()) {
				url = url.concat("role=");
				url = url.concat(cargo);
				url = url.concat("&");
			}
			if (!cpf.isEmpty()) {
				url = url.concat("cpf=");
				url = url.concat(cpf);
			}

			HttpGet httpGet = new HttpGet();
			URI uri = new URI(url);
			httpGet.setURI(uri);
			httpClient.execute(httpGet);
		} catch (Exception e) {

		}

	}

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
			if (!responseBody.isEmpty())
				SalvarNovoJsonBanners(responseBody, context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void SalvarNovoJsonBanners(String responseBody, Context context) {
		BannerRepository repo = new BannerRepository(context);
		repo.setJsonShared(responseBody);
	}

}
