package com.m1noon.countview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.m1noon.countview.CountView;

public class MainActivity extends AppCompatActivity {

    private static final String FORMAT_THREE_SEPARATE = "%1$,d";
    private CountView countView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        countView = (CountView) findViewById(R.id.count_view);
        countView.interpolator(new AccelerateDecelerateInterpolator())
                .formatCommaSeparated()
                .velocity(10)
                .maxDuration(3000);

        Button resetButton = (Button) findViewById(R.id.btn_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countView.to(0, false);
            }
        });

        Button upButton = (Button) findViewById(R.id.btn_up);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countView.up(100);
            }
        });

        Button downButton = (Button) findViewById(R.id.btn_down);
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countView.down(100);
            }
        });


        final SeekBar velocitySeekBar = (SeekBar) findViewById(R.id.velocity_seek_bar);
        final TextView velocityTextView = (TextView) findViewById(R.id.velocity_setting_num);
        velocitySeekBar.setMax(300);
        velocitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = Math.max(1, progress);
                countView.velocity(value);
                velocityTextView.setText(String.valueOf(value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });
        velocitySeekBar.setProgress(150);

        final SeekBar maxDurationSeekBar = (SeekBar) findViewById(R.id.max_duration_seek_bar);
        final TextView maxDurationTextView = (TextView) findViewById(R.id.max_duration_setting_num);
        maxDurationSeekBar.setMax(100);
        maxDurationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final int value = Math.max(1, progress) * 1000;
                countView.maxDuration(value);
                maxDurationTextView.setText(String.format(FORMAT_THREE_SEPARATE, value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });
        maxDurationSeekBar.setProgress(50);
    }

}
