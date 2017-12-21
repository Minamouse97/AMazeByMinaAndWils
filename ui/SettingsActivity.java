package edu.wm.cs.cs301.amazebyminaandwils.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import edu.wm.cs.cs301.amazebyminaandwils.R;

public class SettingsActivity extends AppCompatActivity {


    private static final String TAG = ":SettingsActivityLOG:";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.v(TAG, "Initializing SettingsActivity");


        Spinner spnGame = (Spinner)findViewById(R.id.spinner19);

        /**
         * Sets an item listener on the spinner to keep track of what is selected
         */
        spnGame.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Makes a toast message that tells the user what driver was selected
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataHolder app = DataHolder.getInstance();
                app.setGameTheme(parent.getSelectedItem().toString());
                Log.v(TAG, "GameTheme selected and stored in DataHolder as ~" + parent.getSelectedItem().toString() + "~");
            }

            /**
             * If nothing is selected, nothing happens
             * @param parent
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        Spinner spnMusic = (Spinner)findViewById(R.id.spinner20);

        /**
         * Sets an item listener on the spinner to keep track of what is selected
         */
        spnMusic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Makes a toast and log output that tests to make sure that the selection is being kept track of
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataHolder app = DataHolder.getInstance();
                app.setMusicTheme(parent.getSelectedItem().toString());
                Log.v(TAG, "MusicTheme selected and stored in DataHolder as ~" + parent.getSelectedItem().toString() + "~");            }

            /**
             * If nothing is selected, nothing happens
             * @param parent
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


    }
}
