package br.ikomm.hsm.model;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

public class CartaoRepositorio {
	public static final String MEU_CARTAO = "MEU_CARTAO";
	public static final String MEUS_CONTATOS = "MEUS_CONTATOS";
	private String jsonMeuCartao;
	private String jsonMeusContatos;
	SharedPreferences preferences;
	private Gson gson = new Gson();

	public CartaoRepositorio(Context context) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public Cartao getMeuCartao() {
		try {
			jsonMeuCartao = getMeuCartaoFromShared();

			if (jsonMeuCartao.isEmpty()) {
				Log.e("MEU_CARTAO", "getMeuCartao = Null");
				return null;
			}

			Log.e("MEU_CARTAO", "getMeuCartao = " + jsonMeuCartao);

			Cartao meuCartao = gson.fromJson(jsonMeuCartao, Cartao.class);
			return meuCartao;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Cartao> getMeusContatos() {
		try {
			jsonMeusContatos = getMeusContatosFromShared();

			if (jsonMeusContatos.isEmpty()) {
				Log.e("MEUS_CONTATOS", "getMeusContatos = Null");
				return null;
			}

			Log.e("MEUS_CONTATOS", "getMeusContatos = " + jsonMeusContatos);

			Cartao[] meusContatos = gson.fromJson(jsonMeusContatos,
					Cartao[].class);
			return Arrays.asList(meusContatos);
		} catch (Exception e) {
			return null;
		}
	}

	public void setMeuCartao(Cartao cartao) {
		try {
			if (cartao == null) {
				Log.e("Cartao", "setMeuCartao = Null");
				return;
			} else {
				String jsonCartao = gson.toJson(cartao);
				SharedPreferences.Editor editor = preferences.edit();
				Log.e("Cartao", "setMeuCartao = " + jsonCartao);
				editor.putString(MEU_CARTAO, jsonCartao);
				editor.commit();
			}
		} catch (Exception e) {
		}
	}

	public void setMeusContatos(List<Cartao> contatos) {
		try {
			if (contatos == null) {
				Log.e("Contatos", "setMeusContatos = Null");
				return;
			} else {
				String jsonContatos = gson.toJson(contatos);
				SharedPreferences.Editor editor = preferences.edit();
				Log.e("Contatos", "setMeusContatos = " + jsonContatos);
				editor.putString(MEUS_CONTATOS, jsonContatos);
				editor.commit();
			}
		} catch (Exception e) {
		}
	}

	private String getMeusContatosFromShared() {
		return preferences.getString(MEUS_CONTATOS, "");
	}

	private String getMeuCartaoFromShared() {
		return preferences.getString(MEU_CARTAO, "");
	}
}
