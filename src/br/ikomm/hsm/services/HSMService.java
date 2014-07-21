package br.ikomm.hsm.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import br.ikomm.hsm.model.Agenda;
import br.ikomm.hsm.model.Book;
import br.ikomm.hsm.model.Event;
import br.ikomm.hsm.model.Home;
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

/**
 * HSMService.java class.
 * Modified by Rodrigo Cericatto at July 16, 2014.
 */
public class HSMService extends Service implements Runnable {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private boolean mActive;
	private WSCommunication mWsCommunication = new WSCommunication();

	//--------------------------------------------------
	// Service Life Cycle
	//--------------------------------------------------
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mActive = true;
		new Thread(this, "HSMService"+ startId).start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		mActive = false;
		super.onDestroy();
	}
	
	//--------------------------------------------------
	// Runnable
	//--------------------------------------------------
	
	@Override
	public void run() {
		if (mActive) {
			if (hasConnection()) {
				wsHome();
				wsEvent();
				wsAgenda();
				wsPanelist();
				wsPasse();
				wsMagazine();
				wsBook();
				
				stopSelf();
			} else{
				stopSelf();
			}
		} else {
			stopSelf();
		}
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------

	/**
	 * Checks if we have Internet connection.
	 * 
	 * @return
	 */
	public boolean hasConnection() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Call the webservice and inserts data of {@link Home} model into the database.
	 * 
	 * @return
	 */
	public boolean wsHome() {
		try {
			HomeWS list = mWsCommunication.wsHome();
			if (list.data != null) {
				HomeRepo homeRepo = new HomeRepo(getApplicationContext());
				homeRepo.open();
				homeRepo.deleteAll();
				for (Home item : list.data) {
					Home home = homeRepo.getHome(item.id);
					if (home.id == 0) {
						homeRepo.insertHome(item);
					}
				}
				homeRepo.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Call the webservice and inserts data of {@link Event} model into the database.
	 * 
	 * @return
	 */
	public boolean wsEvent() {
		try {
			EventWS list = mWsCommunication.wsEvent();
			if (!list.data.isEmpty()) {
				EventRepo eventRepo = new EventRepo(getApplicationContext());
				eventRepo.open();
				eventRepo.deleteAll();
				for (Event item : list.data) {
					Event event = eventRepo.getEvent(item.id);
					if (event.id == 0){
						eventRepo.insertEvent(item);
					}
				}
				eventRepo.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Call the webservice and inserts data of {@link Agenda} model into the database.
	 * 
	 * @return
	 */
	public boolean wsAgenda() {
		try {
			AgendaWS list = mWsCommunication.wsAgenda();
			if (!list.data.isEmpty()) {
				AgendaRepo agendaRepo = new AgendaRepo(getApplicationContext());
				agendaRepo.open();
				agendaRepo.deleteAll();
				for (Agenda item : list.data) {
					Agenda agenda = agendaRepo.getAgenda(item.id);
					if (agenda.id == 0) {
						agendaRepo.insertAgenda(item);
					}
				}
				agendaRepo.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Call the webservice and inserts data of {@link Panelist} model into the database.
	 * 
	 * @return
	 */
	public boolean wsPanelist() {
		try {
			PanelistWS list = mWsCommunication.wsPanelist();
			if (!list.data.isEmpty()) {
				PanelistRepo panelistRepo = new PanelistRepo(getApplicationContext());
				panelistRepo.open();
				panelistRepo.deleteAll();
				for (Panelist item : list.data) {
					Panelist panelist = panelistRepo.getPanelist(item.id);
					if (panelist.id == 0) {
						panelistRepo.insertPanelist(item);
					}
				}
				panelistRepo.close();
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Call the webservice and inserts data of {@link Passe} model into the database.
	 * 
	 * @return
	 */
	public boolean wsPasse() {
		try {
			PasseWS list = mWsCommunication.wsPasse();
			if (!list.data.isEmpty()) {
				PasseRepo passeRepo = new PasseRepo(getApplicationContext());
				passeRepo.open();
				passeRepo.deleteAll();
				for (Passe item : list.data) {
					Passe passe = passeRepo.getPasse(item.id);
					if (passe.id == 0) {
						passeRepo.insertPasse(item);
					}
				}
				passeRepo.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Call the webservice and inserts data of {@link Magazine} model into the database.
	 * 
	 * @return
	 */
	public boolean wsMagazine() {
		try {
			MagazineWS list = mWsCommunication.wsMagazine();
			if (!list.data.isEmpty()) {
				MagazineRepo magazineRepo = new MagazineRepo(getApplicationContext());
				magazineRepo.open();
				magazineRepo.deleteAll();
				for (Magazine item : list.data) {
					Magazine magazine = magazineRepo.getMagazine(item.id);
					if (magazine.id == 0) {
						magazineRepo.insertMagazine(item);
					}
				}
				magazineRepo.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Call the webservice and inserts data of {@link Book} model into the database.
	 * 
	 * @return
	 */
	public boolean wsBook() {
		try {
			BookWS list = mWsCommunication.wsBook();
			if (!list.data.isEmpty()) {
				BookRepo bookRepo = new BookRepo(getApplicationContext());
				bookRepo.open();
				bookRepo.deleteAll();
				for (Book item : list.data) {
					Book book = bookRepo.getBook(item.id);
					if (book.id == 0) {
						bookRepo.insertBook(item);
					}
				}
				bookRepo.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}