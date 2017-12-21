package edu.wm.cs.cs301.amazebyminaandwils.falstad;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Array;


import edu.wm.cs.cs301.amazebyminaandwils.ui.DataHolder;



/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 *
 * @author pk
 *
 */
public class MazePanel extends View {

    private static final String TAG = ":MazePanelLOG:";

    /* Panel operates a double buffer see
     * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
     * for details
     */
    // bufferImage can only be initialized if the container is displayable,
    // uses a delayed initialization and relies on client class to call initBufferImage()
    // before first use
//    private Image bufferImage;
//    public Color c;
//    private Graphics2D graphics; // obtained from bufferImage,
    // graphics is stored to allow clients to draw on same graphics object repeatedly
    // has benefits if color settings should be remembered for subsequent drawing operations

    /**
     * Constructor. Object is not focusable.
     */


    private Paint p ;
    private Bitmap b ;
    private Canvas canv ;
    public int col = 0;
    private Matrix m ;

//    private static MazePanel mp;
//    public static MazePanel getPanel() {return mp;}
//    public static MazePanel setData(MazePanel mp) {MazePanel.mp = mp;}

    private void init() {
        // initialize fields
        p = new Paint();
        b = Bitmap.createBitmap(600, 600, Bitmap.Config.ARGB_8888);
        canv = new Canvas(b);
        m = new Matrix();
//        DataHolder app = DataHolder.getInstance();
//        app.setPanel(this);

    }

    public MazePanel(Context context) {
        super(context);
        Log.v(TAG, "Constructor 1 called");
        init();
    }

    public MazePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.v(TAG, "Constructor 2 called");
        init();
    }

    public MazePanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.v(TAG, "Constructor 3 called");
        init();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canv);
        canvas.drawBitmap(b, 0, 0, p);

//        canv.drawBitmap(b,m,p);
    }

    public void drawTestFigure() {
//        /*canv.*/drawLine(200, 100, 100, 20/*, p*/);
        canv.drawLine(0, 0, 600, 600, p);
        canv.drawLine(0, 0, 700, 300, p);
        canv.drawLine(0, 0, 300, 700, p);
        canv.drawLine(600, 0, 0, 600, p);
        drawLine(100, 0, 300, 400);
        int [] x = {100,0,400,400};
        int [] y = {100,300,400,0};
        int z = 10;
        setColor(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazePanel.ColorEnum.BLUE);
        fillPolygon(x, y, z);
        setColor(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazePanel.ColorEnum.MAGENTA);
        fillRect(200, 300, 10,300);
        setColor(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazePanel.ColorEnum.RED);
        fillOval(100, 100, 300, 200);
    }


    public void update() {
        invalidate();
    }


    public enum ColorEnum { WHITE, LIGHTGRAY, GRAY, DARKGRAY, BLACK, RED, PINK, ORANGE, YELLOW, GREEN, MAGENTA, CYAN, BLUE };

    /**
     * Takes in a color enum and changes it to a color of class Color
     * @param colorColor
     * @return
     */
    public int color(ColorEnum colorColor) {
        int colorOne = 0;
        switch (colorColor) {
            case WHITE:
                colorOne = Color.WHITE;
                break;
            case LIGHTGRAY:
                colorOne = Color.LTGRAY;
                break;
            case GRAY:
                colorOne = Color.GRAY;
                break;
            case DARKGRAY:
                colorOne = Color.DKGRAY;
                break;
            case BLACK:
                colorOne = Color.BLACK;
                break;
            case RED:
                colorOne = Color.RED;
                break;
            case PINK:
                colorOne = Color.rgb(255,192,203);
                break;
            case ORANGE:
                colorOne = Color.rgb(255,165,0);
                break;
            case YELLOW:
                colorOne = Color.YELLOW;
                break;
            case GREEN:
                colorOne = Color.GREEN;
                break;
            case MAGENTA:
                colorOne = Color.MAGENTA;
                break;
            case CYAN:
                colorOne = Color.CYAN;
                break;
            case BLUE:
                colorOne = Color.BLUE;
                break;
        }
        col = colorOne;
        return col;
    }

    /**
     * Sets the color using the color() method
     * @param colorColor
     */
    public void setColor(ColorEnum colorColor) {
        p.setColor(color(colorColor));
    }


    public void setColorSpecial(int color) {
        p.setColor(color);
    }
    /**
     * Uses graphics object and calls on fillPolygon method
     * @param xPoints
     * @param yPoints
     * @param nPoints
     */
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        Path path = new Path();
//        getBufferGraphics().fillPolygon(xPoints, yPoints, nPoints );
        p.setStyle(Paint.Style.FILL);
        path.moveTo(xPoints[0], yPoints[0]);
        for(int i=1; i< Array.getLength(xPoints); i++){
            path.lineTo(xPoints[i], yPoints[i]);
        }
        path.lineTo(xPoints[0], yPoints[0]);
        canv.drawPath(path, p);
    }


    /**
     * returns the color
     * @return
     */
    public Object getColor() {
        return col;
    }



    /**
     * Uses graphics object to call fillRect method
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillRect(int x, int y, int width, int height) {
        canv.drawRect(x, y, x+width, y+height, p);
//        getBufferGraphics().fillRect(x, y, width, height);
    }

    /**
     * Uses graphics object to call drawLine method
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */

    public void drawLine(int x1, int y1, int x2, int y2) {
//        float x1 =  x1;
        canv.drawLine(x1, y1, x2, y2, p);

//        getBufferGraphics().drawLine(x1, y1, x2, y2);
    }


    /**
     * Uses graphics object to call fillOval method
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillOval(int x, int y, int width, int height) {
//        getBufferGraphics().fillOval(x, y, width, height);
    canv.drawOval(x, y, x+width, y+height, p);
    }



    /**
     * Creates a new Color using rgb and returns that color
     * @param r
     * @param g
     * @param b
     * @return
     */
    public /*Object*/int setColorUsingInts(int r, int g, int b) {
//        col = new Color(r, g, b);
        col = Color.rgb(r,g,b);
        return col;
    }

    /**
     * Uses graphics object to call the getFontMetrics method
     * @return
     */
    public Paint.FontMetrics getFontMetrics() {
        return p.getFontMetrics();
    }

    /**
     * Uses graphics object to call the setFont method
     */
    public void setFont(Typeface typeface) {
//        getBufferGraphics().setFont(font);
        p.setTypeface(typeface);
    }

    /**
     * Creates new Color using one integer and returns that color
     * @param rgb
     * @return
     */
    public /*Object*/int setColorUsingInt(int rgb) {
//        col = Color.rgb;
        Log.v(TAG, "rgb input is: " + rgb);
        col = rgb;
//        col = Color.toArgb(rgb);
//        return col;

        return col;
    }

    /**
     * Uses graphics object to call the drawString method
     * @param str
     * @param x
     * @param y
     */
    public void drawString(String str, int x, int y) {
//        getBufferGraphics().drawString(str, x, y);
        canv.drawText(str,x,y,p);
    }

    /**
     * Uses color to getRGB
     * @return
     */
    public int getRGB() {
//        return c.getRGB();
        return p.getColor();
    }

//    /**
//     * Sets the color using graphics object
//     * @param color
//     */
//    public void setColorWithObject(int color) {
//
////        getBufferGraphics().setColor((Color) color);
//
//    }
}

