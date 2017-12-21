package edu.wm.cs.cs301.amazebyminaandwils.falstad;




import android.content.Intent;
import android.util.Log;

import edu.wm.cs.cs301.amazebyminaandwils.generation.Cells;

import java.util.concurrent.TimeUnit;

import edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn;
import edu.wm.cs.cs301.amazebyminaandwils.generation.Distance;
import edu.wm.cs.cs301.amazebyminaandwils.generation.Order;
import edu.wm.cs.cs301.amazebyminaandwils.generation.CardinalDirection;
import edu.wm.cs.cs301.amazebyminaandwils.ui.AMazeActivity;
import edu.wm.cs.cs301.amazebyminaandwils.ui.AutoPlayActivity;
import edu.wm.cs.cs301.amazebyminaandwils.ui.DataHolder;
import edu.wm.cs.cs301.amazebyminaandwils.ui.PlayActivity;
import edu.wm.cs.cs301.amazebyminaandwils.ui.WinActivity;

public class WallFollower implements RobotDriver{

//	BasicRobot rob;
//	MazeController maze;
    private static final String TAG = ":WallFollowerLOG:";
    protected int[][] board;
    protected BasicRobot rob;
    protected Distance distance;
    protected int pathLength;
    protected MazeController maze;
    protected CardinalDirection cd;
    protected Cells cell;
    protected int dist;


    // Simple constructor for no bounds, sets everything to null except maze
    public WallFollower() {
        board = null;
//		rob = null;
        distance = null;
        maze = new MazeController();
        rob = new BasicRobot(maze);
        cell = maze.mazeConfig.getMazecells();
        dist = 0;
    }

    // copy paste of other constructor except with a builder
//	public WallFollower(Order.Builder builder) {
//		board = null;
//		rob = null;
//		distance = null;
//		maze = new MazeController(builder);
//		rob = new BasicRobot(maze);
//		cell = maze.mazeConfig.getMazecells();
//		dist = 0;
//	}

    // copy paste of other constructor except with a builder
    public WallFollower(MazeController controller) {
        board = null;
        rob = null;
        distance = null;
        maze = controller;
//        rob = new BasicRobot(controller);
        cell = maze.mazeConfig.getMazecells();
        dist = 0;
    }


    @Override
    public void setRobot(Robot r) {
        rob =(BasicRobot) r;
    }

    @Override
    public void setDimensions(int width, int height) {
        board = new int[width][height];
    }

    @Override
    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    @Override
    public boolean drive2Exit() throws Exception {
        Log.v(TAG, "driver2Exit REALLY reached");
        new Thread(new Runnable() {
            public void run() {
                while (!rob.isAtExit()) {
                    DataHolder app = DataHolder.getInstance();
                    if (app.getMazeToggle()) {
                        Log.v(TAG, "inside \"driver2Exit\" while loop");

                        if (rob.hasStopped()) {
                            maze.switchToLossScreen();
                            break;
                        }

                        if (rob.distanceToObstacle(Direction.LEFT) > 0) {
                            rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.LEFT);
                            rob.move(1, false);
                        } else {
                            if (rob.distanceToObstacle(Direction.LEFT) == 0 && rob.distanceToObstacle(Direction.FORWARD) > 0) {
                                rob.move(1, false);
                            } else if (rob.distanceToObstacle(Direction.LEFT) == 0 && rob.distanceToObstacle(Direction.FORWARD) == 0) {
                                rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.RIGHT);
                            }

                        }
                    }
                    else {
                        break;
                    }
                }

                if (rob.isAtExit()) {
                    if (rob.canSeeExit(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.FORWARD)) {
                        rob.move(1, false);
//                AutoPlayActivity.funMethod();
                    } else if (rob.canSeeExit(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.LEFT)) {
                        rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.LEFT);
                        rob.move(1, false);
                    } else {
                        rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.RIGHT);
                        rob.move(1, false);
                    }
                    DataHolder app = DataHolder.getInstance();
                    app.setHasWon(true);
                }
            }
        }).start();
        return true;
    }

    @Override
    public float getEnergyConsumption() {
        return 3000 - rob.energyLevel;
    }

    @Override
    public int getPathLength() {
        return rob.odometer;
    }

}
