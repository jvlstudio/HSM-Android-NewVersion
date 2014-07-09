package br.ikomm.hsm.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import br.ikomm.hsm.model.Agenda;
import br.ikomm.hsm.model.Book;
import br.ikomm.hsm.model.Event;
import br.ikomm.hsm.model.Magazine;
import br.ikomm.hsm.model.Panelist;
import br.ikomm.hsm.model.Passe;
import br.ikomm.hsm.model.ws.AgendaWS;
import br.ikomm.hsm.model.ws.BookWS;
import br.ikomm.hsm.model.ws.EventWS;
import br.ikomm.hsm.model.ws.HomeWS;
import br.ikomm.hsm.model.ws.MagazineWS;
import br.ikomm.hsm.model.ws.PanelistWS;
import br.ikomm.hsm.model.ws.PasseWS;
import br.ikomm.hsm.repo.AgendaRepo;
import br.ikomm.hsm.repo.BookRepo;
import br.ikomm.hsm.repo.EventRepo;
import br.ikomm.hsm.repo.HomeRepo;
import br.ikomm.hsm.repo.MagazineRepo;
import br.ikomm.hsm.repo.PanelistRepo;
import br.ikomm.hsm.repo.PasseRepo;

public class HSMService extends Service implements Runnable{
	
	private boolean ativo;
	private WSCommunication wsCommunication = new WSCommunication();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		ativo = true;
		new Thread(this, "HSMService"+ startId).start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(ativo){
			if(hasConnection()){
				// ACESSO AO WS END-POINT HOME.
				wsHome();
				
				// ACESSO AO WS END-POINT EVENT.
				wsEvent();
				
				// ACESSO AO WS END-POIN AGENDA
				wsAgenda();
				
				// ACESSO AO WS END-POINT PANELIST
				wsPanelist();
				
				// ACESSO AO WS END-POINT PASSES
				wsPasse();
				
				// ACESSO AO WS END-POINT MAGAZINES
				wsMagazine();
				
				// ACESSO AO WS END-POINT BOOKS
				wsBook();
				
				stopSelf();
			} else{
				stopSelf();
			}
		} else {
			stopSelf();
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		ativo = false;
		super.onDestroy();
	}

	private boolean hasConnection() {
		// TODO Auto-generated method stub
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo ni = connMgr.getActiveNetworkInfo();
		if (ni == null){
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * CHAMADA PARA CONSUMO DO WEB SERVICE E INCLUSÌO DOS DADOS.
	 * @return
	 */
	private boolean wsHome() {
		// TODO Auto-generated method stub
		try {
			HomeWS lista = wsCommunication.wsHome();
			if(lista.data != null){
				HomeRepo _hr = new HomeRepo(getApplicationContext());
				_hr.open();
				_hr.deleteAll();
				Cursor _c = _hr.getHome(lista.data.id);
				if (_c.getCount() == 0) {
					_hr.insertHome(lista.data);
				}
				_hr.close();
			}
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	private boolean wsEvent() {
		// TODO Auto-generated method stub
		try {
			EventWS lista = wsCommunication.wsEvent();
			if(!lista.data.isEmpty()){
				EventRepo _er = new EventRepo(getApplicationContext());
				_er.open();
				_er.deleteAll();
				for(Event item : lista.data){
					Event _event = _er.getEvent(item.id);
					if (_event.id == 0){
						_er.insertEvent(item);
					}
				}
				_er.close();
			}
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	private boolean wsAgenda() {
		// TODO Auto-generated method stub
		try {
			AgendaWS lista = wsCommunication.wsAgenda();
			if(!lista.data.isEmpty()){
				AgendaRepo _ar = new AgendaRepo(getApplicationContext());
				_ar.open();
				_ar.deleteAll();
				for(Agenda item : lista.data){
					Agenda _agenda = _ar.getAgenda(item.id);
					if(_agenda.id == 0){
						// TODO: Delete this, later.
						if (item.id == 27 || item.id == 28 || item.id == 29 || item.id == 31) {
							item.date_start = "2014-08-23 22:00:00";
							item.date_end = "2014-08-23 24:00:00";
						}
						_ar.insertAgenda(item);
					}
				}
				_ar.close();
			}
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	private boolean wsPanelist() {
		// TODO Auto-generated method stub
		try {
			PanelistWS lista = wsCommunication.wsPanelist();
			if(!lista.data.isEmpty()){
				PanelistRepo _pr = new PanelistRepo(getApplicationContext());
				_pr.open();
				_pr.deleteAll();
				for(Panelist item : lista.data){
					Panelist _pan = _pr.getPanelist(item.id);
					if(_pan.id == 0){
						_pr.insertPanelist(item);
					}
				}
				_pr.close();
			}
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	private boolean wsPasse() {
		// TODO Auto-generated method stub
		try {
			PasseWS lista = wsCommunication.wsPasse();
			if(!lista.data.isEmpty()){
				PasseRepo _pr = new PasseRepo(getApplicationContext());
				_pr.open();
				_pr.deleteAll();
				for(Passe item : lista.data){
					Passe _passe = _pr.getPasse(item.id);
					if (_passe.id == 0) {
						_pr.insertPasse(item);
					}
				}
				_pr.close();
			}
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	private boolean wsMagazine() {
		// TODO Auto-generated method stub
		try {
			MagazineWS lista = wsCommunication.wsMagazine();
			if(!lista.data.isEmpty()){
				MagazineRepo _mr = new MagazineRepo(getApplicationContext());
				_mr.open();
				_mr.deleteAll();
				for(Magazine item : lista.data){
					Magazine _mag = _mr.getMagazine(item.id);
					if(_mag.id == 0){
						_mr.insertMagazine(item);
					}
				}
				_mr.close();
			}
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	private boolean wsBook() {
		// TODO Auto-generated method stub
		try {
			BookWS lista = wsCommunication.wsBook();
			if(!lista.data.isEmpty()){
				BookRepo _br = new BookRepo(getApplicationContext());
				_br.open();
				_br.deleteAll();
				for(Book item : lista.data){
					Book book = _br.getBook(item.id);
					if (book.id == 0){
						_br.insertBook(item);
					}
				}
				_br.close();
			}
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
