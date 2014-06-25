package br.ikomm.hsm.model;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class AuditorioSimplesRepository {
	private static String jsonSimplesAuditorio = 
			"[{'id':'1','titulo':'HSM','auditorio':'Auditório 1','link':'http://agenda.expositoronline.com.br/?eventid=8&idAuditorio=93'},{'id':'2','titulo':'ESPM','auditorio':'Auditório 2','link':'http://agenda.expositoronline.com.br/?eventid=8&idAuditorio=94'},{'id':'3','titulo':'Café Filosófico','auditorio':'Auditório 3','link':'http://hsmgroup.com.br/marketing/2013/eve/expo/mostra/conv/emkt-cpfl-24102013/'},{'id':'4','titulo':'Mackenzie','auditorio':'Auditório 4','link':'http://agenda.expositoronline.com.br/?eventid=8&idAuditorio=96'},{'id':'5','titulo':'Nexial','auditorio':'Auditório 5','link':'http://agenda.expositoronline.com.br/?eventid=8&idAuditorio=97'},{'id':'6','titulo':'Sustentabilidade','auditorio':'Auditório 6','link':'http://agenda.expositoronline.com.br/?eventid=8&idAuditorio=98'},{'id':'7','titulo':'CBM Young Professional','auditorio':'Auditório 7','link':'http://agenda.expositoronline.com.br/?eventid=8&idAuditorio=99'},{'id':'8','titulo':'Brasil Gestão para o Futuro','auditorio':'Auditório 8','link':'http://agenda.expositoronline.com.br/?eventid=8&idAuditorio=100'},{'id':'9','titulo':'Atelier de Líderes','auditorio':'Auditório 9','link':'http://agenda.expositoronline.com.br/?eventid=8&idAuditorio=101'},{'id':'10','titulo':'Fórum VAGAS','auditorio':'Aditório 10','link':'http://agenda.expositoronline.com.br/?eventid=8&idAuditorio=102'},{'id':'11','titulo':'Brasil Design Week','auditorio':'Aditório 11','link':'http://agenda.expositoronline.com.br/?eventid=8'}]";
	private Gson gson = new Gson();

	public List<AuditorioSimples> getAll() {
		AuditorioSimples[] palestrasArray = gson.fromJson(jsonSimplesAuditorio,
				AuditorioSimples[].class);
		return Arrays.asList(palestrasArray);
	}

	public AuditorioSimples getById(int id) {
		AuditorioSimples[] palestrasArray = gson.fromJson(jsonSimplesAuditorio,
				AuditorioSimples[].class);
		List<AuditorioSimples> auditorios = Arrays.asList(palestrasArray);

		for (int i = 0; i < auditorios.size(); i++) {
			if (!auditorios.get(i).id.isEmpty()
					&& Long.valueOf(auditorios.get(i).id) == id) {
				return auditorios.get(i);
			}
		}
		return null;
	}
}