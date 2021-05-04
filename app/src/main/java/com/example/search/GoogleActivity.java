package com.example.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GoogleActivity extends AppCompatActivity {
    TextView textView;
    String string;
    String search;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        textView=(TextView) findViewById(R.id.textView);

        string=getIntent().getExtras().getString("Value");
        textView.setText(string);


        button=(Button) findViewById(R.id.buttonSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search =textView.getText().toString();
                try {
                    search = URLEncoder.encode(search, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.parse("http://www.google.com/#q=" + search);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri + search));
                String id = uri.getQueryParameter("id");
                String _prof = uri.getQueryParameter("_prof");
                startActivity(intent);
                finish();
            }
        });
    }
}

