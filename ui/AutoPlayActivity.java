package edu.wm.cs.cs301.amazebyminaandwils.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import edu.wm.cs.cs301.amazebyminaandwils.R;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.BasicRobot;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.MazePanel;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.Pledge;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.RobotDriver;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.WallFollower;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.Wizard;

public class AutoPlayActivity extends AppCompatActivity {


    private static final String TAG = ":AutoPlayActivityLOG:";
    public MazePanel mp;
    MazeController controller;
    private BasicRobot rob;


    /**
     * Creates logs and toasts for every user selection that is made in AutoPlayActivity screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_auto);

        mp = (MazePanel)findViewById(R.id.MPb);
        DataHolder app = DataHolder.getInstance();
        app.setHasWon(false);
        app.setPanel(mp);
        controller = app.getController();

        Log.v(TAG, "Initializing AutoPlayActivity");


        ToggleButton togglePlay = (ToggleButton) findViewById(R.id.toggleButton);
        togglePlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v(TAG, "Pause/play button toggled");
                DataHolder app = DataHolder.getInstance();
                if (isChecked) {
                    app.setMazeToggle(true);

                    try {
                        controller.runDeliver();
                        } catch (Exception e) {
                        Log.v(TAG, "Catch reached");
                        e.printStackTrace();
                    }
                } else {
                    app.setMazeToggle(false);
                }
            }
        });
        ToggleButton toggleWalls = (ToggleButton) findViewById(R.id.toggleButton5);
        toggleWalls.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v(TAG, "Minimap walls button toggled");
                    if (isChecked) {
                        controller.keyDown(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController.UserInput.ToggleLocalMap, 1);
                        Log.v(TAG, "Minimap walls toggled on");
                    } else {
                        controller.keyDown(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController.UserInput.ToggleLocalMap, 1);
                        Log.v(TAG, "Minimap walls toggled off");
                    }

            }
        });
        ToggleButton toggleMaze = (ToggleButton) findViewById(R.id.toggleButton6);
        toggleMaze.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v(TAG, "Minimap maze button toggled");
                if (isChecked) {
                    controller.keyDown(MazeController.UserInput.ToggleFullMap, 1);
                } else {
                    controller.keyDown(MazeController.UserInput.ToggleFullMap, 1);
                }
            }
        });
        ToggleButton toggleSolution = (ToggleButton) findViewById(R.id.toggleButton7);
        toggleSolution.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v(TAG, "Minimap solution button toggled");
                if (isChecked) {
                    controller.keyDown(MazeController.UserInput.ToggleSolution, 1);
                } else {
                    controller.keyDown(MazeController.UserInput.ToggleSolution, 1);
                }
            }
        });
        init();
    }
    private void init() {
        rob = new BasicRobot(controller);
        controller.setRobot(rob);

        DataHolder app = DataHolder.getInstance();
        controller.switchToPlayingScreen();
        Log.v(TAG, "entering \"runDeliver\"");
    }
    public void funMethod (View view) {
        Intent intent = new Intent(getApplicationContext(), WinActivity.class);
        startActivity(intent);
    }

}
