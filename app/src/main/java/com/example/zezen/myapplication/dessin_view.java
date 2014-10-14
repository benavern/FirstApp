package com.example.zezen.myapplication;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.Vector;

/**
 * Created by ZeZeN on 07/10/2014.
 */
public class dessin_view extends View {

    private int posX=0, posY=0, width, height, rayon_boule = 20, nb_perdu = 0;
    public float offsetX, offsetY;
    public Rect ballRect, arrivee;
    public Vector<Rect> obstacles;
    public int posDX=0, posDY=0, posAX=0, posAY=0;
    private dessin mon_dessin;
    private boolean sortie_jeu = false;
    private String player="no_name";
    private long chrono;




    dessin_view(Context c, int w, int h, String player_name){
        super(c);
        this.width = w;
        this.height = h;
        ballRect=new Rect();
        arrivee = new Rect();
        obstacles = new Vector();
        if (player_name.length()>0) {
            this.player = player_name;
        }

    }


    Paint paint = new Paint();

    public void init(dessin mon_dessin){
        this.mon_dessin = mon_dessin;

        //haut
        obstacles.addElement(new Rect(0,0,width,150));
        //bas
        obstacles.addElement(new Rect (0, height-250, width, height));

        //les autres
        obstacles.addElement(new Rect (0, 350, width-width/6, 400));
        obstacles.addElement(new Rect (width/6, 600, width, 650));

        obstacles.addElement(new Rect (0, height/2, width/2-50, height/2+50));
        obstacles.addElement(new Rect (width/2+50, height/2, width, height/2+50));


        obstacles.addElement(new Rect (0, height-700, width-width/6, height-650));
        obstacles.addElement(new Rect (width/8, height-450, width, height-400));


        // point de départ en haut à gauche
        this.posDX = 100;
        this.posDY = 250;
        // arrivée en bas à droite
        this.posAX = width-100;
        this.posAY = height-350;


    }

    protected void onDraw(Canvas c){

       // this.chrono = dessin.get_chrono();

        //les déplacements
        posX -= Math.round(offsetX)*2;//droite-gauche
        posY += Math.round(offsetY)*2;//haut-bas

        //fond
        c.drawARGB(255,128,128,128);


        //Point de départ
        paint.setARGB(128,0,255,0);
        c.drawCircle(posDX, posDY, 2*rayon_boule, paint);

        //Point d'arrivée
        paint.setARGB(128,255,0,0);
        arrivee.set(posAX-2*rayon_boule, posAY-2*rayon_boule,posAX+2*rayon_boule, posAY+2*rayon_boule);
        c.drawCircle(posAX, posAY, 2*rayon_boule, paint);


        //tracer les obstacles
        paint.setARGB(255,0,0,0);
        for(int i=0;i<obstacles.size();i++) {
            c.drawRect(obstacles.get(i), paint);
        }


        //tracer la bouboule
        paint.setARGB(255,0,102,204);
        ballRect.set(posDX+posX-rayon_boule, posY+posDY-rayon_boule,posDX+posX+rayon_boule, posDY+posY+rayon_boule );
        c.drawCircle(posDX+posX, posDY+posY, rayon_boule, paint);


        //affiche les vies
        paint.setTextSize(60);
        paint.setARGB(150,255,255,255);
        c.drawText("Reste " + (5-nb_perdu) + " vies!" ,100 , 100, paint);

        //affiche le chrono
        c.drawText(get_chrono_string(),width/2,100,paint);



        //intersection avec les bords de l'ecran
        if ( posX+posDX-rayon_boule <= 0 || posX+posDX+rayon_boule >= width || posY+posDY-rayon_boule <= 0 || posY+posDY+rayon_boule >= height){
            try_again();
        }

        //gestion des intersections
        for(int i=0;i<obstacles.size();i++){
            if (ballRect.intersect(obstacles.get(i))){
                try_again();
            }
        }

        if (ballRect.intersect(arrivee)){
            victoire();
        }


    }

    protected void try_again(){
        if (5-nb_perdu>0){
            this.mon_dessin.playSound(this);
            nb_perdu += 1;
            posX = 0;
            posY = 0;
//            Log.v("try_again", "ouais ouais!");
        }
        else{
            if (sortie_jeu == false) {
                sortie_jeu = true;
                perdu();
            }
        }

    }

    protected void perdu(){
//        Log.v("perdu", "nan nan!");
        this.mon_dessin.perdu();
    }

    protected void victoire(){
        if (sortie_jeu == false) {
            sortie_jeu = true;
            this.mon_dessin.victoire();
            //mon_dessin.finish();
//            Log.v("victoire", "ouais ouais!");
        }
    }

    public void set_offsets(float x, float y){
        offsetX = x;
        offsetY = y;
    }


    public void set_chrono(long t){
        chrono = t;
    }

    public String get_chrono_string(){

        int jours = Math.round((chrono/1000) / (60 * 60 * 24));
        int heures = Math.round(((chrono/1000) - (jours * 60 * 60 * 24)) / (60 * 60));
        int minutes = Math.round(((chrono/1000) - ((jours * 60 * 60 * 24 + heures * 60 * 60))) / 60);
        int secondes = Math.round((chrono/1000) - ((jours * 60 * 60 * 24 + heures * 60 * 60 + minutes * 60)));

        String the_chrono = ( String.valueOf(heures) + "h " + String.valueOf(minutes) + "m " + String.valueOf(secondes)+"s" );

        return the_chrono;
    }


}
