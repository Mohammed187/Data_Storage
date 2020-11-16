package com.example.test;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends Activity {

    EditText phone, msg;
    Button nextBtn;

    public static final String PHONE = "PHONE";
    public static final String MSG = "MSG";
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextBtn = findViewById(R.id.next_btn);
        phone = findViewById(R.id.edit_phone);
        msg = findViewById(R.id.edit_msg);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SecondActivity.class);
                i.putExtra(PHONE, phone.getText().toString());
                i.putExtra(MSG, msg.getText().toString());
                startActivity(i);
            }
        });

    }

}
