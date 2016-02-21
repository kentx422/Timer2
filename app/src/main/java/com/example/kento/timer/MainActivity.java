package com.example.kento.timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //test2
    private Button startButton, stopButton;
    private TextView timerText;

    private Timer timer;
    private CountUpTimerTask timerTask = null;
    private Handler handler = new Handler();
    private long count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.start_button);
        stopButton = (Button) findViewById(R.id.stop_button);

        timerText = (TextView) findViewById(R.id.timer);
        timerText.setText("00:00.0");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != timer) {
                    timer.cancel();
                    timer = null;
                }

                // Timer インスタンスを生成
                timer = new Timer();

                // TimerTask インスタンスを生成
                timerTask = new CountUpTimerTask();

                // スケジュールを設定 100msec
                // public void schedule (TimerTask task, long delay, long period)
                timer.schedule(timerTask, 0, 100);

                // カウンター
                count = 0;
                timerText.setText("00:00.0");

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != timer) {
                    // Cancel
                    timer.cancel();
                    timer = null;
                    timerText.setText("00:00.0");
                }
            }
        });
    }

    class CountUpTimerTask extends TimerTask {
        @Override
        public void run() {
            // handlerを使って処理をキューイングする
            handler.post(new Runnable() {
                public void run() {
                    count++;
                    long mm = count * 100 / 1000 / 60;
                    long ss = count * 100 / 1000 % 60;
                    long ms = (count * 100 - ss * 1000 - mm * 1000 * 60) / 100;
                    timerText.setText(String.format("%1$02d:%2$02d.%3$01d", mm, ss, ms));
                }
            });
        }
    }
}