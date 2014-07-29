package br.com.ikomm.apps.HSM.utils;

import br.com.ikomm.apps.HSM.model.Card;

public class CardConverter {
	private String tag = "-hsm-";

	public String CartaoToString(Card card) {
		String concat = card.nome + tag + card.email + tag
			+ card.telefone + tag + card.celular + tag + card.empresa
			+ tag + card.cargo + tag + card.website + tag + "gold";
		return concat;
	}

	public Card CartaoFromString(String strQrCode) {
		String[] properties = strQrCode.split("-hsm-");
		int number = 1;
		Card card = new Card();
		
		for (String string : properties) {
			switch (number) {
			case 1:
				card.nome = string;
				break;
			case 2:
				card.email = string;
				break;
			case 3:
				card.telefone = string;
				break;
			case 4:
				card.celular = string;
				break;
			case 5:
				card.empresa = string;
				break;
			case 6:
				card.cargo = string;
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