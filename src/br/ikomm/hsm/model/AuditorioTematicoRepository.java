package br.ikomm.hsm.model;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class AuditorioTematicoRepository {
	private static String jsonTematicoAuditorio = 
			"[{'id':'1','textoBranco':'Estação do conhecimento','textoPagina':'Uma iniciativa HSM pela valorização da gestão empresarial brasileira. <br/><br/>A Estação irá abordar a <b>gestão para o futuro</b>, com um olhar para asoportunidades e desafios que o país irá enfrentar, trazendo informações ediscussões importantes para as decisões empresariais.<br/><br/>Quais as mudanças de cenário que irão influenciar as decisões dos líderesbrasileiros?<br/><br/>O que é necessário para prosperar neste novo Brasil?<br/><br/>Como alguns dos principais setores da economia estão se organizando paracompetir em um cenário de competição global?<br/><br/>Para ajudar a responder a essas e outras perguntas, construímos umagrade que une uma visão prática com grandes estudiosos sobre temas como: <b>empreendedorismo, ética, economia, digital, varejo, entre outros</b>.','titulo':'Brasil: Gestão para o futuro'},{'id':'2','textoBranco':'Estação do conhecimento','textoPagina':'O objetivo deste espaço é reunir grupos de lideranças, formadores deopinião, para promover o diálogo sobre as políticas públicas existentes noBrasil. Por meio dessa discussão, queremos engajar o público presentee transformar o local em um ambiente de criação coletiva e troca de experiências.<br/><br/>E, para que esta discussão sobre as políticas públicas possa serenriquecedora, os contextos problemáticos atuais da sociedade brasileiraserão utilizados como exemplo na busca das definições necessárias para aconstrução do país das Cidades do Futuro.','titulo':'Estação Sustentabilidade'},{'id':'3','textoBranco':'Estação do conhecimento','textoPagina':'Conhecimento e inspiração para o desenvolvimento de grandes líderes.<br/><br/>Comandado por César Souza, um dos maiores especialistas em liderançano País e curador de conteúdo do espaço, esta Estação visa contribuir parao incremento da capacidade de gestão dos líderes brasileiros, em conversascom empresários e executivos, discutindo, analisando e apontando caminhospara combater a atual escassez de líderes em diversas áreas. Iniciativa daHSM e da Empreenda.','titulo':'Atelier de Líderes'},{'id':'4','textoBranco':'Estação do conhecimento','textoPagina':'Construindo uma nova geração de executivos.<br/><br/>A <b>3ª Edição do CBN Young Professional apresenta</b> conteúdos para jovensprofissionais que desejam ampliar suas visões sobre gestão e negócios.Durantes os 3 dias, âncoras e comentaristas da CBN apresentarão parao jovem profissional aspectos ligados a sua carreira, economia, gestão,educação e sucesso.<br/><br/>Ao final de cada palestra, participe ainda da rodada de perguntas e respostas com <b>Mário Sérgio Cortela, Juca Kfouri, Max Gehringer entre outros</b>.','titulo':'CBN Young Professional'}]";
	private Gson gson = new Gson();

	public List<AuditorioTematico> getAll() {
		AuditorioTematico[] auditoriosTematicos = gson.fromJson(jsonTematicoAuditorio,
				AuditorioTematico[].class);
		return Arrays.asList(auditoriosTematicos);
	}

	public AuditorioTematico getById(int id) {
		AuditorioTematico[] palestrasArray = gson.fromJson(jsonTematicoAuditorio,
				AuditorioTematico[].class);
		List<AuditorioTematico> palestras = Arrays.asList(palestrasArray);

		for (int i = 0; i < palestras.size(); i++) {
			if (!palestras.get(i).id.isEmpty()
					&& Long.valueOf(palestras.get(i).id) == id) {
				return palestras.get(i);
			}
		}
		return null;
	}
}