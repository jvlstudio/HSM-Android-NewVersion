package br.com.ikomm.apps.HSM.utils;

import br.com.ikomm.apps.HSM.model.Card;

/**
 * CardConverter.java class. <br>
 * Modified by Rodrigo at August 1, 2014.
 */
public class CardConverter {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String mTag = "-hsm-";

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Converts a {@link Card} to a string.
	 * 
	 * @param card
	 * 
	 * @return
	 */
	public String cardToString(Card card) {
		String concat = card.name + mTag + card.email + mTag + card.phone + mTag + card.mobilePhone + mTag + card.company
			+ mTag + card.role + mTag + card.website + mTag + "gold";
		return concat;
	}

	/**
	 * Converts a string to a {@link Card}.
	 * 
	 * @param strQrCode
	 * 
	 * @return
	 */
	public Card cardFromString(String strQrCode) {
		String[] properties = strQrCode.split("-hsm-");
		int number = 1;
		Card card = new Card();
		
		for (String string : properties) {
			switch (number) {
			case 1:
				card.name = string;
				break;
			case 2:
				card.email = string;
				break;
			case 3:
				card.phone = string;
				break;
			case 4:
				card.mobilePhone = string;
				break;
			case 5:
				card.company = string;
				break;
			case 6:
				card.role = string;
				break;
			case 7:
				card.website = string;
				break;
			/*
			 * case 8: cartao.nome = string;
			 */
			}
			number++;
		}
		return card;
	}
}