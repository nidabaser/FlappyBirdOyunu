package com.example.flappybirdoyunu;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Bird extends BaseObject{
    private ArrayList<Bitmap> arrBms = new ArrayList<>();
    private int sayac, vFlap, idSuandakiBitmap;
    //Her tekrarda sayac bir artacak. Eğer sayac eşitse vflap o zaman kuşun bitmap ı değişecek
    private float drop;

    public Bird(){
        this.sayac = 0;
        this.vFlap = 5;
        this.idSuandakiBitmap = 0;
        this.drop = 0;
    }

    public void draw(Canvas kanvas){
        drop();
        kanvas.drawBitmap(this.getBm(), this.x, this.y, null);
    }

    private void drop() {   //Kuşun serbest düşmesi ve yükselmesi
        this.drop += 0.7;
        this.y+=this.drop;
    }

    public ArrayList<Bitmap> getArrBms() {
        return arrBms;
    }

    public void setArrBms(ArrayList<Bitmap> arrBms) { //Kuşun genişlik ve yüksekliğine göre bitmap ölçeklendirme
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
                sayac=0; //Döngüye sokmak için sürekli kuş resimleri dönmesi için sıfırlıyoruz.

            }
            return this.arrBms.get(idSuandakiBitmap);
            //return this.getArrBms().get(0);
        }

    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }
}



