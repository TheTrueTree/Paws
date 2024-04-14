package com.example.paws;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Objects;

public class idlePage extends AppCompatActivity {
    float x1, x2, meter, current;
    boolean idle, sad;
    Handler handler = new Handler();;
    RunCheck runableInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler.removeCallbacksAndMessages(RunCheck.class);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_idle_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        UsageStatsManager mStatManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> stats = mStatManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, (System.currentTimeMillis() - 60000), System.currentTimeMillis());
        for (UsageStats ind : stats) {
            if (Objects.equals(ind.getPackageName(), "com.google.android.youtube")) {
                meter = ind.getTotalTimeInForeground();
            }
        }
        runableInt = new RunCheck(this, handler, meter);
        handler.post(runableInt);
    }


    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(RunCheck.class);
    }

    public boolean onTouchEvent(MotionEvent touch) {
        switch (touch.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touch.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touch.getX();
                if ((x2 - x1) > 30) {
                    Intent i = new Intent(idlePage.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else if ((x1 - x2) > 30) {
                    Intent i = new Intent(idlePage.this, store.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
        return false;
    }
}

