package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.db.DatabaseAdapter;
import com.example.test.db.PhoneMsgDTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    TextView phone, msg;
    Button readShared, writeShared, readInternal, writeInternal, readSQLite , writeSQLite , closeBtn;

    Intent intent;
    SharedPreferences preferences;

    FileOutputStream fileOutputStream;
    DataOutputStream dataOutputStream;

    FileInputStream fileInputStream;
    DataInputStream dataInputStream;

    public static final String FILE_NAME = "PHONE_MSG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        intent = getIntent();

        phone = findViewById(R.id.phone_txt);
        msg = findViewById(R.id.msg_txt);
        phone.setText(intent.getStringExtra(MainActivity.PHONE));
        msg.setText(intent.getStringExtra(MainActivity.MSG));

        closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        readShared = findViewById(R.id.read_shared);
        readShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getPreferences(Context.MODE_PRIVATE);
                phone.setText(preferences.getString(MainActivity.PHONE, "N?A"));
                msg.setText(preferences.getString(MainActivity. MSG, "N?A"));
            }
        });


        writeShared = findViewById(R.id.write_shared);
        writeShared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(MainActivity.PHONE, phone.getText().toString());
                editor.putString(MainActivity.MSG, msg.getText().toString());
                editor.apply();
                phone.setText("");
                msg.setText("");
            }
        });

        readInternal = findViewById(R.id.read_internal);
        readInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fileInputStream = openFileInput(SecondActivity.FILE_NAME);
                    dataInputStream = new DataInputStream(fileInputStream);

                    phone.setText(dataInputStream.readUTF());
                    msg.setText(dataInputStream.readUTF());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        writeInternal = findViewById(R.id.write_internal);
        writeInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fileOutputStream = openFileOutput(SecondActivity.FILE_NAME, Context.MODE_PRIVATE);
                    dataOutputStream = new DataOutputStream(fileOutputStream);

                    dataOutputStream.writeUTF(phone.getText().toString());
                    dataOutputStream.writeUTF(msg.getText().toString());

                    //close the memory
                    dataOutputStream.close();
                    fileOutputStream.close();

                    phone.setText("");
                    msg.setText("");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        readSQLite = findViewById(R.id.read_sqlite);
        readSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAdapter adapter = new DatabaseAdapter(SecondActivity.this);
                PhoneMsgDTO result = adapter.getEntry();
                phone.setText(result.getPhone());
                msg.setText(result.getMsg());
            }
        });


        writeSQLite = findViewById(R.id.write_sqlite);
        writeSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAdapter adapter = new DatabaseAdapter(SecondActivity.this);
                adapter.addEntry(new PhoneMsgDTO(phone.getText().toString(), msg.getText().toString()));
                phone.setText("");
                msg.setText("");
            }
        });
    }

}