package com.example.flappybirdoyunu;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Bird extends BaseObject{
    private ArrayList<Bitmap> arrBms = new ArrayList<>();
    private int sayac, vFlap, idSuandakiBitmap;
    //Her tekrarda sayac bir artacak. Eğer sayac eşitse vflap o zaman kuşun bitmap ı değişecek

    public Bird(){
        this.sayac = 0;
        this.vFlap = 5;
        this.idSuandakiBitmap = 0;
    }

    public void draw(Canvas kanvas){
        kanvas.drawBitmap(this.getBm(), this.x, this.y, null);
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
            if(sayac==vFlap){
                for(int i=0;i<arrBms.size();i++){
                    if(i == arrBms.size()-1){
                        idSuandakiBitmap = 0;
                        break;
                    }else if(idSuandakiBitmap == i){
                        idSuandakiBitmap = i+1;
                        break;
                    }
                }
                sayac=0; //Döngüye sokmak için sürekli kuş resimleri dönmesi için

            }
            return this.arrBms.get(idSuandakiBitmap);
            //return this.getArrBms().get(0);
        }

    }

