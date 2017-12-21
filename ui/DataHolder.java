package edu.wm.cs.cs301.amazebyminaandwils.ui;


import android.app.Application;
import android.util.Log;

import edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.MazePanel;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.RobotDriver;
import edu.wm.cs.cs301.amazebyminaandwils.generation.MazeBuilder;
import edu.wm.cs.cs301.amazebyminaandwils.generation.MazeConfiguration;


public class DataHolder extends Application{
    private static final String TAG = ":DataHolderLOG:";
    public static final DataHolder ourInstance = new DataHolder();
    public static DataHolder getInstance() {
        return ourInstance;
    }
    private DataHolder() { }



    private MazeController controller;
    public MazeController getController() {
        Log.v(TAG, "\"getController\" called");
        return controller; }
    public void setController(MazeController controller) {
        Log.v(TAG, "\"setController\" called");
        this.controller = controller;}


    private MazePanel mp;
    public MazePanel getPanel() {
        Log.v(TAG, "\"getPanel\" called");
        return mp;}
    public void setPanel(MazePanel mp) {
        Log.v(TAG, "\"setPanel\" called");
        this.mp = mp;}

    private MazeConfiguration mazeConfig;
    public MazeConfiguration getConfig() {
        Log.v(TAG, "\"getConfig\" called");
        return mazeConfig;}
    public void setConfig(MazeConfiguration mazeConfig) {
        Log.v(TAG, "\"setConfig\" called");
        this.mazeConfig = mazeConfig;}


    private MazeBuilder mazeBuilder;
    public MazeBuilder getBuilder() {
        Log.v(TAG, "\"getBuilder\" called");
        return mazeBuilder;}
    public void setBuilder(MazeBuilder mazeBuilder) {
        Log.v(TAG, "\"setBuilder\" called");
        this.mazeBuilder = mazeBuilder;}


    private String selDriver;
    public String getDriver() {
        Log.v(TAG, "\"getDriver\" called");
        return selDriver;}
    public void setDriver(String selDriver) {
        Log.v(TAG, "\"setDriver\" called");
        this.selDriver = selDriver;
    }

    private RobotDriver robDriver;
    public RobotDriver getRobDriver() {
        Log.v(TAG, "\"getRobDriver\" called");
        return robDriver;}
    public void setRobDriver(RobotDriver robDriver) {
        Log.v(TAG, "\"setDriver\" called");
        this.robDriver = robDriver;
    }

    private String selAlgorithm;
    public String getAlgorithm() {
        Log.v(TAG, "\"getAlgorithm\" called");
        return selAlgorithm;}
    public void setAlgorithm(String selAlgorithm) {
        Log.v(TAG, "\"setAlgorithm\" called");
        this.selAlgorithm = selAlgorithm;}

    private int progressBarValue;
    public int getBarValue() {
        Log.v(TAG, "\"getBarValue\" called");
        return progressBarValue;}
    public void setBarValue(int progressBarValue) {
        Log.v(TAG, "\"setBarValue\" called");
        this.progressBarValue = progressBarValue;}

    private String gameTheme;
    public String getGameTheme() {
        Log.v(TAG, "\"getGameTheme\" called");
        return gameTheme;}
    public void setGameTheme(String gameTheme) {
        Log.v(TAG, "\"setGameTheme\" called");
        this.gameTheme = gameTheme;}

    private String musicTheme;
    public String getMusicTheme() {
        Log.v(TAG, "\"getMusicTheme\" called");
        return musicTheme;}
    public void setMusicTheme(String musicTheme) {
        Log.v(TAG, "\"setMusicTheme\" called");
        this.musicTheme = musicTheme;}

    private boolean mazeToggle;
    public boolean getMazeToggle() {
        Log.v(TAG, "\"getMazeToggle\" called");
        return mazeToggle;}
    public void setMazeToggle(boolean mazeToggle) {
        Log.v(TAG, "\"setMazeToggel\" called");
        this.mazeToggle = mazeToggle;}

    private boolean hasWon;
    public boolean getHasWon() {
        Log.v(TAG, "\"getHasWon\" called");
        return hasWon;}
    public void setHasWon(boolean hasWon) {
        Log.v(TAG, "\"setHasWon\" called");
        this.hasWon = hasWon;}

    private String revOrExp;
    public String getRevOrExp() {
        Log.v(TAG, "\"getDriver\" called");
        return revOrExp;}
    public void setRevOrExp(String revOrExp) {
        Log.v(TAG, "\"setDriver\" called");
        this.revOrExp = revOrExp;
    }

    private int partiters;
    public int getPartiters() {
        Log.v(TAG, "\"getPartiters\" called");
        return partiters;}
    public void setPartiters(int partiters) {
        Log.v(TAG, "\"setPartiters\" called");
        this.partiters = partiters;}

    private boolean done;
    public boolean getDone() {
        Log.v(TAG, "\"getDone\" called");
        return done;}
    public void setDone(boolean done) {
        Log.v(TAG, "\"setDone\" called");
        this.done = done;}
}
