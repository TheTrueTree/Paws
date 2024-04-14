package com.example.paws;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;
import java.util.Objects;

public class RunCheck implements Runnable {
    private final AppCompatActivity contextual;
    private final Handler handlers;
    boolean idle, sad;
    float current, meter;


    public RunCheck(AppCompatActivity c, Handler hanle, float bar) {
        this.contextual = c;
        this.handlers = hanle;
        this.meter = bar;
    }

    @Override
    public void run() {
        UsageStatsManager mStatManager = (UsageStatsManager) this.contextual.getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> stats = mStatManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, (System.currentTimeMillis() - 60000), System.currentTimeMillis());

        for (UsageStats ind : stats) {
            if (Objects.equals(ind.getPackageName(), "com.google.android.youtube")) {
                current = (ind.getTotalTimeInForeground() - meter);
            }
        }

        ImageView gif = contextual.findViewById(R.id.idle1);
        ImageView bar = contextual.findViewById(R.id.bar);
        ConstraintLayout scene = contextual.findViewById(R.id.main);

        System.out.println(current);
        if (!sad && (current > 20000)) {
            sad = true;
            idle = true;
            gif.setImageResource(R.drawable.sadidle);
            bar.setImageResource(R.drawable.sadbar);
            scene.setBackgroundResource(R.drawable.sadbackground);
        } else if (!idle && (current > 10000)) {
            idle = true;
            gif.setImageResource(R.drawable.idlebear);
            bar.setImageResource(R.drawable.idlebar);
            scene.setBackgroundResource(R.drawable.neutralbackground);
        }


        handlers.postDelayed(this, 1000);
    }

}



