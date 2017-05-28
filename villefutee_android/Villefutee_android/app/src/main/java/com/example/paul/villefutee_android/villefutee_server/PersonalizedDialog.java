package com.example.paul.villefutee_android.villefutee_server;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import com.example.paul.villefutee_android.multispinner;

/**
 * Created by sylvinho on 28/05/2017.
 */

public class PersonalizedDialog extends Dialog implements multispinner.multispinnerListener {
    public PersonalizedDialog(@NonNull Context context) {
        super(context);
    }

    public PersonalizedDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected PersonalizedDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void onItemschecked(boolean[] checked) {

    }
}
