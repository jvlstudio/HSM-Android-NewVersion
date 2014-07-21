package br.ikomm.hsm.repo;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import br.ikomm.hsm.model.Cartao;

import com.google.gson.Gson;

/**
 * CartaoRepository.java class.
 * Modified by Rodrigo Cericatto at July 21, 2014.
 */
public class CartaoRepository {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String MEU_CARTAO = "MEU_CARTAO";
	public static final String MEUS_CONTATOS = "MEUS_CONTATOS";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String mJsonMeuCartao;
	private String mJsonMeusContatos;
	private SharedPreferences mPreferences;
	private Gson mGson = new Gson();

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public CartaoRepository(Context context) {
		mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	public Cartao getMeuCartao() {
		try {
			mJsonMeuCartao = getMeuCartaoFromShared();
			if (mJsonMeuCartao.isEmpty()) {
				Log.e("MEU_CARTAO", "getMeuCartao = Null");
				return null;
			}
			Log.e("MEU_CARTAO", "getMeuCartao = " + mJsonMeuCartao);
			Cartao meuCartao = mGson.fromJson(mJsonMeuCartao, Cartao.class);
			
			return meuCartao;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Cartao> getMeusContatos() {
		try {
			mJsonMeusContatos = getMeusContatosFromShared();
			if (mJsonMeusContatos.isEmpty()) {
				Log.e("MEUS_CONTATOS", "getMeusContatos = Null");
				return null;
			}
			Log.e("MEUS_CONTATOS", "getMeusContatos = " + mJsonMeusContatos);
			Cartao[] meusContatos = mGson.fromJson(mJsonMeusContatos, Cartao[].class);
			
			return Arrays.asList(meusContatos);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setMeuCartao(Cartao cartao) {
		try {
			if (cartao == null) {
				Log.e("Cartao", "setMeuCartao = Null");
				return;
			} else {
				String jsonCartao = mGson.toJson(cartao);
				SharedPreferences.Editor editor = mPreferences.edit();
				Log.e("Cartao", "setMeuCartao = " + jsonCartao);
				editor.putString(MEU_CARTAO, jsonCartao);
				editor.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMeusContatos(List<Cartao> contatos) {
		try {
			if (contatos == null) {
				Log.e("Contatos", "setMeusContatos = Null");
				return;
			} else {
				String jsonContatos = mGson.toJson(contatos);
				SharedPreferences.Editor editor = mPreferences.edit();
				Log.e("Contatos", "setMeusContatos = " + jsonContatos);
				editor.putString(MEUS_CONTATOS, jsonContatos);
				editor.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getMeusContatosFromShared() {
		return mPreferences.getString(MEUS_CONTATOS, "");
	}

	private String getMeuCartaoFromShared() {
		return mPreferences.getString(MEU_CARTAO, "");
	}
}