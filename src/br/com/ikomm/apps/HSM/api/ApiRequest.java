package br.com.ikomm.apps.HSM.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.JsonMappingException;

import br.com.ikomm.apps.HSM.AppConfiguration;
import br.com.ikomm.apps.HSM.api.OperationResult.ResultType;
import br.com.ikomm.apps.HSM.manager.ContentManager;
import br.com.ikomm.apps.HSM.manager.HttpManager;
import br.com.ikomm.apps.HSM.model.Agenda;
import br.com.ikomm.apps.HSM.model.Book;
import br.com.ikomm.apps.HSM.model.Event;
import br.com.ikomm.apps.HSM.model.Home;
import br.com.ikomm.apps.HSM.model.Magazine;
import br.com.ikomm.apps.HSM.model.Panelist;
import br.com.ikomm.apps.HSM.model.Passe;
import br.com.ikomm.apps.HSM.utils.StringUtils;

import com.google.gson.JsonParseException;

/**
 * ApiRequest class.
 * 
 * @author Rodrigo Cericatto
 * @since Set 9, 2012
 */
public class ApiRequest {
	
	//----------------------------------------------
	// Get Web Services
	//----------------------------------------------
	
	/**
	 * Gets the alderman list.
	 * 
	 * @return The alderman list.
	 * @throws Exception
	 */
	public static OperationResult getApiList(String serviceUrl, Integer type) throws Exception {
		// Gets the url of this service.
		String url = AppConfiguration.SERVER_IP + serviceUrl;
		
		// Holds the operation result.
		OperationResult result = null;
		
		// Getting data from server.
		try {
			result = HttpManager.getInstance().executeHttpGetWithRetry(url);
		} catch (Exception e) {
		}
		
		// Checking for errors.
		if (!ResultType.SUCCESS.equals(result.getResultType())) {
			return result;
		}
		
		// Getting JSON string.
		String jsonString = result.getResponseString();
		if (StringUtils.isEmpty(jsonString)) {
			// Set the result as parsing error.
			result.setResultType(ResultType.PARSING_ERROR);
			return result;
		}
		
		// TODO: Increase this command.
		List<Object> list = getApiParsedList(jsonString, type);
		result.setEntityList(list);
		return result;
	}
	
	//----------------------------------------------
	// Generic Models 
	//----------------------------------------------
	
	/**
	 * Returns a generic list, that can be list of any Model. 
	 * 
	 * @param jsonString The jsonString to be parsed.
	 * @param type The type (class) of the model of the generic list.
	 * 
	 * @return The generic list.
	 * 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getApiParsedList(String jsonString, Integer type)
		throws JsonParseException, JsonMappingException, IOException {
		
		// Gets JSON data.
		Map<String, Object> jsonData = (Map<String, Object>)ApiMapper.parseMap(jsonString);
		
		// Variables.
		List<Object> list = new ArrayList<Object>();
		Object object = null;
		String key = getKey(type);
		
		// Parsing JSON.
		if (jsonData.containsKey(key)) {
			for (Map<String, Object> item : (List<Map<String, Object>>)jsonData.get(key)) {
				try {
					object = getValue(type, item);
				} catch (Exception e) {
					e.printStackTrace();
				}
				list.add(object);
			}
		}
		// Return of the list.
		return list;
	}
	
	/**
	 * Gets the KEY_ROOT.
	 * 
	 * @param type The type of the model class.
	 * 
	 * @return The KEY_ROOT.
	 */
	public static String getKey(Integer type) {
		String key = "";
		
		// Verification.
		switch (type) {
			case ContentManager.FETCH_TASK.AGENDA:
				key = Agenda.KEY_ROOT;
				break;
			case ContentManager.FETCH_TASK.BOOK:
				key = Book.KEY_ROOT;
				break;
			case ContentManager.FETCH_TASK.EVENT:
				key = Event.KEY_ROOT;
				break;
			case ContentManager.FETCH_TASK.HOME:
				key = Home.KEY_ROOT;
				break;
			case ContentManager.FETCH_TASK.MAGAZINE:
				key = Magazine.KEY_ROOT;
				break;
			case ContentManager.FETCH_TASK.PANELIST:
				key = Panelist.KEY_ROOT;
				break;
			case ContentManager.FETCH_TASK.PASSE:
				key = Passe.KEY_ROOT;
				break;
		}
		
		return key;
	}
	
	/**
	 * Gets the KEY_ROOT.
	 * 
	 * @param type The type of the model class.
	 * 
	 * @return The KEY_ROOT.
	 * 
	 * @throws Exception 
	 */
	public static Object getValue(Integer type, Map<String, Object> item) throws Exception {
		Object object = null;
		
		// Verification.
		switch (type) {
			case ContentManager.FETCH_TASK.AGENDA:
				object = ApiMapper.getAgendaFromJson(item);
				break;
			case ContentManager.FETCH_TASK.BOOK:
				object = ApiMapper.getBookFromJson(item);
				break;
			case ContentManager.FETCH_TASK.EVENT:
				object = ApiMapper.getEventFromJson(item);
				break;
			case ContentManager.FETCH_TASK.HOME:
				object = ApiMapper.getHomeFromJson(item);
				break;
			case ContentManager.FETCH_TASK.MAGAZINE:
				object = ApiMapper.getMagazineFromJson(item);
				break;
			case ContentManager.FETCH_TASK.PANELIST:
				object = ApiMapper.getPanelistFromJson(item);
				break;
			case ContentManager.FETCH_TASK.PASSE:
				object = ApiMapper.getPasseFromJson(item);
				break;
		}
		return object;
	}
}