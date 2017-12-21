package edu.wm.cs.cs301.amazebyminaandwils.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.wm.cs.cs301.amazebyminaandwils.R;
import edu.wm.cs.cs301.amazebyminaandwils.ui.AMazeActivity;

public class LoseActivity extends AppCompatActivity {

    private static final String TAG = ":LoseActivityLOG:";

    /**
     * Sets the xml file that corresponds to this activity as activity_lose.
     * Outputs in the logcat
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);
        Log.v(TAG, "Initializing LoseActivity");
    }

    /**
     * Goes to AMazeActivity if player presses the play again button
     * @param view
     */
    public void pressPB(View view) {
        Intent intent = new Intent(this, AMazeActivity.class);
        startActivity(intent);
        Log.v(TAG, "Play again pressed, returning to title screen");
    }
}
