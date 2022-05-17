package com.example.flappybirdoyunu;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class GameView extends View {
    private Bird bird;
    private Handler handler;
    private Runnable r;
    private ArrayList<Pipe> arrPipes;
    private int topBoru, mesafe;
    private int score, bestScore = 0;
    private boolean start;
    private Context context;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
        if(sp!=null){
            bestScore =sp.getInt("bestscore", 0);
        }
        start = false;
        score = 0;
        initBird();
        initPipe();
        handler =  new Handler();

        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

    }

    private void initPipe() {
        topBoru = 6;
        mesafe = 350*Constants.SCREEN_HEIGHT/1920;
        arrPipes = new ArrayList<>();
        for(int i = 0; i < topBoru; i++){
            if(i<topBoru/2){
                this.arrPipes.add(new Pipe(Constants.SCREEN_WIDTH+i*((Constants.SCREEN_WIDTH+200*Constants.SCREEN_WIDTH/1080)/(topBoru/2)),0,200*Constants.SCREEN_WIDTH/1080, Constants.SCREEN_HEIGHT/2));
                this.arrPipes.get(this.arrPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.pipe2));
                this.arrPipes.get(this.arrPipes.size()-1).randomY();
            }else{
                this.arrPipes.add(new Pipe(this.arrPipes.get(i-topBoru/2).getX(), this.arrPipes.get(i-topBoru/2).getY() +
                this.arrPipes.get(i-topBoru/2).getHeight() + this.mesafe,200*Constants.SCREEN_WIDTH/1080, Constants.SCREEN_HEIGHT/2));
                this.arrPipes.get(this.arrPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe1));
            }
        }
    }

    private void initBird(){
        bird = new Bird();
        bird.setWidth(100*Constants.SCREEN_WIDTH/1080);
        bird.setHeight(100*Constants.SCREEN_HEIGHT/1920);
        bird.setX(100*Constants.SCREEN_WIDTH/1080);
        bird.setY(Constants.SCREEN_HEIGHT/2-bird.getHeight()/2);
        ArrayList<Bitmap> arrBms = new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird2));
        bird.setArrBms(arrBms);
    }

    public void draw(Canvas kanvas){
        super.draw(kanvas);
        if(start){
            bird.draw(kanvas);
            for(int i = 0; i < topBoru; i++){

                if(bird.getRect().intersect(arrPipes.get(i).getRect())  ||  bird.getY()-bird.getHeight() < 0  ||  bird.getY() > Constants.SCREEN_HEIGHT){
                    Pipe.speed = 0;
                    MainActivity.txt_finish_score.setText(MainActivity.txt_score.getText());
                    MainActivity.txt_best_score.setText("BEST: " + bestScore);
                    MainActivity.txt_score.setVisibility(INVISIBLE);
                    MainActivity.rl_game_over.setVisibility(VISIBLE);
                }

                if(this.bird.getX()+this.bird.getWidth() > arrPipes.get(i).getX() + arrPipes.get(i).getWidth()/2
                        && this.bird.getX()+this.bird.getWidth() <= arrPipes.get(i).getX() + arrPipes.get(i).getWidth()/2 + Pipe.speed
                        && i < topBoru/2){
                    score++;
                    if(score>bestScore){
                        bestScore = score;
                        SharedPreferences sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("bestScore", bestScore);
                        editor.apply();
                    }
                    MainActivity.txt_score.setText(""+score);
                }

                if(this.arrPipes.get(i).getX() < -arrPipes.get(i).getWidth()){
                    this.arrPipes.get(i).setX(Constants.SCREEN_WIDTH);
                    if(i<topBoru/2){
                        arrPipes.get(i).randomY();
                    }else{
                        arrPipes.get(i).setY(this.arrPipes.get(i-topBoru/2).getY() + this.arrPipes.get(i-topBoru/2).getHeight() + this.mesafe);
                    }
                }
                this.arrPipes.get(i).draw(kanvas);
            }
        }else{
            if(bird.getY() > Constants.SCREEN_HEIGHT/2){
                bird.setDrop(-15*Constants.SCREEN_HEIGHT/1920);
            }
            bird.draw(kanvas);
        }
        handler.postDelayed(r,10);   //Update every 0.01 seconds
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            bird.setDrop(-15);   //Düşerken dokununca ne kadar yükselecek
        }
        return true;
    }

    public boolean isStart(){
        return start;
    }

    public void setStart(boolean start){
        this.start = start;
    }

    public void reset(){
        MainActivity.txt_score.setText("0");
        score=0;
        initPipe();
        initBird();
    }
}