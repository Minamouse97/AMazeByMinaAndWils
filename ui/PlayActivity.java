package edu.wm.cs.cs301.amazebyminaandwils.ui;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import edu.wm.cs.cs301.amazebyminaandwils.R;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.MazePanel;
import edu.wm.cs.cs301.amazebyminaandwils.ui.DataHolder;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.ManualDriver;

import static edu.wm.cs.cs301.amazebyminaandwils.R.layout.activity_play;

public class PlayActivity extends AppCompatActivity {


    private static final String TAG = ":PlayActivityLOG:";


    ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(300,300);
    private MazePanel mp;
    MazeController controller;
    ManualDriver manDriver;


    /**
     * Links the xml file activity_play to this file.
     * Returns toasts and logcat outputs for each selection that is made by the user
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_play);
        mp = (MazePanel) findViewById(R.id.MPa);
        DataHolder app = DataHolder.getInstance();
        app.setPanel(mp);
        controller = app.getController();
        manDriver = new ManualDriver(controller);
        Log.v(TAG, "Initializing PlayActivity");



        Random rand = new Random();
        int  n = rand.nextInt(1) + 1;
        Log.v(TAG, "msui is: " + app.getMusicTheme());
        if (Objects.equals(app.getMusicTheme(), "Christmas")) {
            Log.v(TAG, "Christmas music playing");
            MediaPlayer player= MediaPlayer.create(PlayActivity.this,R.raw.silverbells);
            player.start();
        }
        else if (Objects.equals(app.getMusicTheme(), "Halloween")) {
            Log.v(TAG, "Halloween music playing");
            MediaPlayer player= MediaPlayer.create(PlayActivity.this,R.raw.monstermash);
            player.start();
        }




        ToggleButton toggleWalls = (ToggleButton) findViewById(R.id.toggleButton2);
        toggleWalls.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    controller.keyDown(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController.UserInput.ToggleLocalMap, 1);
                    Log.v(TAG, "Minimap walls toggled on");
                } else {
                    controller.keyDown(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController.UserInput.ToggleLocalMap, 1);
                    Log.v(TAG, "Minimap walls toggled off");
                }
            }
        });
        ToggleButton toggleMaze = (ToggleButton) findViewById(R.id.toggleButton3);
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
        ToggleButton toggleSolution = (ToggleButton) findViewById(R.id.toggleButton4);
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
        controller.switchToPlayingScreen();
    }



    /**
     * Tests to make sure left button was pressed
     * @param view
     */
    public void pressLeft(View view) {
        Log.v(TAG, "Left button pressed");
        manDriver.turnLeft();
    }

    /**
     * Tests to make sure right button was pressed
     * @param view
     */
    public void pressRight(View view) {
        Log.v(TAG, "Right button pressed");
        manDriver.turnRight();    }

    /**
     * Tests to make sure the up button was pressed
     * @param view
     */
    public void pressUp(View view) {
        Log.v(TAG, "Up button pressed" + view);
        manDriver.moveForward();
        new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (controller.isOutside(controller.px, controller.py)) {
                    Intent intent = new Intent(getApplicationContext(), WinActivity.class);
                    startActivity(intent);
                }
            }
        }).start();

    }

    /**
     * Tests to make sure the down button was pressed
     * @param view
     */
    public void pressDown(View view) {
        Log.v(TAG, "Down button pressed" + view);
        manDriver.moveBackward();
        new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (controller.isOutside(controller.px, controller.py)) {
                    Intent intent = new Intent(getApplicationContext(), WinActivity.class);
                    startActivity(intent);
                }
            }
        }).start();
    }
}
