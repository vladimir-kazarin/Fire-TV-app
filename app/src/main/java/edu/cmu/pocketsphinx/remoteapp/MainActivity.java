package edu.cmu.pocketsphinx.remoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "SettingsActivity" item, show the app settings UI...
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void onConnectClick(View v) {
        String ipAddress = ((TextView) this.findViewById(R.id.ipAddress)).getText().toString();
        String out = runCommand(String.format("adb connect %s", ipAddress));

        this.findViewById(R.id.up_btn).setOnClickListener(this);
        this.findViewById(R.id.down_btn).setOnClickListener(this);
        this.findViewById(R.id.left_btn).setOnClickListener(this);
        this.findViewById(R.id.right_btn).setOnClickListener(this);
        this.findViewById(R.id.enter_btn).setOnClickListener(this);
        this.findViewById(R.id.home_btn).setOnClickListener(this);
        this.findViewById(R.id.back_btn).setOnClickListener(this);
        this.findViewById(R.id.play_pause_btn).setOnClickListener(this);
        this.findViewById(R.id.prev_btn).setOnClickListener(this);
        this.findViewById(R.id.next_btn).setOnClickListener(this);
        this.findViewById(R.id.menu_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id;
        switch (v.getId()) {
            case R.id.up_btn:
                id = 19;
                break;
            case R.id.down_btn:
                id = 20;
                break;
            case R.id.left_btn:
                id = 21;
                break;
            case R.id.right_btn:
                id = 22;
                break;
            case R.id.enter_btn:
                id = 66;
                break;
            case R.id.home_btn:
                id = 3;
                break;
            case R.id.back_btn:
                id = 4;
                break;
            case R.id.play_pause_btn:
                id = 85;
                break;
            case R.id.prev_btn:
                id = 88;
                break;
            case R.id.next_btn:
                id = 87;
                break;
            case R.id.menu_btn:
                id = 1;
                break;
            default:
                return;
        }
        runCommand(String.format("adb shell input %d", id));
    }

    private String runCommand(String cmd) {
        Process exec = null;
        String output = "";
        try {
            exec = Runtime.getRuntime().exec(cmd);

            BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));

            StringBuilder out = new StringBuilder();
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    out.append(line);
                }
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            Toast.makeText(this, out.toString(), Toast.LENGTH_LONG).show();
            output = out.toString();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return output;
    }
}
