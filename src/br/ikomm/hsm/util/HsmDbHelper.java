package br.ikomm.hsm.util;

import com.google.gson.Gson;
import br.ikomm.hsm.model.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class HsmDbHelper extends SQLiteOpenHelper{

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "HsmDbHelper.db";

	
	public HsmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


	@Override
	public void onCreate(SQLiteDatabase db) {
		 db.execSQL(SQL_CRIA_PALESTRA);
		 PapulaPalestras();
		 
	}
	
	private void PapulaPalestras() {
		Gson gson = new Gson();
		
		PalestraResponse palestraResponse = gson.fromJson(JSON_PALESTRAS, PalestraResponse.class);
		
	}


	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	private String JSON_PALESTRAS = "{'data':[[{'author':'','content':'','day':'27out','gmtc':'','hour_final':'18h00','hour_init':'17h30','id':'1','minutes':'','slug':'credential','subtitle':'Café de boas-vindas','title':'Credenciamento','type':'break'},{'author':'','content':'','day':'27out','gmtc':'','hour_final':'18h30','hour_init':'18h00','id':'2','minutes':'','slug':'credential','subtitle':'Bem-vindos','title':'Diretoria FDC','type':'break'},{'author':'Rafael Lucchesi','content':'','day':'27out','gmtc':'','hour_final':'19h15','hour_init':'18h30','id':'3','minutes':'','slug':'rafael-lucchesi','subtitle':'Desafios da Competitividade','title':'Rafael Lucchesi','type':'lecture'},{'author':'William Waack','content':'','day':'27out','gmtc':'','hour_final':'20h00','hour_init':'19h15','id':'4','minutes':'','slug':'william-waack','subtitle':'Palestra Magna de Abertura','title':'William Waack','type':'lecture'},{'author':'','content':'','day':'27out','gmtc':'','hour_final':'21h30','hour_init':'20h00','id':'5','minutes':'','slug':'coquetel','subtitle':'','title':'Coquetel','type':'break'}],[{'author':'','content':'','day':'28out','gmtc':'','hour_final':'09h00','hour_init':'08h00','id':'6','minutes':'','slug':'credential','subtitle':'Café de boas-vindas','title':'Credenciamento','type':'break'},{'author':'Prof. José Paschoal Rossetti','content':'','day':'28out','gmtc':'','hour_final':'10h15','hour_init':'09h00','id':'7','minutes':'','slug':'prof-jose-paschoal-rossetti','subtitle':'Cenários Econômicos: Brasil 2014 a 2016','title':'Prof. José Paschoal Rossetti','type':'lecture'},{'author':'Mailson da Nóbrega','content':'','day':'28out','gmtc':'','hour_final':'11h15','hour_init':'10h15','id':'8','minutes':'','slug':'mailson-da-nobrega','subtitle':'Perspectivas Políticas e Institucionais para o Brasil','title':'Mailson da Nóbrega','type':'lecture'},{'author':'','content':'','day':'28out','gmtc':'','hour_final':'11h45','hour_init':'11h15','id':'9','minutes':'','slug':'coffee','subtitle':'','title':'Coffee Break','type':'break'},{'author':'Prof. José Paschoal Rossetti, Prof. Paulo Resende, Mailson da Nóbrega e Carlênio Bezerra Castelo Branco','content':'','day':'28out','gmtc':'','hour_final':'12h45','hour_init':'11h45','id':'10','minutes':'','slug':'painel-competitividade','subtitle':'Prof. José Paschoal Rossetti, Prof. Paulo Resende, Mailson da Nóbrega e Carlênio Bezerra Castelo Branco','title':'Painel: Competitividade','type':'lecture'},{'author':'','content':'','day':'28out','gmtc':'','hour_final':'14h15','hour_init':'12h45','id':'11','minutes':'','slug':'lunch','subtitle':'','title':'Almoço Dialogado','type':'break'},{'author':'Jorge Arbache','content':'','day':'28out','gmtc':'','hour_final':'15h15','hour_init':'14h15','id':'12','minutes':'','slug':'jorge-arbache','subtitle':'Produtividade','title':'Jorge Arbache','type':'lecture'},{'author':'Cees van Riel','content':'','day':'28out','gmtc':'','hour_final':'16h15','hour_init':'15h15','id':'13','minutes':'','slug':'cees-van-riel','subtitle':'Reputação e Competitividade','title':'Cees van Riel','type':'lecture'},{'author':'','content':'','day':'28out','gmtc':'','hour_final':'16h45','hour_init':'16h15','id':'14','minutes':'','slug':'coffee','subtitle':'','title':'Coffee Break','type':'break'},{'author':'Jorge Arbache, Marcelo Strufaldi Castelli, Ricardo Selmi e Otávio Maia','content':'','day':'28out','gmtc':'','hour_final':'17h45','hour_init':'16h45','id':'15','minutes':'','slug':'painel-competitividade','subtitle':'Jorge Arbache, Marcelo Strufaldi Castelli, Ricardo Selmi e Otávio Maia','title':'Painel: Competitividade','type':'lecture'},{'author':'Clóvis de Barros Filho','content':'','day':'28out','gmtc':'','hour_final':'19h00','hour_init':'17h45','id':'16','minutes':'','slug':'clovis-de-barros-filho','subtitle':'Palestra','title':'Clóvis de Barros Filho','type':'lecture'},{'author':'','content':'','day':'28out','gmtc':'','hour_final':'21h00','hour_init':'19h00','id':'17','minutes':'','slug':'lunch','subtitle':'','title':'Jantar','type':'break'}],[{'author':'Jorge Gerdau Johannpeter, Prof. Carlos Arruda','content':'','day':'29out','gmtc':'','hour_final':'10h00','hour_init':'09h0','id':'18','minutes':'','slug':'forum-de-dialogo-empresarial','subtitle':'Jorge Gerdau Johannpeter, Prof. Carlos Arruda','title':'Fórum de Diálogo Empresarial','type':'lecture'},{'author':'Ernesto Vilella, Rafael Guimarães, Carlos Zilli, José Carlos Duarte','content':'','day':'29out','gmtc':'','hour_final':'11h15','hour_init':'10h00','id':'19','minutes':'','slug':'painel-inovacao-em-modelos-de-negocio','subtitle':'Ernesto Vilella, Rafael Guimarães, Carlos Zilli, José Carlos Duarte','title':'Painel: Inovação em Modelos de Negócio','type':'lecture'},{'author':'','content':'','day':'29out','gmtc':'','hour_final':'11h45','hour_init':'11h15','id':'20','minutes':'','slug':'coffee','subtitle':'','title':'Coffee Break','type':'break'},{'author':'Wagner Brenner','content':'','day':'29out','gmtc':'','hour_final':'13h00','hour_init':'11h45','id':'21','minutes':'','slug':'wagner-brenner','subtitle':'Palestra Magna de Encerramento: Update or Die','title':'Wagner Brenner','type':'lecture'}]]}"; 
	
	
	
	public static abstract class PalestraDadosTabela implements BaseColumns {
        public static final String TABLE_NAME = "palestra";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_GMTC = "gmtc";
        public static final String COLUMN_HOUR_FINAL = "hour_final";
        public static final String COLUMN_HOUR_INIT = "hour_init";
        public static final String COLUMN_MINUTES = "minutes";
        public static final String COLUMN_SLUG = "slug";
        public static final String COLUMN_SUBTITLE = "subtitle";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TYPE = "type";
    }

	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CRIA_PALESTRA =
	    "CREATE TABLE " + PalestraDadosTabela.TABLE_NAME + " (" +
		PalestraDadosTabela.COLUMN_ID + TEXT_TYPE + COMMA_SEP +
	    PalestraDadosTabela.COLUMN_AUTHOR + TEXT_TYPE + COMMA_SEP +
	    PalestraDadosTabela.COLUMN_CONTENT + TEXT_TYPE + COMMA_SEP +
	    PalestraDadosTabela.COLUMN_DAY + TEXT_TYPE + COMMA_SEP +
	    PalestraDadosTabela.COLUMN_GMTC + TEXT_TYPE + COMMA_SEP +
	    PalestraDadosTabela.COLUMN_HOUR_FINAL + TEXT_TYPE + COMMA_SEP +
	    PalestraDadosTabela.COLUMN_HOUR_INIT + TEXT_TYPE + COMMA_SEP +
	    PalestraDadosTabela.COLUMN_MINUTES + TEXT_TYPE + COMMA_SEP +
	    PalestraDadosTabela.COLUMN_SLUG + TEXT_TYPE + COMMA_SEP +
	    PalestraDadosTabela.COLUMN_SUBTITLE + TEXT_TYPE + COMMA_SEP +
	    PalestraDadosTabela.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
	    PalestraDadosTabela.COLUMN_TYPE + TEXT_TYPE +
	    " )";

}
