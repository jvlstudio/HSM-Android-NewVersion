package br.ikomm.hsm.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

public class ActionBarCustom {

	/**
	 * action bar background
	 * @param activity
	 * @param color
	 * @param changeTextColorToWhite
	 */
	public void setActionBarBackgroundColor(
			Activity activity, 
			String color, 
			boolean changeTextColorToWhite)
	{
		activity.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
        int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        if (actionBarTitleId > 0) {
            TextView title = (TextView) activity.findViewById(actionBarTitleId);
            if (title != null && changeTextColorToWhite) {
                title.setTextColor(Color.WHITE);
            } else{
            	title.setTextColor(Color.parseColor("#00ACFF"));
            }
        }
	}
}
