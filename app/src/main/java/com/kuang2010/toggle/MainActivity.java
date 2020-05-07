package com.kuang2010.toggle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kuang2010.slidetoggle.view.SlideToggleView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlideToggleView stv_demo = findViewById(R.id.stv_demo);

        stv_demo.setOnToggleChangeListener(new SlideToggleView.OnToggleChangeListener() {
            @Override
            public void onToggleChange(View view, boolean open) {
                Toast.makeText(MainActivity.this,open?"打开":"关闭",Toast.LENGTH_SHORT).show();
            }
        });

        stv_demo.setToggleState(true);


    }
}
