package br.ikomm.hsm.util;

public interface TaskCallback {

	void onPreExecute() throws Exception;

	/**
	 * Thread para executar transações
	 * 
	 * @throws Exception
	 */
	void execute() throws Exception;

	/**
	 * UI Thread para atualizar a View
	 */
	void updateView();

	/**
	 * Some error ocurrs
	 * 
	 * @param e
	 */
	void error(Throwable e);

}
