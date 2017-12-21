package edu.wm.cs.cs301.amazebyminaandwils.falstad;




import android.util.Log;

import edu.wm.cs.cs301.amazebyminaandwils.generation.Cells;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn;
import edu.wm.cs.cs301.amazebyminaandwils.generation.Distance;
import edu.wm.cs.cs301.amazebyminaandwils.generation.Order;
import edu.wm.cs.cs301.amazebyminaandwils.generation.SingleRandom;
import edu.wm.cs.cs301.amazebyminaandwils.generation.CardinalDirection;
import edu.wm.cs.cs301.amazebyminaandwils.ui.DataHolder;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Pledge implements RobotDriver{

//	BasicRobot rob;
//	MazeController maze;

    protected int[][] board;
    protected BasicRobot rob;
    protected Distance distance;
    protected int pathLength;
    protected MazeController maze;
    protected CardinalDirection cd;
    protected SingleRandom random;
    protected Cells cell;
    protected int dist;

    // Simple constructor for no bounds, sets everything to null except maze
    public Pledge() {
        Log.v(TAG, "Pledge constructor withOUT builder made");
        board = null;
        rob = null;
        distance = null;
        maze = new MazeController();
        random = SingleRandom.getRandom();
    }

    // copy paste of other constructor except with a builder
    public Pledge(Order.Builder builder) {
        Log.v(TAG, "Pledge constructor with builder made");
        board = null;
        rob = null;
        distance = null;
        maze = new MazeController(builder);
        random = SingleRandom.getRandom();
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
        Log.v(TAG,"drive2Exit() in Pledge is reached!");
        // 0 is left, 1 is right
        new Thread(new Runnable() {
            public void run() {
                DataHolder app = DataHolder.getInstance();
                int h = random.nextIntWithinInterval(0, 2);
                int count = 0;
                while (!rob.isAtExit()) {
                    if (app.getMazeToggle()) {
                        if (rob.hasStopped()) {
                            Log.v(TAG, "Pledge hasstopped but not at exit reached");
                            maze.switchToLossScreen();
                            break;
                        }
                        if (count != 0) {
                            if (h == 0) {
                                if (rob.distanceToObstacle(Direction.LEFT) > 0) {
                                    rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.LEFT);
                                    count--;
                                    rob.move(1, false);
                                } else if ((dist = rob.distanceToObstacle(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.FORWARD)) > 0) {
                                    while (dist > 0) {
                                        rob.move(1, false);
                                        dist -= 1;
                                        if (rob.distanceToObstacle(Direction.LEFT) > 0) {
                                            rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.LEFT);
                                            count--;
                                            rob.move(1, false);
                                            break;
                                        }
                                    }
                                } else {
                                    rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.RIGHT);
                                    count++;
                                }
                            } else {
                                // else if h = 1...
                                if (rob.distanceToObstacle(Direction.RIGHT) > 0) {
                                    rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.RIGHT);
                                    count++;
                                    rob.move(1, false);
                                } else if ((dist = rob.distanceToObstacle(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.FORWARD)) > 0) {
                                    while (dist > 0) {
                                        rob.move(1, false);
                                        dist -= 1;
                                        if (rob.distanceToObstacle(Direction.RIGHT) > 0) {
                                            rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.RIGHT);
                                            count++;
                                            rob.move(1, false);
                                            break;
                                        }
                                    }
                                } else {
                                    rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.LEFT);
                                    count--;
                                }
                            }
                        } else {
                            if (h == 0) {
                                if ((dist = rob.distanceToObstacle(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.FORWARD)) > 0) {
                                    while (dist > 0) {
                                        rob.move(1, false);
                                        dist -= 1;
                                    }
                                } else if (rob.distanceToObstacle(Direction.LEFT) > 0) {
                                    rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.LEFT);
                                    count--;
                                    rob.move(1, false);
                                } else {
                                    rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.RIGHT);
                                    count++;
                                }
                            } else {
                                if ((dist = rob.distanceToObstacle(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.FORWARD)) > 0) {
                                    while (dist > 0) {
                                        rob.move(1, false);
                                        dist -= 1;
                                    }
                                } else if (rob.distanceToObstacle(Direction.RIGHT) > 0) {
                                    rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.RIGHT);
                                    count++;
                                    rob.move(1, false);
                                } else {
                                    rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.LEFT);
                                    count--;
                                }
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
                    } else if (rob.canSeeExit(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.LEFT)) {
                        rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.LEFT);
                        rob.move(1, false);
                    } else {
                        rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.RIGHT);
                        rob.move(1, false);
                    }
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
