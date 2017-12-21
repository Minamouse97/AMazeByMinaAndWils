package edu.wm.cs.cs301.amazebyminaandwils.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.wm.cs.cs301.amazebyminaandwils.R;

public class WinActivity extends AppCompatActivity {

    private static final String TAG = ":WinActivityLOG:";

    /**
     * Links the xml file activity_win to this file.
     * Outputs in logcat that WinActivity in being initialized.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        Log.v(TAG, "Initializing WinActivity");
    }

    /**
     * Goes to AMazeActivity
     * @param view
     */
    public void pressPA(View view) {
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
        Log.v(TAG, "Play again pressed, returning to title screen");
    }
}
