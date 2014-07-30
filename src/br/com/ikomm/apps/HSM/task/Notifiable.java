package br.com.ikomm.apps.HSM.task;

import br.com.ikomm.apps.HSM.api.OperationResult;

/**
 * Notifiable.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Set 9, 2012
 */
public interface Notifiable {
	
	/**
	 * Called when a task is finished.
	 * 
	 * @param type The type of the task.
	 * @param result The OperationResult object.
	 */
	public void taskFinished(int type, OperationResult result);
}