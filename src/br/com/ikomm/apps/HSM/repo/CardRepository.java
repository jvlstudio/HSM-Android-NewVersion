package br.com.ikomm.apps.HSM.repo;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.model.Card;

import com.google.gson.Gson;

/**
 * CardRepository.java class.
 * Modified by Rodrigo Cericatto at July 21, 2014.
 */
public class CardRepository {
	
	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String MY_CARD = "my_card";
	public static final String MY_CONTACTS = "my_contacts";
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String mJsonMyCard;
	private String mJsonMyContacts;
	
	private SharedPreferences mPreferences;
	private Gson mGson = new Gson();

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public CardRepository(Context context) {
		mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets my {@link Card}. 
	 */
	public Card getMyCard() {
		try {
			mJsonMyCard = getMyCardFromShared();
			if (mJsonMyCard.isEmpty()) {
				Log.e(AppConfiguration.COMMON_LOGGING_TAG, "getMyCard() is Null");
				return null;
			}
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "getMyCard() is " + mJsonMyCard);
			Card myCard = mGson.fromJson(mJsonMyCard, Card.class);
			
			return myCard;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets my {@link Card} contacts list.
	 * 
	 * @return
	 */
	public List<Card> getMyContacts() {
		try {
			mJsonMyContacts = getMyContactsFromShared();
			if (mJsonMyContacts.isEmpty()) {
				Log.e(AppConfiguration.COMMON_LOGGING_TAG, "getMyContacts() is Null.");
				return null;
			}
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "getMyContacts() is " + mJsonMyContacts);
			Card[] myContacts = mGson.fromJson(mJsonMyContacts, Card[].class);
			
			return Arrays.asList(myContacts);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Sets my {@link Card}}.
	 * 
	 * @param card
	 */
	public void setMyCard(Card card) {
		try {
			if (card == null) {
				Log.e(AppConfiguration.COMMON_LOGGING_TAG, "setMyCard() is Null");
				return;
			} else {
				String jsonCard = mGson.toJson(card);
				SharedPreferences.Editor editor = mPreferences.edit();
				Log.e(AppConfiguration.COMMON_LOGGING_TAG, "setMyCard() is " + jsonCard);
				editor.putString(MY_CARD, jsonCard);
				editor.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets my {@link Card} contact list.
	 * 
	 * @param contactList
	 */
	public void setMyContacts(List<Card> contactList) {
		try {
			if (contactList == null) {
				Log.e(AppConfiguration.COMMON_LOGGING_TAG, "setMyContacts() is Null.");
				return;
			} else {
				String jsonContacts = mGson.toJson(contactList);
				SharedPreferences.Editor editor = mPreferences.edit();
				Log.e(AppConfiguration.COMMON_LOGGING_TAG, "setMyContacts() is " + jsonContacts);
				editor.putString(MY_CONTACTS, jsonContacts);
				editor.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets my {@link Card} contacts from the {@link Preference}.
	 * 
	 * @return
	 */
	private String getMyContactsFromShared() {
		return mPreferences.getString(MY_CONTACTS, "");
	}

	/**
	 * Gets my {@link Card} from the {@link Preference}.
	 * 
	 * @return
	 */
	private String getMyCardFromShared() {
		return mPreferences.getString(MY_CARD, "");
	}
}