package br.com.ikomm.apps.HSM.utils;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import br.com.ikomm.apps.HSM.model.Agenda;

/**
 * DateUtils.java class.
 * 
 * @author Rodrigo Cericatto
 * @since July 25, 2012
 */
public class DateUtils {
	
	/**
	 * Checks if the Lecture is the current one.
	 * 
	 * @param agenda
	 * @return
	 */
	public static Boolean isTheCurrentLecture(Agenda agenda) {
//		Long currentTimeInMillis = System.currentTimeMillis();
		Long currentTimeInMillis = 1415014200000L;
		String currentDate = getDate(currentTimeInMillis, "yyyyMMddHHmm");
		String startDate = getDateFromString(agenda.date_start);
		String endDate = getDateFromString(agenda.date_end);
		
		// Checks if is the current lecture.
		Boolean firstCondition = false;
		Boolean secondCondition = false;
		Integer result = 0;
		
		result = currentDate.compareTo(startDate);
		if (result > 0) {
			firstCondition = true;
		}
		
		result = currentDate.compareTo(endDate);
		if (result < 0) {
			secondCondition = true;
		}
		
		// Return.
		Boolean back = false; 
		if (firstCondition && secondCondition) {
			back = true;
		} else {
			back = false;
		}
			
		return back;
	}
	
	/**
	 * Gets the current time in a specific format.
	 * 
	 * @param milli
	 * @param format
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDate(Long milli, String format) {
        SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(format);
        return simpleDateFormatter.format(milli);
    }
	
	/**
	 * Gets the current time in a specific format.
	 * 
	 * @param text
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateFromString(String text) {
		String formattedText = StringUtils.trimAllCharacters(text);
		return formattedText;
    }
}