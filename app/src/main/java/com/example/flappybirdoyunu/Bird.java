package com.example.flappybirdoyunu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

public class Bird extends BaseObject{
    private ArrayList<Bitmap> arrBms = new ArrayList<>();
    private int sayac, vFlap, idSuandakiBitmap;
    private float drop;

    public Bird(){
        this.sayac = 0;
        this.vFlap = 5;
        this.idSuandakiBitmap = 0;
        this.drop = 0;
    }

    //
    //
    @Override
    public Rect getRect() {
        return new Rect((int)this.x+20, (int)this.y+20, (int)this.x+this.width-10, (int)this.y+this.height-10);
    }
    //
    //

    public void draw(Canvas kanvas){
        drop();
        kanvas.drawBitmap(this.getBm(), this.x, this.y, null);
    }

    private void drop() {
        this.drop += 0.7;
        this.y+=this.drop;
    }

    public ArrayList<Bitmap> getArrBms() {
        return arrBms;
    }

    public void setArrBms(ArrayList<Bitmap> arrBms) {
        this.arrBms = arrBms;
        for (int i = 0; i < arrBms.size(); i++) {
            this.arrBms.set(i, Bitmap.createScaledBitmap(this.arrBms.get(i), this.width, this.height, true));
        }
    }


        @Override
        public Bitmap getBm() {
            sayac++;
            if(this.sayac==this.vFlap){
                for(int i=0;i<arrBms.size();i++){
                    if(i == arrBms.size()-1){
                        idSuandakiBitmap = 0;
                        break;
                    }else if(idSuandakiBitmap == i){
                        idSuandakiBitmap = i+1;
                        break;
                    }
                }
                sayac=0;

            }
            return this.arrBms.get(idSuandakiBitmap);
        }

    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }
}



