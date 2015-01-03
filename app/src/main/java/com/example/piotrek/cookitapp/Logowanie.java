package com.example.piotrek.cookitapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ProgressBar;


public class Logowanie extends ActionBarActivity
{
    ProgressBar silaHasla;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        silaHasla = (ProgressBar) findViewById(R.id.silaHasla);
        silaHasla.setProgress(50);
    }
}