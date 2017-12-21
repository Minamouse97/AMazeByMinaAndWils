package edu.wm.cs.cs301.amazebyminaandwils.falstad;

import edu.wm.cs.cs301.amazebyminaandwils.generation.Distance;

//import java.awt.event.KeyListener;

import edu.wm.cs.cs301.amazebyminaandwils.falstad.Constants.StateGUI;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController.UserInput;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn;

public class ManualDriver implements RobotDriver{

    BasicRobot rob;

//    private KeyListener key;
    MazeController maze;

    public ManualDriver() {
        rob = new BasicRobot(maze);
        maze = new MazeController();
    }

    public ManualDriver(MazeController controller) {
        rob = new BasicRobot(controller);
        maze = controller;
    }

    /**
     * Assigns a robot platform to the driver.
     * The driver uses a robot to perform, this method provides it with this necessary information.
     * @param r robot to operate
     */
    public void setRobot(Robot r) {
        rob =(BasicRobot) r;
    }

    /**
     * Rotates the robot left
     */
    public void turnLeft() {
        new Thread(new Runnable() {
            public void run() {
                rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.RIGHT);
            }
        }).start();
    }

    /**
     * Rotates the robot right
     */
    public void turnRight() {
        new Thread(new Runnable() {
            public void run() {
                rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.LEFT);
            }
        }).start();
    }

    /**
     * Moves robot forward
     */
    public void moveForward() {
        new Thread(new Runnable() {
            public void run() {
                rob.move(1, true);
            }
        }).start();
    }

    /**
     * Moves robot backward
     */
    public void moveBackward() {
        new Thread(new Runnable() {
            public void run() {
                rob.moveBack(1, true);
            }
        }).start();
    }



    /**
     * Provides the robot driver with information on the dimensions of the 2D maze
     * measured in the number of cells in each direction.
     * @param width of the maze
     * @param height of the maze
     * @precondition 0 <= width, 0 <= height of the maze.
     */
    public void setDimensions(int width, int height) {
        width = maze.mazeConfig.getWidth();

        height = maze.mazeConfig.getHeight();
    }

    /**
     * Provides the robot driver with information on the distance to the exit.
     * Only some drivers such as the wizard rely on this information to find the exit.
     * @param distance gives the length of path from current position to the exit.
     * @precondition null != distance, a full functional distance object for the current maze.
     */
    public void setDistance(Distance distance) {

    }

    /**
     * Drives the robot towards the exit given it exists and given the robot's energy supply lasts long enough.
     * @return true if driver successfully reaches the exit, false otherwise
     * throws exception if robot stopped due to some problem, e.g. lack of energy
     */
    public boolean drive2Exit() throws Exception {
        return false;
    }

    /**
     * Returns the total energy consumption of the journey, i.e.,
     * the difference between the robot's initial energy level at
     * the starting position and its energy level at the exit position.
     * This is used as a measure of efficiency for a robot driver.
     */
    public float getEnergyConsumption() {
        return 3000 - rob.energyLevel;
    }

    /**
     * Returns the total length of the journey in number of cells traversed.
     * Being at the initial position counts as 0.
     * This is used as a measure of efficiency for a robot driver.
     */
    public int getPathLength() {
        return rob.odometer;
    }

}
