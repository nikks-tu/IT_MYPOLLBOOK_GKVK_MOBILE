package com.techuva.iot.ngt.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.techuva.iot.ngt.table.Table;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Table table = new Table(this);

        setContentView(table);
    }
}
