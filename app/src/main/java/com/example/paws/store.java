package com.example.paws;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class store extends AppCompatActivity {
    float x1, x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_store);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView hatBob = findViewById(R.id.hatBobble);

    }

    public boolean onTouchEvent(MotionEvent touch){
        switch (touch.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touch.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touch.getX();
                if(x1 < x2){
                   /* Intent i = new Intent(MainActivity.this, idlePage.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                    finish();
                }
                break;
        }
        return false;
    }
}