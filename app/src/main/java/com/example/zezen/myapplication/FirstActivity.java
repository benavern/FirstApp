package com.example.zezen.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
*
* Vous verrez probablement des morceaux de code improbables, j'ai fait ça en suivant un tuto sur le net...
* j'ai fait en sorte de ne pas utiliser ce code, mais n'avais pas franchement le courage de supprimer les
* morceaux inutiles.
*
* */
public class FirstActivity extends ActionBarActivity implements View.OnClickListener {

    int compteur;
    Button ajouter, supprimer, afficher, bonus, suivant;
    TextView afficheur, resultat;
    EditText nom, prenom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first);
        //setContentView(R.layout.toto);

        compteur = 3;

        ajouter = (Button) findViewById(R.id.new_meuf);
        supprimer = (Button) findViewById(R.id.new_ex);
        //bonus = (Button) findViewById(R.id.bonus);
        afficheur = (TextView) findViewById(R.id.text_afficheur);



       /* ajouter.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                compteur++;
                afficheur.setText("Vous avez " + compteur + " meufs");
            }
        });*/

        /*bonus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                setContentView(R.layout.toto);

            }
        });*/

        /*--------------------------------------------------------------------*/
        nom = (EditText) findViewById(R.id.editText);
        //prenom = (EditText) findViewById(R.id.editText2);

        //afficher = (Button) findViewById(R.id.button);
        //resultat = (TextView) findViewById(R.id.text_result);

        suivant = (Button) findViewById(R.id.button2);
        /*---------------------------------------------------------------------*/

        suivant.setOnClickListener(this);

    }

    public void onClick(View v){
        Intent intent = new Intent(FirstActivity.this, dessin.class);
        intent.putExtra("player_name",nom.getText().toString());
       // Log.v("je m'appelle", nom.getText().toString());
        startActivity(intent);
    }

    /*
    test avec onclicklistener sinon*/
    public void addCopine(View v){
        compteur++;
        afficheur.setText("Vous avez " + compteur + " meufs");
    }

    public void dellCopine(View v){
        if (compteur>0) {
            compteur--;
            afficheur.setText("Vous avez " + compteur + " meufs");
        }
    }






/*-----------------------------------------------------------------------------------*/
    /*public void pop(View v){
        resultat.setText("Coucou " + nom.getText() + " " + prenom.getText() + "!");
    }*/






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
