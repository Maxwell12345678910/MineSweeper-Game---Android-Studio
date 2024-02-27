package com.gamecodeschool.hw03_minesweeperpart1_maxwellhanson;

import android.os.Handler;
import android.widget.TextView;

public class Timer {
    private Handler timerHandler;
    private Runnable timerRunnable;
    private long startTime;
    private TextView timerDisp;

    public Timer(TextView timerDisp) {
        this.timerDisp = timerDisp;
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                int milliseconds = (int) (millis % 1000);
                timerDisp.setText(String.format("%d:%02d:%03d", minutes, seconds, milliseconds));
                timerHandler.postDelayed(this, 10);
            }
        };
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    public void stopTimer() {
        timerHandler.removeCallbacks(timerRunnable);
    }
}
