package br.com.ikomm.apps.HSM;

import java.util.Calendar;
import java.util.TimeZone;

import br.com.ikomm.apps.HSM.neo.DetalhePalestraNEOActivity;
import br.ikomm.hsm.model.Palestra;
import br.ikomm.hsm.model.PalestraRepository;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.provider.CalendarContract.Events;

public class DetalhePalestraNeoActivity extends FragmentActivity {

	private int id = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_palestra);

		addListenerOnButton();

		Bundle extras = getIntent().getExtras(); 
		if (extras != null){
			id = extras.getInt("id");
		}

		if (id == 1){
			ImageView imagem = (ImageView) findViewById(R.id.imgPalestranteDetalhe);
			imagem.setImageResource(R.drawable.p_ricardo_jose_de_almeida_large);
			TextView nomePalestrante = (TextView) findViewById(R.id.tDetNomePalestrante);
			nomePalestrante.setText("RICARDO JOSÉ DE ALMEIDA");

			TextView especialidade = (TextView) findViewById(R.id.tDetEspecialidade);
			especialidade.setText("ENTENDENDO O QUE FAZ O VALOR DA EMPRESA");

			TextView data = (TextView) findViewById(R.id.tDetData);
			data.setText("27 de março");

			TextView horario = (TextView) findViewById(R.id.tDetHorario);
			horario.setText("14h - 18h");

			TextView resumo = (TextView) findViewById(R.id.tResumoPalestra);
			resumo.setText("ENTENDENDO O QUE FAZ O VALOR DA EMPRESA");

			TextView content = (TextView) findViewById(R.id.tDetalhesPalestra);
			content.setText("• O que a empresa deve entregar para criar valor para os acionistas" +
					"\n• A relação entre o que acontece na economia, no setor e na própria empresa para criar (ou destruir) valor para os acionistas" +
					"\n• Entendendo o modelo de avaliar uma empresa" +
					"\n• Como decidir a distribuição de lucros entre os acionistas para criar valor" +
					"\n• Se vender uma empresa, vai investir em quê? Como dimensionar o custo de oportunidade dos investidores" +
					"\n• Como analisar se o modelo que direcionou o valor está realista" +
					"\n• Como extrair os value drivers usados pelo avaliador da empresa" +
					"\n• Como alinhar a estratégia da empresa no valuation");
			ActionBar action = getActionBar();
			action.setLogo(R.drawable.hsm_logo);
			action.setTitle("RICARDO JOSÉ DE ALMEIDA");
			
		} else if (id == 2){
			ImageView imagem = (ImageView) findViewById(R.id.imgPalestranteDetalhe);
			imagem.setImageResource(R.drawable.p_aswath_damodaran_large);
			TextView nomePalestrante = (TextView) findViewById(R.id.tDetNomePalestrante);
			nomePalestrante.setText("ASWATH DAMORADAN ");

			TextView especialidade = (TextView) findViewById(R.id.tDetEspecialidade);
			especialidade.setText("2 EVENTOS");

			TextView data = (TextView) findViewById(R.id.tDetData);
			data.setText("28 de março");

			TextView horario = (TextView) findViewById(R.id.tDetHorario);
			horario.setText("9h - 17h30");

			TextView resumo = (TextView) findViewById(R.id.tResumoPalestra);
			resumo.setText("2 eventos");

			TextView content = (TextView) findViewById(R.id.tDetalhesPalestra);
			content.setText("Considerado a maior autoridade em valuation e o mais consagrado professor de Finanças em todo o mundo, Aswath Damodaran é autor de dez livros, usados nos principais programas de finanças em todo o mundo. Entre eles estão: Investment valuation (Avaliação de investimentos), Damodaran on valuation, Corporate finance (Finanças corporativas) e Strategic risk taking (Gestão estratégica do risco)." +
					"\nÉ professor na Stern School of Business, da New York University, onde recebeu por sete vezes o prêmio por Excelência em Ensino. Suas avaliações influenciam investidores nos quatro continentes e previram a sobrevalorização das ações do Facebook." +
					"\nDamodaran tem diversos artigos publicados no The Journal of Finance, Journal of Financial Economics e no Journal of Financial and Quantitative Analysis (JFQA), entre outras publicações." +
					"\nFormou-se pelo Indian Institute of Management e obteve seu MBA e o Ph.D. pela University of California, em Los Angeles.");
			
			ActionBar action = getActionBar();
			action.setLogo(R.drawable.hsm_logo);
			action.setTitle("ASWATH DAMORADAN ");
			
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhe_palestra, menu);
		return false;
	}

	private void addListenerOnButton() {
		findViewById(R.id.btnAgendar).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addEvent();
				Toast.makeText(DetalhePalestraNeoActivity.this,
						"Palestra agendada com sucesso", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	@SuppressLint("NewApi")
	protected void addEvent() {
		try {
			long calID = 1;
			long startMillis = 0;
			long endMillis = 0;

			Calendar beginTime = Calendar.getInstance();

			Calendar endTime = Calendar.getInstance();

			endMillis = endTime.getTimeInMillis();

			String[] init = "14h".split("h");
			int initHour = Integer.valueOf(init[0]);
			int initMin = Integer.valueOf(init[1]);
			String[] fim = "18h".split("h");
			int fimHour = Integer.valueOf(fim[0]);
			int fimMin = Integer.valueOf(fim[1]);

			if ("27".contains("27")) {
				beginTime.set(2013, Calendar.MARCH, 4, initHour, initMin);
				endTime.set(2013, Calendar.MARCH, 4, fimHour, fimMin);
			}
			if ("28".contains("28")) {
				beginTime.set(2013, Calendar.MARCH, 5, initHour, initMin);
				endTime.set(2013, Calendar.MARCH, 5, fimHour, fimMin);
			}

			ContentResolver cr = getContentResolver();
			ContentValues values = new ContentValues();
			
			values.put(Events.DTSTART, beginTime.getTimeInMillis());
			values.put(Events.DTEND, endTime.getTimeInMillis());
			values.put("allDay", 0);
			values.put("hasAlarm", 0);
			
			values.put(Events.TITLE, "ENTENDENDO O QUE FAZ O VALOR DA EMPRESA");
			values.put(Events.DESCRIPTION, "RICARDO JOSÉ DE ALMEIDA");
			values.put(Events.CALENDAR_ID, calID);
			
			TimeZone timeZone = TimeZone.getDefault();
			values.put(Events.EVENT_TIMEZONE, timeZone.getID());

			Uri uri = cr.insert(Events.CONTENT_URI, values);
			
			//get the event ID that is the last element in the Uri
			long eventID = Long.parseLong(uri.getLastPathSegment());
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
