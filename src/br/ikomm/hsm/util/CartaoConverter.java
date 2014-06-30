package br.ikomm.hsm.util;

import br.ikomm.hsm.model.Cartao;

public class CartaoConverter {
	private String tag = "-hsm-";

	public String CartaoToString(Cartao cartao) {
		String concat = cartao.nome + tag + cartao.email + tag
				+ cartao.telefone + tag + cartao.celular + tag + cartao.empresa
				+ tag + cartao.cargo + tag + cartao.website + tag + "gold";
		return concat;
	}

	public Cartao CartaoFromString(String strQrCode) {
		String[] properties = strQrCode.split("-hsm-");
		int number = 1;
		Cartao cartao = new Cartao();
		for (String string : properties) {
			switch (number) {
			case 1:
				cartao.nome = string;
				break;
			case 2:
				cartao.email = string;
				break;
			case 3:
				cartao.telefone = string;
				break;
			case 4:
				cartao.celular = string;
				break;
			case 5:
				cartao.empresa = string;
				break;
			case 6:
				cartao.cargo = string;
				break;
			case 7:
				cartao.website = string;
				break;
			/*
			 * case 8: cartao.nome = string;
			 */
			}
			number++;
		}

		return cartao;
	}
}
