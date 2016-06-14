package com.example.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Clanner on 2016/5/20.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        init();
    }

    private void init() {
        findViewById(R.id.sure).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
