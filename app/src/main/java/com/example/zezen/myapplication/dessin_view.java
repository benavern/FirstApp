package com.example.zezen.myapplication;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.Vector;

/**
 * Created by ZeZeN on 07/10/2014.
 */
public class dessin_view extends View {

    private int posX=0, posY=0, width, height, draw_height, draw_width, rayon_boule = 20, nb_perdu = 0;
    public float offsetX, offsetY;
    public Rect ballRect, arrivee;
    public Vector<Rect> obstacles;
    public int posDX=0, posDY=0, posAX=0, posAY=0;
    private dessin mon_dessin;
    private boolean sortie_jeu = false;
    private String player="ZeZen";
    private long chrono;
    private int sensibilite = 3;/// = 3;
    private int difficulte = 3;
    private int gap_between = 70;
    private int gap_rightleft = 8;



    dessin_view(Context c, int w, int h, String player_name, String sensibilite, String difficulte){
        super(c);
        this.width = w;
        this.height = h;
        ballRect=new Rect();
        arrivee = new Rect();
        obstacles = new Vector();
        if (player_name.length()>0) {
            this.player = player_name;
        }
        this.difficulte = Integer.parseInt(difficulte);
        this.sensibilite = Integer.parseInt(sensibilite);

    }


    Paint paint = new Paint();

    public int random(int mini, int max){
        return (int)( Math.random()*( max - mini + 1 ) ) + mini;
    }

    // rate: le quotient de proportionnalite pour positionner la bar d'entête
    public void draw_frame(int rate){
        //haut
        obstacles.addElement(new Rect(0,0,width,height/rate));
        //bas
        obstacles.addElement(new Rect (0, height-250, width, height));
        this.draw_height = height-height/rate-250;
    }

    // y: hauteur, space: espace libre pour la balle (rapport a la largeur)
    public void bar_right(int y, int space){
        obstacles.addElement(new Rect (0, y, width-width/space,y+20));
    }

    // y: hauteur, space: espace libre pour la balle (rapport a la largeur)
    public void bar_left(int y, int space){
        //obstacles.addElement(new Rect (0, y, width-width/space,y+20));
        obstacles.addElement(new Rect (width/space, y, width,y+20));
    }

    // y: hauteur, space: espace libre pour la balle (rapport a la largeur)
    public void bar_betweenspace(int y, int rate ,int space){
        //obstacles.addElement(new Rect (0, y, width-width/space,y+20));
        obstacles.addElement(new Rect (0, y, width-width/rate,y+20));
        obstacles.addElement(new Rect (width-width/rate+space, y, width,y+20));
    }

    public void draw_labyrinth(int root){
        // case line stop on the right\
        boolean flag_1 = false;
        boolean flag_2 = false;
        for(int x = 1; x < root; x = x+1) {
            if(x==1){
                bar_right((height / 7) + x * (this.draw_height / root), gap_rightleft);
                flag_1 = true;
                flag_2 = false;
            } else if(x==root-1){
                bar_left((height / 7) + x * (this.draw_height / root), gap_rightleft);
                flag_1 = false;
                flag_2 = true;
            }else{
                switch (random(1, 3)) {
                    case 1:
                        if (!flag_1) {
                            bar_right((height / 7) + x * (this.draw_height / root), gap_rightleft);
                            flag_1 = true;
                            flag_2 = false;
                        } else {
                            bar_left((height / 7) + x * (this.draw_height / root), gap_rightleft);
                            flag_1 = false;
                            flag_2 = true;
                        }
                        break;
                    case 2:
                        if (!flag_2) {
                            bar_left((height / 7) + x * (this.draw_height / root),gap_rightleft);
                            flag_2 = true;
                            flag_1 = false;
                        } else {
                            bar_right((height / 7) + x * (this.draw_height / root), gap_rightleft);
                            flag_2 = false;
                            flag_1 = true;
                        }
                        break;
                    case 3:
                        bar_betweenspace((height / 7) + x * (this.draw_height / root), random(1, 4), gap_between);
                        flag_2 = false;
                        flag_1 = false;
                        break;
                }
            }
        }

//        bar_left(2*this.draw_height /root,8);
//
//        // case line stopped in between
//        bar_betweenspace(3*this.draw_height /root,3,70);
//
//        bar_betweenspace(4*this.draw_height /root,3,70);
//        bar_betweenspace(5*this.draw_height /root,3,70);
//        bar_betweenspace(6*this.draw_height /root,3,70);
//        bar_betweenspace(7*this.draw_height /root,3,70);

    }

    public void init(dessin mon_dessin){
        this.mon_dessin = mon_dessin;
        draw_frame(7);

        // point de départ en haut à gauche
        this.posDX = 50;
        this.posDY = height/7+40;
        // arrivée en bas à droite
        this.posAX = width-50;
        this.posAY = height-295;


        switch (difficulte){
            case 1:
                gap_between = 140;
                gap_rightleft = 6;
                draw_labyrinth(5);
                break;
            case 2:
                gap_between = 110;
                gap_rightleft = 7;
                draw_labyrinth(6);
                break;
            case 3:
                gap_between = 90;
                gap_rightleft = 8;
                draw_labyrinth(7);
                break;
            case 4:
                gap_between = 70;
                gap_rightleft = 8;
                draw_labyrinth(8);
                break;
            case 5:
                gap_between = 60;
                gap_rightleft = 9;
                draw_labyrinth(9);
                break;
        }


    }

    protected void onDraw(Canvas c){

       // this.chrono = dessin.get_chrono();

        //les déplacements
        posX -= Math.round(offsetX)*(sensibilite);//droite-gauche
        posY += Math.round(offsetY)*(sensibilite);//haut-bas

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
        c.drawText("| Reste " + (5-nb_perdu) + " vies!" ,3*width/6 , height/14, paint);

        //affiche le chrono
        c.drawText(get_chrono_string(),1*width/7,height/14,paint);

        //affiche le nom du joueur
        c.drawText(this.player,3*width/7,(height/8+height/7)/2,paint);

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
            this.mon_dessin.playSound(this, 1);
            nb_perdu += 1;
            posX = 0;
            posY = 0;
//            Log.v("try_again", "ouais ouais!");
        }
        else{
            if (sortie_jeu == false) {
                sortie_jeu = true;
                this.mon_dessin.playSound(this, 2);
                perdu();
            }
        }

    }

    protected void perdu(){
//        Log.v("perdu", "nan nan!");
        //this.mon_dessin.playSound(this, 2);
        this.mon_dessin.perdu();
    }

    protected void victoire(){
        if (sortie_jeu == false) {
            sortie_jeu = true;
            this.mon_dessin.playSound(this, 3);
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
        int heures = Math.round(((chrono / 1000) - (jours * 60 * 60 * 24)) / (60 * 60));
        int minutes = Math.round(((chrono/1000) - ((jours * 60 * 60 * 24 + heures * 60 * 60))) / 60);
        int secondes = Math.round((chrono / 1000) - ((jours * 60 * 60 * 24 + heures * 60 * 60 + minutes * 60)));

        String the_chrono = ( String.valueOf(heures) + "h " + String.valueOf(minutes) + "m " + String.valueOf(secondes)+"s" );

        return the_chrono;
    }


}
