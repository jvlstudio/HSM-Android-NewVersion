package br.com.ikomm.apps.HSM.model;

public class Pacote {
	public String titulo;
	public String validade;
	public String local;
	public String precoNormal;
	public String precoApp;
	
	@Override
	public String toString() {
		return "Pacote [titulo=" + titulo + ", validade=" + validade
			+ ", local=" + local + ", precoNormal=" + precoNormal
			+ ", precoApp=" + precoApp + "]";
	}
}