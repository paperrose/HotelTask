package com.artfonapps.hotelstask.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.artfonapps.hotelstask.constants.Strings;

public class PDManager {
    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.setTitle(Strings.UPLOAD_TITLE);
        pd.setMessage(Strings.UPLOAD_MESSAGE);
        pd.setIndeterminate(true);
        pd.show();
        return pd;
    }
}
