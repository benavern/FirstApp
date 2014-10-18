package com.example.zezen.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

/*
*
* Vous verrez probablement des morceaux de code improbables, j'ai fait ça en suivant un tuto sur le net...
* j'ai fait en sorte de ne pas utiliser ce code, mais n'avais pas franchement le courage de supprimer les
* morceaux inutiles.
*
* */
public class FirstActivity extends ActionBarActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    Button suivant;
    TextView  val_sensibilite, val_difficulte;
    EditText nom;
    SeekBar sensibilite, difficulte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first);

        /*--------------------------------------------------------------------*/
        nom = (EditText) findViewById(R.id.editText);

        suivant = (Button) findViewById(R.id.button2);


        sensibilite = (SeekBar) findViewById(R.id.sensibilite);
        difficulte = (SeekBar) findViewById(R.id.difficulte);

        val_sensibilite = (TextView) findViewById(R.id.val_sensibilite);
        val_difficulte = (TextView) findViewById(R.id.val_difficulte);

        /*---------------------------------------------------------------------*/

        suivant.setOnClickListener(this);
        sensibilite.setOnSeekBarChangeListener(this);

    }

    public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
        switch(seekbar.getId()){
            case R.id.sensibilite:
                val_sensibilite.setText(progress);
                break;
            case R.id.difficulte:
                val_difficulte.setText(progress);
                break;

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void onClick(View v){
        Intent intent = new Intent(FirstActivity.this, dessin.class);
        intent.putExtra("player_name",nom.getText().toString());
        intent.putExtra("sensibilite",(""+(sensibilite.getProgress()+1)));
        intent.putExtra("difficulte",(""+(difficulte.getProgress()+1)));

       // Log.v("je m'appelle", nom.getText().toString());
        startActivity(intent);
    }

//    /*
//    test avec onclicklistener sinon*/
//    public void addCopine(View v){
//        compteur++;
//        afficheur.setText("Vous avez " + compteur + " meufs");
//    }
//
//    public void dellCopine(View v){
//        if (compteur>0) {
//            compteur--;
//            afficheur.setText("Vous avez " + compteur + " meufs");
//        }
//    }






/*-----------------------------------------------------------------------------------*/
    /*public void pop(View v){
        resultat.setText("Coucou " + nom.getText() + " " + prenom.getText() + "!");
    }*/




/*

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
*/
}
