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
import android.widget.Toast;

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
        difficulte.setOnSeekBarChangeListener(this);

    }

    public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
//        switch(seekbar.getId()){
//            Log.v("", "" + seekbar);
//
//            case R.id.sensibilite:
//                val_sensibilite.setText(""+(progress+1));
//                break;
//
//            case R.id.difficulte:
//                val_difficulte.setText(""+(progress+1));
//                break;
//
//        }

        if (bar.equals(sensibilite))
        {
            // do something
            val_sensibilite.setText(""+(progress+1));
        }
        else if (bar.equals(difficulte))
        {
            // do something else
            val_difficulte.setText(""+(progress+1));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void onClick(View v){
        if (nom.getText().toString().length() != 0) {
            Intent intent = new Intent(FirstActivity.this, dessin.class);
            intent.putExtra("player_name", nom.getText().toString());
            intent.putExtra("sensibilite", ("" + (sensibilite.getProgress() + 1)));
            intent.putExtra("difficulte", ("" + (difficulte.getProgress() + 1)));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Veuillez entrer un Pseudo SVP!", Toast.LENGTH_LONG).show();
        }
    }




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
