package com.example.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Clanner on 2016/5/11.
 */
public class SecondActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btn_image;
    private String url = "https://www.baidu.com/img/bdlogo.png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seconactivity);

        btn_image = (Button) findViewById(R.id.btn_image);
        imageView = (ImageView) findViewById(R.id.imageView);
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(SecondActivity.this).load(url).into(imageView);
            }
        });
    }
}
