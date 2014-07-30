package br.com.ikomm.apps.HSM.utils;

import br.com.ikomm.apps.HSM.model.Card;

public class CardConverter {
	private String tag = "-hsm-";

	public String CartaoToString(Card card) {
		String concat = card.name + tag + card.email + tag
			+ card.phone + tag + card.mobilePhone + tag + card.company
			+ tag + card.role + tag + card.website + tag + "gold";
		return concat;
	}

	public Card CartaoFromString(String strQrCode) {
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