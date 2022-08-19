package com.example.reminder.helpers;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

public abstract class TextValidator implements TextWatcher {
    private final TextView textView;

    protected TextValidator(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    public abstract void validate(TextView textView, String text);
}
