package com.krisztianszabo.elementarycellularautomaton.activities.simulation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.BitSet;

public class SimulationView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private int lineLength;
    private int pixelSize;
    private int numLines;
    private int currentLine = 0;
    private Rect[] rowPixels;
    private Bitmap currentView;
    private int viewWidth;
    private int viewHeight;
    private Rect viewRect;
    private Rect src;
    private Rect dst;
    private Canvas canvas;
    private Paint background = new Paint();
    private Paint foreground = new Paint();

    public SimulationView(Context context, int width, int height, int lineLength) {
        super(context);

        this.viewWidth = width;
        this.viewHeight = height;
        this.lineLength = lineLength;
        this.pixelSize = this.viewWidth / lineLength;
        this.numLines = this.viewHeight / this.pixelSize;
        this.src = new Rect(0, pixelSize, this.viewWidth, this.viewHeight);
        this.dst = new Rect(0, 0, this.viewWidth, this.viewHeight - pixelSize);
        this.viewRect = new Rect(0, 0, this.viewWidth, this.viewHeight);
        this.currentView = Bitmap.createBitmap(this.viewWidth, this.viewHeight, Bitmap.Config.ARGB_8888);

        this.rowPixels = new Rect[lineLength];
        for (int i = 0; i < lineLength; i++) {
            rowPixels[i] = new Rect(i * pixelSize, 0, pixelSize, pixelSize);
        }

        this.background.setARGB(255, 255, 255, 255);
        this.foreground.setARGB(255, 0, 0, 0);
        this.foreground.setAntiAlias(false);
        this.canvas = new Canvas(this.currentView);
        this.canvas.drawPaint(background);
    }

    private void drawCurrentView() {
        Canvas canvas = holder.lockCanvas();
        canvas.drawBitmap(currentView, null, this.viewRect, null);
        holder.unlockCanvasAndPost(canvas);
    }

    public void addLine(BitSet data) {

        if (currentLine < numLines - 1) {
            // Haven't reached the bottom yet, so we only need to add a new line
            for (int i = 0; i < lineLength; i++) {
                if (data.get(i)) {
                    this.canvas.drawRect(rowPixels[i], foreground);
                }
                rowPixels[i].top += pixelSize;
            }
            currentLine++;
        } else {
            // We're at the bottom, so we need to shift the existing image one row up
            //Bitmap oldImage = this.currentView.copy(Bitmap.Config.ARGB_8888, false);

            this.canvas.drawBitmap(this.currentView, src, dst, null);
            for (int i = 0; i < lineLength; i++) {
                this.canvas.drawRect(rowPixels[i], data.get(i) ? foreground : background);
            }
        }

        drawCurrentView();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
        this.holder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.holder = null;
    }
}
