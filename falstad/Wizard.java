package edu.wm.cs.cs301.amazebyminaandwils.falstad;


        import edu.wm.cs.cs301.amazebyminaandwils.generation.Cells;
        import edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction;
        import edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn;
        import edu.wm.cs.cs301.amazebyminaandwils.generation.Distance;
        import edu.wm.cs.cs301.amazebyminaandwils.generation.Order;
        import edu.wm.cs.cs301.amazebyminaandwils.generation.CardinalDirection;

public class Wizard implements RobotDriver{

//	BasicRobot rob;
//	MazeController maze;

    protected int[][] board;
    protected BasicRobot rob;
    protected Distance distance;
    protected int pathLength;
    protected MazeController maze;
    protected CardinalDirection cd;
    protected Cells cell;
    protected int dist;

    // Simple constructor for no bounds, sets everything to null except maze
    public Wizard() {
        board = null;
        rob = null;
        distance = null;
        maze = new MazeController();
        cell = maze.getMazeConfiguration().getMazecells();
    }

    // copy paste of other constructor except with a builder
    public Wizard(Order.Builder builder) {
        board = null;
        rob = null;
        distance = null;
        maze = new MazeController(builder);
        cell = maze.getMazeConfiguration().getMazecells();
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

    private Cells getCells() {
        if (null == cell) {
            cell = maze.getMazeConfiguration().getMazecells();
        }
        return cell;
    }


    @Override
    public boolean drive2Exit() throws Exception {
        cell = getCells();
        int[] dir;
        int[] result = new int[2] ;
        int dnext = distance.getDistance(maze.getCurrentPosition()[0], maze.getCurrentPosition()[1]) ;
        while (!rob.isAtExit()) {
            // checks which neighbor is closer to the exit
            for (CardinalDirection cd: CardinalDirection.values()) {
                if (cell.hasWall(maze.getCurrentPosition()[0], maze.getCurrentPosition()[1], cd)) {

                    continue;
                }
                dir = cd.getDirection();
                int dn = distance.getDistance(maze.getCurrentPosition()[0]+dir[0], maze.getCurrentPosition()[1]+dir[1]);
                if (dn < dnext) {
                    // update neighbor position with min distance
                    result[0] = maze.getCurrentPosition()[0]+dir[0] ;
                    result[1] = maze.getCurrentPosition()[1]+dir[1] ;
                    dnext = dn ;
                }
            }

            if (result[0] == (maze.getCurrentPosition()[0] - 1) && rob.distanceToObstacle(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.LEFT) > 0) {
                rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.LEFT);
                while ((dist = rob.distanceToObstacle(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.FORWARD)) > 0) {
                    rob.move(1,  false);
                    dist -= 1;
                }
            }

            else if (result[0] == (maze.getCurrentPosition()[0] + 1) && rob.distanceToObstacle(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.RIGHT) > 0) {
                rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.RIGHT);
                while ((dist = rob.distanceToObstacle(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.FORWARD)) > 0) {
                    rob.move(1,  false);
                    dist -= 1;
                }
            }

            else if (result[1] == (maze.getCurrentPosition()[1] - 1) && rob.distanceToObstacle(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.BACKWARD) > 0) {
                rob.rotate(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn.AROUND);
                while ((dist = rob.distanceToObstacle(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.FORWARD)) > 0) {
                    rob.move(1,  false);
                    dist -= 1;
                }
            }

            else if (result[1] == (maze.getCurrentPosition()[1] + 1) && rob.distanceToObstacle(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.FORWARD) > 0) {
                while ((dist = rob.distanceToObstacle(edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction.FORWARD)) > 0) {
                    rob.move(1,  false);
                    dist -= 1;
                }
            }


            // if rob has stopped the loss screen is generated
            if (rob.hasStopped()) {
                maze.switchToLossScreen();
                break;
            }
        }
        return false;
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
