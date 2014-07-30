package br.com.ikomm.apps.HSM.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import br.com.ikomm.apps.HSM.R;

/**
 * DialogUtils class.
 * 
 * @author Rodrigo Cericatto
 * @since Set 9, 2012
 */
public class DialogUtils {

	/**
	 * Creates a list dialog.
	 * 
	 * @param context The context to show the dialog.
	 * @param items The list of items.
	 * @param title The title text.
	 * @param listener The click listener.
	 */
	public static void showListDialog(Context context, CharSequence[] items, CharSequence title, OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setItems(items, listener);
		builder.show();
	}

	/**
	 * Create simple progress dialog with the given message resource.
	 * 
	 * @param context
	 * @param messageResource
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, int messageResource) {
		return showProgressDialog(context, messageResource, false);
	}
	
	/**
	 * Create simple progress dialog with the given message resource.
	 * 
	 * @param context
	 * @param messageResource
	 * @param cancelable
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, int messageResource, boolean cancelable) {
		String message = context.getString(messageResource);
		return showProgressDialog(context, message, cancelable);
	}
	
	/**
	 * Create simple progress dialog with the given message resource.
	 * 
	 * @param context
	 * @param message
	 * @param cancelable
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, String message, boolean cancelable) {
		ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
	}
	
	/**
	 * Creates simple alert dialog with the given title and message resource.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 */
	public static void showSimpleAlert(Context context, String titleResource, String messageResource) {
		showSimpleAlert(context, titleResource, messageResource, null);
	}
	
	/**
	 * Creates simple alert dialog with the given title and message resource.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 */
	public static void showSimpleAlert(Context context, int titleResource, int messageResource) {
		showSimpleAlert(context, titleResource, messageResource, null);
	}

	/**
	 * Creates simple alert dialog with the given title and message resource.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 * @param listener
	 */
	public static void showSimpleAlert(Context context, int titleResource, int messageResource, OnClickListener listener) {
		String dialogTitle = titleResource > 0 ? context.getString(titleResource) : null;
		String dialogMessage = messageResource > 0 ? context.getString(messageResource) : null;
		showSimpleAlert(context, dialogTitle, dialogMessage, listener);
	}
	
	/**
	 * Creates simple alert dialog with the given title and message resource.
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param listener
	 */
	public static void showSimpleAlert(Context context, String title, String message, OnClickListener listener) {
		if (context instanceof Activity && ((Activity)context).isFinishing()) {
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setPositiveButton(R.string.dialog_ok, listener);
		builder.show();
	}
	
	/**
	 * Shows a confirm dialog.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 * @param confirmLabelResource
	 * @param confirmListener
	 */
	public static void showConfirmDialog(Context context, int titleResource, int messageResource, int confirmLabelResource, OnClickListener confirmListener) {
		showConfirmDialog(context, titleResource, messageResource, confirmLabelResource, confirmListener, 0, null);
	}
	
	/**
	 * Shows a confirm dialog.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 * @param confirmListener
	 * @param cancelListener
	 */
	public static void showConfirmDialog(Context context, int titleResource, int messageResource, OnClickListener confirmListener, OnClickListener cancelListener) {
		showConfirmDialog(context, titleResource, messageResource, 0, confirmListener, 0, cancelListener);
	}

	/**
	 * Shows a confirm dialog.
	 * 
	 * @param context
	 * @param titleResource
	 * @param messageResource
	 * @param confirmLabelResource
	 * @param confirmListener
	 * @param cancelLabelResource
	 * @param cancelListener
	 */
    public static void showConfirmDialog(Context context, int titleResource, int messageResource, int confirmLabelResource, OnClickListener confirmListener, 
    	int cancelLabelResource, OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setIcon(android.R.drawable.ic_dialog_alert);

        if (titleResource != 0) builder.setTitle(titleResource);
        if (messageResource != 0) builder.setMessage(messageResource);
        if (confirmLabelResource == 0) confirmLabelResource = R.string.dialog_ok;
        if (cancelLabelResource == 0) cancelLabelResource = R.string.dialog_cancel;

        builder.setPositiveButton(confirmLabelResource, confirmListener);
        if (cancelListener != null) {
            builder.setNegativeButton(cancelLabelResource, cancelListener);
        } else {
            builder.setNegativeButton(cancelLabelResource, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder.show();
    }
    
	/**
	 * Shows a no Internet connection dialog.
	 * 
	 * @param context
	 */
	public static void showNoConnectionDialog(Context context) {
		showSimpleAlert(context, R.string.error_title_no_internet, R.string.error_msg_no_internet);
	}
	
	/**
	 * Shows a custom layout dialog.
	 * 
	 * @param context
	 * @param layoutId
	 */
	public static void showCustomDialog(final Context context, int layoutId) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    LayoutInflater inflater = ((Activity) context).getLayoutInflater();

	    builder.setView(inflater.inflate(layoutId, null))
	    	.setPositiveButton(R.string.dialog_ok,
	    		new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {}
	    	})
	    	.setNegativeButton(R.string.dialog_cancel,
	    		new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {}
	    	});      
	    builder.create();
	    builder.show();
	}
	
	/**
	 * Calls a date picker dialog.
	 * 
	 * @param context
	 * @param listener
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static DatePickerDialog showDatePickerDialog(Context context, OnDateSetListener listener, int year, int month, int day) {
		DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, year, month, day); 
		datePickerDialog.show();
		return datePickerDialog;
	}
	
	/**
	 * Calls a time picker dialog.
	 * 
	 * @param context
	 * @param listener
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static TimePickerDialog showTimePickerDialog(Context context, OnTimeSetListener listener, int hour, int minute) {
		TimePickerDialog timePickerDialog = new TimePickerDialog(context, listener, hour, minute, true); 
		timePickerDialog.show();
		return timePickerDialog;
	}
}