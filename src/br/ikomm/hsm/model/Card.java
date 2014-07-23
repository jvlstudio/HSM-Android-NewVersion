package br.ikomm.hsm.model;

public class Card {
	public String nome;
	public String email;
	public String telefone;
	public String celular;
	public String empresa;
	public String cargo;
	public String website;
	
	@Override
	public String toString() {
		return "Card [nome=" + nome + ", email=" + email + ", telefone="
				+ telefone + ", celular=" + celular + ", empresa=" + empresa
				+ ", cargo=" + cargo + ", website=" + website + "]";
	}	
}