package com.example.calculator; // Make sure the package matches your app's package name

import android.app.Activity;
import android.os.Bundle;

public class DummyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy_page);
    }
}

