package com.example.and.jogodavelha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // banana = 1; apple = 0, empty = 2
    // the game starts with everything empty and with apple being the first fruit
    int fruitActive=0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    // these are the combinations that make the game to end
    int[][] winPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive=true;

    public void addButton(View view){
        String fruit;
        ImageView counter=(ImageView) view;
        // the tag keeps track of the position activated
        int imgSelected= Integer.parseInt(counter.getTag().toString());
        Button restartButton=(Button) findViewById(R.id.button);
        TextView restartTextview=(TextView) findViewById(R.id.textView);

        // to avoid continue the game after finished or replace images already set
        if (gameState[imgSelected]==2 && gameActive){

            // move image outside game to be able to use animation
            counter.setTranslationY(-1500);
            // get the type of fruit used in the image and added to gameState
            gameState[imgSelected]= fruitActive;
            // alternate the fruits
            if(fruitActive==1){
                counter.setImageResource(R.drawable.banana);
                fruitActive=0;
            } else {
                counter.setImageResource(R.drawable.maca);
                fruitActive=1;
            }
            // put the image in the position activated
            counter.animate().translationYBy(1500).rotation(3600).setDuration(100);

            // check if someone won
            for (int[] w: winPositions) {
                if (gameState[w[0]] == gameState[w[1]] && gameState[w[1]] == gameState[w[2]] && gameState[w[0]] != 2) {
                    if (gameState[w[0]] == 1) {
                        fruit = "Banana";
                    } else {
                        fruit = "Apple";
                    }
                    // print message end game
                    Toast.makeText(this, fruit + " has won!", Toast.LENGTH_LONG).show();
                    gameActive=false;
                    restartTextview.setText(fruit + " has won!");
                    // show button to restart game
                    restartTextview.setVisibility(View.VISIBLE);
                    restartButton.setVisibility(View.VISIBLE);

                }
            }

        }
        // if all the positions are filled and nobody won
        boolean continueGame=false;
        for (int i=0; i<gameState.length; i++){
            if (gameState[i]==2){
                continueGame=true;
                break;
            }
        }
        if (!continueGame){
            restartButton.setVisibility(View.VISIBLE);
        }

    }

    // set all the game back to beginning
    public void restart(View view){
        Button restartButton=(Button) findViewById(R.id.button);
        TextView restartTextview=(TextView) findViewById(R.id.textView);
        GridLayout myGridView =(GridLayout) findViewById(R.id.gridLayout);
        restartTextview.setVisibility(View.INVISIBLE);
        restartButton.setVisibility(View.INVISIBLE);
        for (int i=0; i<myGridView.getChildCount();i++){
            ImageView counter = (ImageView) myGridView.getChildAt(i);
            counter.setImageDrawable(null);
        }

        fruitActive=0;
        gameActive=true;
        for (int i=0;i<gameState.length;i++){
            gameState[i]=2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
