package com.example.flappybirdoyunu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Pipe extends BaseObject{

    public static int speed;

    public Pipe(float x, float y, int width, int height){
        super(x,y,width,height);
        speed=10*Constants.SCREEN_WIDTH/1080;
    }

    //
    //
    @Override
    public Rect getRect() {
        return new Rect((int)this.x+10, (int)this.y, (int)this.x+this.width-10, (int)this.y+this.height);
    }
    //
    //

    public void draw(Canvas canvas){
        this.x -= speed;
        canvas.drawBitmap(this.bm, this.x,this.y, null);
    }

    public void randomY(){
        Random r = new Random();
        this.y = r.nextInt((0 + this.height/4) + 1) - this.height/4;
    }

    @Override
    public void setBm(Bitmap bm) {
        this.bm = Bitmap.createScaledBitmap(bm, width, height, true);
    }
}
