package edu.wm.cs.cs301.amazebyminaandwils.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.ProgressDialog;
import android.os.Handler;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import edu.wm.cs.cs301.amazebyminaandwils.R;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.MazePanel;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.Pledge;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.RobotDriver;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.WallFollower;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.Wizard;
import edu.wm.cs.cs301.amazebyminaandwils.generation.BSPBuilder;
import edu.wm.cs.cs301.amazebyminaandwils.generation.MazeFactory;
import edu.wm.cs.cs301.amazebyminaandwils.generation.Order;

import static edu.wm.cs.cs301.amazebyminaandwils.R.layout.activity_play;

public class GeneratingActivity extends AppCompatActivity {

    Button btnStartProgress;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();

    private long fileSize = 0;

    private static final String TAG = "GenerateActivityLOG";
    private MazeController controller;


    /**
     * Links the xml file activity_generating to this file.
     * Outputs a log string and gets the driver selected in previous screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);
        Log.v(TAG, "Initializing GeneratingActivity");
        init();
    }


    private void init() {
        Order.Builder itemPicked;

        DataHolder app = DataHolder.getInstance();
        String selAlgorithm = app.getAlgorithm();
        String selDriver = app.getDriver();

        switch (selAlgorithm) {
            case "DFS":
                itemPicked = Order.Builder.DFS;
                break;
            case "Eller":
                itemPicked = Order.Builder.Eller;
                break;
            case "Prim":
                itemPicked = Order.Builder.Prim;
                break;
            default:
                itemPicked = Order.Builder.DFS;
                break;
        }

        controller = new MazeController(itemPicked);

        Log.v(TAG, "selDriver is: " + selDriver);

        if (Objects.equals(selDriver, "Wizard")) {
            RobotDriver driver = new Wizard();
            Log.v(TAG, "DRIVER IS: " + driver);
            controller.setDriver(driver);
            Log.v(TAG, "driver is being set to controller");
            app.setRobDriver(driver);
        } else if (Objects.equals(selDriver, "WallFollower")) {
            RobotDriver driver = new WallFollower(controller);
            Log.v(TAG, "DRIVER IS: " + driver);
            controller.setDriver(driver);
            Log.v(TAG, "driver is being set to controller");
            app.setRobDriver(driver);
        } else if (Objects.equals(selDriver, "Pledge")) {
            RobotDriver driver = new Pledge();
            Log.v(TAG, "DRIVER IS: " + driver);
            controller.setDriver(driver);
            Log.v(TAG, "driver is being set to controller");
            app.setRobDriver(driver);
        } else {
        }


        controller.setSkillLevel(app.getBarValue());

        controller.init();
        Log.v(TAG, "controller.switchToGeneratingScreen is being reached!!!");
        controller.switchToGeneratingScreen();
        app.setController(controller);

        method();

    }


    public void method() {
        ProgressBar prog = (ProgressBar) findViewById(R.id.progressBar15);
        progressBar = new ProgressDialog(prog.getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage("File downloading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        Log.v(TAG, "Generation button pressed, generating maze");
        progressBar.show();

        //reset progress bar status
        progressBarStatus = 0;

        //reset filesize
        fileSize = 0;

        new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < 100) {

                    // process some tasks
                    progressBarStatus = doSomeTasks();

                    // your computer is too fast, sleep 1 second
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }

                // ok, file is downloaded,
                if (progressBarStatus >= 100) {

                    // sleep 2 seconds, so that you can see the 100%
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // close the progress bar dialog
                    progressBar.dismiss();
                    pressProceed(null);
                }

            }

        }).start();

    }


    /**
     * If Manual Driver was selected, goes to PlayActivity.
     * If any other driver was selected, goes to AutoPlayActivity.
     *
     * @param view
     */
    public void pressProceed(View view) {

        DataHolder app = DataHolder.getInstance();
        ;
        String selDriver = app.getDriver();

        if ("ManualDriver".equals(selDriver)) {
            Intent intent = new Intent(this, PlayActivity.class);
            Log.v(TAG, "Generation finished, switching to PlayActivity");
            startActivity(intent);

        } else {
            Intent intent = new Intent(this, AutoPlayActivity.class);
            Log.v(TAG, "Generation finished, switching to AutoPlayActivity");
            startActivity(intent);
        }
    }


    /**
     * simulates a file being downloaded, but nothing is really being downloaded...
     *
     * @return
     */
    // file download simulator... a really simple
    public int doSomeTasks() {

        while (fileSize <= 1000000) {

            fileSize++;

            if (fileSize == 100000) {
                return 10;
            } else if (fileSize == 200000) {
                return 20;
            } else if (fileSize == 300000) {
                return 30;
            }
            // ...add your own

        }

        return 100;

    }

}
