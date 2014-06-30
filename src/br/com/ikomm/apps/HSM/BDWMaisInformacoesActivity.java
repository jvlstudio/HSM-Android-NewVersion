package br.com.ikomm.apps.HSM;

import android.os.Bundle;
import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

public class BDWMaisInformacoesActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bdwmais_informacoes);
		
		ActionBar action = getActionBar();
		action.setLogo(R.drawable.hsm_logo);
		
		TextView maisInfo = (TextView) findViewById(R.id.textoPaginaBDW);
		/*maisInfo.setText(Html.fromHtml("<b>DESIGN TRADE SHOW</b>"+

"Alguns dos melhores escritórios do mercado brasileiro apresentarão suas principais inovações no campo do design, neste que é o coração do evento. Através de seus trabalhos, irão mostrar que a indústria brasileira de design possui visão estratégica, experiência, dimensão profissional e uma grande capacidade de inovação. Absolutamente imperdível para quem busca novas ideias. <br/>"+
"<b>Participantes no Design Trade Show:</b>"+
"<br/>"+
"Abedesign<br/>"+
Crama Design Estratégico<br/>"+
Greco Design<br/>"+
Komm Design Group<br/>"+
OZ Design<br/>"+
Pande<br/>"+
Tátil<br/>"+

<b>DESIGN IN VIDEO</b>"+
<br/>"+
Apresentação digital de cases de design dos melhores escritórios do mercado brasileiro. Através de postais posicionados nas paredes e de tablets, o público poderá ter acesso ao conteúdo das empresas participantes."+

LVA Criação<br/>"+

Crama Design<br/>"+

Komm::<br/>"+

Tátil<br/>"+

Saad branding+design <br/>"+

Dezign com Z<br/>"+

Commgroup<br/>"+

<b>BRASIL DESIGN AWARD</b>"+
<br/>"+
O mais abrangente prêmio de inovação através do design O Brasil Design Award é o prêmio dos prêmios! <br/>"+
Promovido pela ABEDESIGN, com a Direção de André Poppovic e realização do Centro Brasil Design, os prêmios serão entregues aos trabalhos brasileiros que acumularam o maior numero de premiações selecionadas de design, nacionais e internacionais, no ano de 2013.<br/>"+

O Brasil Design Award premiará trabalhos na área de Design de Produto e Design Gráfico, além de personalidades do setor e entidades dedicadas a promoção da categoria no Brasil.<br/>"+

PATROCINADOR: Sebrae<br/>"+

ORGANIZAÇÃO: Centro Brasil Design<br/>"+

<b>PROJETO COMPRADOR RODADA DE NEGÓCIOS</b>"+

Em parceria com a Apex-Brasil, o Projeto Comprador fomenta a realização de negócios e gera oportunidades para realização de contatos internacionais entre empresas de design participantes do projeto Brasil Design e potenciais executivos internacionais. Um dos mais eficazes instrumentos de promoção de negócios, organizado a partir de uma dinâmica de encontros empresariais.<br/>"+

<b>PROJETO IMAGEM</b>"+
<br/>"+
Exclusivo aos participantes do projeto Brasil Design, o Projeto Imagem tem por objetivo trazer jornalistas internacionais para visitar a Brasil Design Week, conhecer as agências de design e promover o design brasileiro internacionalmente. <br/>"+

<b>COMITÊ ORGANIZADOR</b><br/>"+

DIRETORIA ABEDESIGN "+
<br/>"+
<b>Gustavo Gelli</b> – Presidente"+
<br/>"+
<b>André Poppovic</b> - Vice-Presidente"+
<br/>"+
<b>Roger Rieger</b> – Diretor de Promoção Comercial e Eventos"+
<br/>"+
<b>Gian Franco Rocchiccioli</b> – Diretor de Assuntos Regulatórios e Fomento"+
<br/>"+
<b>Gisela Schulzinger</b> – Diretora de Capacitação Empresarial"+
<br/>"+
<b>Giovanni Vannuchi</b> – Diretor de Relações Institucionais"+
<br/>"+
<b>Ellen Kiss</b> – Diretora de Relações Internacionais"+
<br/>"+
<b>Gustavo Greco</b> – Diretor de Imagem"+
<br/>"+
<b>Vinicíus Iubel</b> – Diretor ABEDESIGN Regional Paraná"+
<br/>"+
<b>Cynthia Massote</b> – Diretora ABEDESIGN Regional Minas Gerais"+
<br/>"+
<b>Felipe Arcoverde</b> - Diretor ABEDESIGN Regional Bahia"+
<br/>"+
<b>Allyson Reis </b>– Diretor ABEDESIGN Regional Ceará"+
<br/>"+
<b>Paulo Oliva </b>– Diretor ABEDESIGN Regional Pernambuco"+
<br/>"+
<b>Marcos Sebben</b> – Diretor ABEDESIGN Regional Santa Catarina"+
<br/>"+
<b>Mário Verdi </b>– Diretor ABEDESIGN Regional Rio Grande do Sul"+
<br/>"+

DIREÇÃO GERAL"+

<b>Roger Rieger </b>– KOMM::"+
<br/><br/>"+
BRASIL DESIGN AWARDS"+

Curador: André Poppovic | Organização: Centro Brasil Design"+
<br/><br/>"+
EQUIPE EXECUTIVA ABEDESIGN"+

Anna Carolina Maccarone – Gerente Executiva"+

Bruno Fernandes – Gerente Administrativo Financeiro"+

Paola Oliveira – Coordenadora do Projeto Interagência e Inteligência Comercial"+

Iara Araújo – Assistente de Atendimento"+
<br/><br/>"+
ESTRATÉGIA E INOVAÇÃO"+

Maurício Medeiros"+
<br/><br/>
CONSELHO CONSULTIVO

Manoel Müller | Luciano Deos | Fernando Faria | Frederico Gelli

Luis Augusto Indio da Costa | Ricardo Leite 
<br/><br/>
IDENTIDADE VISUAL

OXX DESIGN
<br/><br/>
ASSESSORIA DE IMPRENSA

Lucia Faria Inteligência em Comunicação

Marco Barone: marco@luciafaria.com.br 

Telefone: (11) 3277-8891"));
*/		
		
		String a = "<b>DESIGN TRADE SHOW</b><br/>Alguns dos melhores escritórios do mercado brasileiro apresentarão suas principais inovações no campo do design, neste que é o coração do evento. Através de seus trabalhos, irão mostrar que a indústria brasileira de design possui visão estratégica, experiência, dimensão profissional e uma grande capacidade de inovação. Absolutamente imperdível para quem busca novas ideias. <br/><br/><b>Participantes no Design Trade Show:</b><br/>Abedesign<br/>Crama Design Estratégico<br/>Greco Design<br/>Komm Design Group<br/>OZ Design<br/>Pande<br/>Tátil<br/><br/><b>DESIGN IN VIDEO</b><br/>Apresentação digital de cases de design dos melhores escritórios do mercado brasileiro. Através de postais posicionados nas paredes e de tablets, o público poderá ter acesso ao conteúdo das empresas participantes.LVA Criação<br/>Crama Design<br/>Komm::<br/>Tátil<br/>Saad branding+design <br/>Dezign com Z<br/>Commgroup<br/><br/><b>BRASIL DESIGN AWARD</b><br/>O mais abrangente prêmio de inovação através do design O Brasil Design Award é o prêmio dos prêmios! <br/>Promovido pela ABEDESIGN, com a Direção de André Poppovic e realização do Centro Brasil Design, os prêmios serão entregues aos trabalhos brasileiros que acumularam o maior numero de premiações selecionadas de design, nacionais e internacionais, no ano de 2013.<br/>O Brasil Design Award premiará trabalhos na área de Design de Produto e Design Gráfico, além de personalidades do setor e entidades dedicadas a promoção da categoria no Brasil.<br/><br/>PATROCINADOR: Sebrae<br/>ORGANIZAÇÃO: Centro Brasil Design<br/><br/><b>PROJETO COMPRADOR RODADA DE NEGÓCIOS</b><br/>Em parceria com a Apex-Brasil, o Projeto Comprador fomenta a realização de negócios e gera oportunidades para realização de contatos internacionais entre empresas de design participantes do projeto Brasil Design e potenciais executivos internacionais. Um dos mais eficazes instrumentos de promoção de negócios, organizado a partir de uma dinâmica de encontros empresariais.<br/><br/><b>PROJETO IMAGEM</b><br/>Exclusivo aos participantes do projeto Brasil Design, o Projeto Imagem tem por objetivo trazer jornalistas internacionais para visitar a Brasil Design Week, conhecer as agências de design e promover o design brasileiro internacionalmente. <br/><br/><b>COMITÊ ORGANIZADOR</b><br/>DIRETORIA ABEDESIGN<br/><b>Gustavo Gelli</b> – Presidente<br/><b>André Poppovic</b> - Vice-Presidente<br/><b>Roger Rieger</b> – Diretor de Promoção Comercial e Eventos<br/><b>Gian Franco Rocchiccioli</b> – Diretor de Assuntos Regulatórios e Fomento<br/><b>Gisela Schulzinger</b> – Diretora de Capacitação Empresarial<br/><b>Giovanni Vannuchi</b> – Diretor de Relações Institucionais<br/><b>Ellen Kiss</b> – Diretora de Relações Internacionais<br/><b>Gustavo Greco</b> – Diretor de Imagem<br/><b>Vinicíus Iubel</b> – Diretor ABEDESIGN Regional Paraná<br/><b>Cynthia Massote</b> – Diretora ABEDESIGN Regional Minas Gerais<br/><b>Felipe Arcoverde</b> - Diretor ABEDESIGN Regional Bahia<br/><b>Allyson Reis </b>– Diretor ABEDESIGN Regional Ceará<br/><b>Paulo Oliva </b>– Diretor ABEDESIGN Regional Pernambuco<br/><b>Marcos Sebben</b> – Diretor ABEDESIGN Regional Santa Catarina<br/><b>Mário Verdi </b>– Diretor ABEDESIGN Regional Rio Grande do Sul<br/><br/>DIREÇÃO GERAL<br/><b>Roger Rieger </b>– KOMM::<br/><br/>BRASIL DESIGN AWARDS<br/>Curador: André Poppovic | Organização: Centro Brasil Design<br/><br/>EQUIPE EXECUTIVA ABEDESIGN<br/>Anna Carolina Maccarone – Gerente ExecutivaBruno Fernandes – Gerente Administrativo FinanceiroPaola Oliveira – Coordenadora do Projeto Interagência e Inteligência ComercialIara Araújo – Assistente de Atendimento<br/><br/>ESTRATÉGIA E INOVAÇÃOMaurício Medeiros<br/><br/>CONSELHO CONSULTIVO<br/>Manoel Müller | Luciano Deos | Fernando Faria | Frederico GelliLuis Augusto Indio da Costa | Ricardo Leite<br/><br/>IDENTIDADE VISUALOXX DESIGN<br/><br/>ASSESSORIA DE IMPRENSA<br/>Lucia Faria Inteligência em Comunicação<br/>Marco Barone: marco@luciafaria.com.br<br/>Telefone: (11) 3277-8891";
		maisInfo.setText(Html.fromHtml(a));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bdwmais_informacoes, menu);
		return false;
	}

}
