package br.com.ikomm.apps.HSM.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.Book;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Home;
import br.com.ikomm.apps.HSM.model.Magazine;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.model.Passe;

import com.google.gson.JsonParseException;

/**
 * ApiMapper.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Set 9, 2012
 */
public class ApiMapper {

	//----------------------------------------------
	// Statics
	//----------------------------------------------
	
	// The JSON object mapping parser.
	private static final ObjectMapper sObjectMapper = new ObjectMapper();
	
	//----------------------------------------------
	// JSON Parsing
	//----------------------------------------------

	/**
	 * Parse JSON string into a list.
	 * 
	 * @param data
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static List<?> parseList(String data) throws JsonParseException, JsonMappingException, IOException {
		return (List<?>)parseObject(data, List.class);
	}
	
	/**
	 * Parse JSON string into a map.
	 * 
	 * @param data
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Map<?, ?> parseMap(String data) throws JsonParseException, JsonMappingException, IOException {
		return (Map<?, ?>)parseObject(data, Map.class);
	}
	
	/**
	 * Parse JSON string to the specified object.
	 * 
	 * @param data
	 * @param clazz
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Object parseObject(String data, Class<?> clazz) throws JsonParseException, JsonMappingException, IOException {
		return sObjectMapper.readValue(data, clazz);
	}
	
	//----------------------------------------------
	// Agenda
	//----------------------------------------------
	
	/**
	 * Gets the {@link Agenda} from the given JSON data.
	 * 
	 * @param data The JSON data that contains the {@link Agenda} info.
	 * 
	 * @return The current {@link Agenda}.
	 * @throws Exception 
	 */
	public static Agenda getAgendaFromJson(Map<String, Object> data) throws Exception {
		Agenda agenda = new Agenda();
		
		if (data != null) {
			agenda.setId(getInt(data.get(Agenda.KEY_ID)));
			agenda.setType(getString(data.get(Agenda.KEY_TYPE)));
			agenda.setEventId(getInt(data.get(Agenda.KEY_EVENT_ID)));
			agenda.setPanelistId(getInt(data.get(Agenda.KEY_PANELIST_ID)));
			agenda.setDate(getInt(data.get(Agenda.KEY_DATE)));
			agenda.setDateStart(getString(data.get(Agenda.KEY_DATE_START)));
			agenda.setDateEnd(getString(data.get(Agenda.KEY_DATE_END)));
			agenda.setThemeTitle(getString(data.get(Agenda.KEY_THEME_TITLE)));
			agenda.setThemeDescription(getString(data.get(Agenda.KEY_THEME_DESCRIPTION)));
			agenda.setLabel(getString(data.get(Agenda.KEY_LABEL)));
			agenda.setSublabel(getString(data.get(Agenda.KEY_SUBLABEL)));
			agenda.setImage(getString(data.get(Agenda.KEY_IMAGE)));
		} else {
			agenda = null;
		}
		return agenda;
	}
	
	//----------------------------------------------
	// Book
	//----------------------------------------------
	
	/**
	 * Gets the {@link Book} from the given JSON data.
	 * 
	 * @param data The JSON data that contains the Book info.
	 * 
	 * @return The current {@link Book}.
	 * @throws Exception 
	 */
	public static Book getBookFromJson(Map<String, Object> data) throws Exception {
		Book book = new Book();
		
		if (data != null) {
			book.setId(getInt(data.get(Book.KEY_ID)));
			book.setName(getString(data.get(Book.KEY_NAME)));
			book.setPicture(getString(data.get(Book.KEY_PICTURE)));
			book.setDescription(getString(data.get(Book.KEY_DESCRIPTION)));
			book.setAuthorName(getString(data.get(Book.KEY_AUTHOR_NAME)));
			book.setAuthorDescription(getString(data.get(Book.KEY_AUTHOR_DESCRIPTION)));
			book.setLink(getString(data.get(Book.KEY_LINK)));
		} else {
			book = null;
		}
		return book;
	}
	
	//----------------------------------------------
	// Event
	//----------------------------------------------
	
	/**
	 * Gets the {@link Event} from the given JSON data.
	 * 
	 * @param data The JSON data that contains the Event info.
	 * 
	 * @return The current {@link Event}.
	 * @throws Exception 
	 */
	public static Event getEventFromJson(Map<String, Object> data) throws Exception {
		Event event = new Event();
		
		if (data != null) {
			event.setId(getInt(data.get(Event.KEY_ID)));
			event.setName(getString(data.get(Event.KEY_NAME)));
			event.setSlug(getString(data.get(Event.KEY_SLUG)));
			event.setDescription(getString(data.get(Event.KEY_DESCRIPTION)));
			event.setTinyDescription(getString(data.get(Event.KEY_TINY_DESCRIPTION)));
			event.setInfoDates(getString(data.get(Event.KEY_INFO_DATES)));
			event.setInfoHours(getString(data.get(Event.KEY_INFO_HOURS)));
			event.setInfoLocale(getString(data.get(Event.KEY_INFO_LOCALE)));
			event.setImageList(getString(data.get(Event.KEY_IMAGE_LIST)));
			event.setImageSingle(getString(data.get(Event.KEY_IMAGE_SINGLE)));
		} else {
			event = null;
		}
		return event;
	}
	
	//----------------------------------------------
	// Home
	//----------------------------------------------
	
	/**
	 * Gets the {@link Home} from the given JSON data.
	 * 
	 * @param data The JSON data that contains the Home info.
	 * 
	 * @return The current {@link Home}.
	 * @throws Exception 
	 */
	public static Home getHomeFromJson(Map<String, Object> data) throws Exception {
		Home home = new Home();
		
		if (data != null) {
			home.setId(getInt(data.get(Home.KEY_ID)));
			home.setEventsTitle(getString(data.get(Home.KEY_EVENTS_TITLE)));
			home.setEventsImageAndroid(getString(data.get(Home.KEY_EVENTS_IMAGE_ANDROID)));
			home.setEducationTitle(getString(data.get(Home.KEY_EDUCATION_TITLE)));
			home.setEducationImageAndroid(getString(data.get(Home.KEY_EDUCATION_IMAGE_ANDROID)));
			home.setVideosTitle(getString(data.get(Home.KEY_VIDEOS_TITLE)));
			home.setVideosImageAndroid(getString(data.get(Home.KEY_VIDEOS_IMAGE_ANDROID)));
			home.setMagazinesTitle(getString(data.get(Home.KEY_MAGAZINES_TITLE)));
			home.setMagazinesImageAndroid(getString(data.get(Home.KEY_MAGAZINES_IMAGE_ANDROID)));
			home.setBooksTitle(getString(data.get(Home.KEY_BOOKS_TITLE)));
			home.setBooksImageAndroid(getString(data.get(Home.KEY_BOOKS_IMAGE_ANDROID)));
		} else {
			home = null;
		}
		return home;
	}
	
	//----------------------------------------------
	// Magazine
	//----------------------------------------------
	
	/**
	 * Gets the {@link Magazine} from the given JSON data.
	 * 
	 * @param data The JSON data that contains the Magazine info.
	 * 
	 * @return The current {@link Magazine}.
	 * @throws Exception 
	 */
	public static Magazine getMagazineFromJson(Map<String, Object> data) throws Exception {
		Magazine magazine = new Magazine();
		
		if (data != null) {
			magazine.setId(getInt(data.get(Magazine.KEY_ID)));
			magazine.setName(getString(data.get(Magazine.KEY_NAME)));
			magazine.setPicture(getString(data.get(Magazine.KEY_PICTURE)));
			magazine.setDescription(getString(data.get(Magazine.KEY_DESCRIPTION)));
			magazine.setLink(getString(data.get(Magazine.KEY_LINK)));
		} else {
			magazine = null;
		}
		return magazine;
	}
	
	//----------------------------------------------
	// Panelist
	//----------------------------------------------
	
	/**
	 * Gets the {@link Panelist} from the given JSON data.
	 * 
	 * @param data The JSON data that contains the Panelist info.
	 * 
	 * @return The current {@link Panelist}.
	 * @throws Exception 
	 */
	public static Panelist getPanelistFromJson(Map<String, Object> data) throws Exception {
		Panelist panelist = new Panelist();
		
		if (data != null) {
			panelist.setId(getInt(data.get(Panelist.KEY_ID)));
			panelist.setName(getString(data.get(Panelist.KEY_NAME)));
			panelist.setSlug(getString(data.get(Panelist.KEY_SLUG)));
			panelist.setDescription(getString(data.get(Panelist.KEY_DESCRIPTION)));
			panelist.setPicture(getString(data.get(Panelist.KEY_PICTURE)));
		} else {
			panelist = null;
		}
		return panelist;
	}
	
	//----------------------------------------------
	// Passe
	//----------------------------------------------
	
	/**
	 * Gets the {@link Passe} from the given JSON data.
	 * 
	 * @param data The JSON data that contains the Passe info.
	 * 
	 * @return The current {@link Passe}.
	 * @throws Exception 
	 */
	public static Passe getPasseFromJson(Map<String, Object> data) throws Exception {
		Passe passe = new Passe();
		
		if (data != null) {
			passe.setId(getInt(data.get(Passe.KEY_ID)));
			passe.setEventId(getInt(data.get(Passe.KEY_EVENT_ID)));
			passe.setColor(getString(data.get(Passe.KEY_COLOR)));
			passe.setName(getString(data.get(Passe.KEY_NAME)));
			passe.setSlug(getString(data.get(Passe.KEY_SLUG)));
			passe.setPriceFrom(getString(data.get(Passe.KEY_PRICE_FROM)));
			passe.setPriceTo(getString(data.get(Passe.KEY_PRICE_TO)));
			passe.setValidTo(getString(data.get(Passe.KEY_VALID_TO)));
			passe.setEmail(getString(data.get(Passe.KEY_EMAIL)));
			passe.setDescription(getString(data.get(Passe.KEY_DESCRIPTION)));
			passe.setDays(getString(data.get(Passe.KEY_DAYS)));
			passe.setShowDates(getString(data.get(Passe.KEY_SHOW_DATES)));
			passe.setIsMultiple(getString(data.get(Passe.KEY_IS_MULTIPLE)));
		} else {
			passe = null;
		}
		return passe;
	}
	
	//----------------------------------------------
	// Methods
	//----------------------------------------------
	
	/**
	 * Return the string value from an object.
	 * 
	 * @param object The checked object. 
	 * @return It's string value.
	 */
	private static String getString(Object object) {
		String value = "";
		if (object != null) {
			try {
				value = (String)object;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
    /**
     * Returns primitive integer from object with a default value of 0 for non integers.
     * 
     * @param object
     * @param defaultValue
     * @return
     */
    private static int getInt(Object object) {
        if (object == null) return 0;
        try {
            return Integer.parseInt(object.toString());
        } catch(NumberFormatException e) {
            return 0;
        }
    }
    
    /**
     * Returns primitive double from object with a default value of 0.0 for non double.
     * 
     * @param object
     * @param defaultValue
     * @return
     */
    @SuppressWarnings("unused")
	private static double getDouble(Object object) {
    	if (object == null) return 0;
    	try {
    		return Double.parseDouble(object.toString());
    	} catch(NumberFormatException e) {
    		return 0.0;
    	}
    }
}