package br.ikomm.hsm.model;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class PalestraBDWRepository {
	private static String jsonPalestras = 
			"[{'id':'2','day':'4 de novembro','hour_init':'11h30','hour_final':'11h45','slug':'','title':'Design Trade Show','subtitle':'','type':'lecture'},{'id':'3','day':'4 de novembro','hour_init':'11h30','hour_final':'18h00','slug':'','title':'Projeto Comprador/Imagem','subtitle':'','type':'lecture'},{'id':'4','day':'4 de novembro','hour_init':'13h30','hour_final':'14h30','slug':'','title':'Design Como Ferramenta de Inovação','subtitle':'para Micro e Pequenas Empresas - SEBRAE','type':'lecture'},{'id':'5','day':'4 de novembro','hour_init':'15h00','hour_final':'16h00','slug':'','title':'Marcas e Você fazendo o bem','subtitle':'com Suzana Apelbaum','type':'lecture'},{'id':'6','day':'4 de novembro','hour_init':'16h30','hour_final':'17h30','slug':'','title':'Quais as Fronteiras do Design Brasileiro','subtitle':'Mediação: Regina Augusto (Meio e Mensagem)','type':'lecture'},{'id':'7','day':'4 de novembro','hour_init':'18h00','hour_final':'19h00','slug':'','title':'Cerimônia Brasil Design Awards','subtitle':'','type':'lecture'},{'id':'8','day':'4 de novembro','hour_init':'19h00','hour_final':'20h00','slug':'','title':'Happy Hour','subtitle':'','type':'break'},{'id':'9','day':'5 de novembro','hour_init':'11h30','hour_final':'11h45','slug':'','title':'Abertura Oficial do Brasil Design Week 2013','subtitle':'com Gustavo Gelli e Roger Rieger','type':'lecture'},{'id':'10','day':'5 de novembro','hour_init':'11h45','hour_final':'12h45','slug':'','title':'Fórum: Fomento e Políticas Públicas de Incentivo ao Design','subtitle':'','type':'lecture'},{'id':'11','day':'5 de novembro','hour_init':'11h30','hour_final':'18h00','slug':'','title':'Projeto Comprador/Imagem','subtitle':'','type':'lecture'},{'id':'12','day':'5 de novembro','hour_init':'11h30','hour_final':'20h00','slug':'','title':'Design Trade Show - Para visitantes','subtitle':'','type':'lecture'},{'id':'13','day':'5 de novembro','hour_init':'13h00','hour_final':'14h30','slug':'','title':'Storytelling: A Essência da Marca Disney','subtitle':'com Tokie Esaka/Disney','type':'lecture'},{'id':'14','day':'5 de novembro','hour_init':'15h00','hour_final':'18h00','slug':'','title':'Design Brasileiro - Um panorama do Design Nacional','subtitle':'Visão dos diretores regionais da ABEDesign','type':'lecture'},{'id':'15','day':'5 de novembro','hour_init':'18h00','hour_final':'19h00','slug':'','title':'Plataforma de Conteúdo Transmídia: O Boticário Viva','subtitle':'com Bruno Monte Jorge/O Boticário','type':'lecture'},{'id':'16','day':'5 de novembro','hour_init':'19h00','hour_final':'','slug':'','title':'Foto Geral Brasil Design Week','subtitle':'','type':'lecture'},{'id':'17','day':'5 de novembro','hour_init':'19h00','hour_final':'20h00','slug':'','title':'Happy Hour','subtitle':'','type':'break'},{'id':'18','day':'5 de novembro','hour_init':'20h00','hour_final':'21h00','slug':'','title':'Sessão Especial HSM: A Sabedoria e Inspiração de Steve Jobs','subtitle':'Regis McKenna','type':'lecture'},{'id':'19','day':'6 de novembro','hour_init':'11h30','hour_final':'20h00','slug':'','title':'Design Trade Show - Para visitantes','subtitle':'','type':'lecture'},{'id':'20','day':'6 de novembro','hour_init':'11h30','hour_final':'13h00','slug':'','title':'Reunião Fórum Brasil Design','subtitle':'','type':'lecture'},{'id':'21','day':'6 de novembro','hour_init':'11h30','hour_final':'18h00','slug':'','title':'Projeto Comprador/Imagem','subtitle':'','type':'lecture'},{'id':'22','day':'6 de novembro','hour_init':'13h30','hour_final':'14h30','slug':'','title':'Bienal Florianópolis 2015','subtitle':'Roselie Faria Lemos','type':'lecture'},{'id':'23','day':'6 de novembro','hour_init':'15h00','hour_final':'16h00','slug':'','title':'Yes, I Can Design','subtitle':'Cristina Grether (Coca-Cola)','type':'lecture'},{'id':'24','day':'6 de novembro','hour_init':'16h30','hour_final':'17h30','slug':'','title':'C.E.S.A.R.','subtitle':'(Centro de Estudos e Sistemas Avançados do Recife)','type':'lecture'},{'id':'25','day':'6 de novembro','hour_init':'20h00','hour_final':'21h00','slug':'','title':'Confraternização ABEDESIGN','subtitle':'','type':'break'}]";
	private Gson gson = new Gson();

	public List<PalestraBDW> getAll() {
		PalestraBDW[] palestrasArray = gson.fromJson(jsonPalestras,
				PalestraBDW[].class);
		return Arrays.asList(palestrasArray);
	}

	public PalestraBDW getById(int id) {
		PalestraBDW[] palestrasArray = gson.fromJson(jsonPalestras,
				PalestraBDW[].class);
		List<PalestraBDW> palestras = Arrays.asList(palestrasArray);

		for (int i = 0; i < palestras.size(); i++) {
			if (!palestras.get(i).id.isEmpty()
					&& Long.valueOf(palestras.get(i).id) == id) {
				return palestras.get(i);
			}
		}
		return null;
	}
}