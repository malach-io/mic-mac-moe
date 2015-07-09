package com.example.tmicmacmoe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, CompoundButton.OnCheckedChangeListener{

    boolean turn = true; // true = X & false = O
    boolean gameOver = false;
    int turnCounter = 0;
    TextView[] square = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        square = new TextView[]{
            (TextView) findViewById(R.id.square1),
            (TextView) findViewById(R.id.square2),
            (TextView) findViewById(R.id.square3),
            (TextView) findViewById(R.id.square4),
            (TextView) findViewById(R.id.square5),
            (TextView) findViewById(R.id.square6),
            (TextView) findViewById(R.id.square7),
            (TextView) findViewById(R.id.square8),
            (TextView) findViewById(R.id.square9),};
        for (TextView square : this.square) square.setOnClickListener(this);

        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id.switch_compat);
        switchCompat.setOnCheckedChangeListener(this);
        TextView newGame = (TextView) findViewById(R.id.new_game_button);
        newGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    public void newGame() {
        gameOver = false;
        turn = true;
        turnCounter = 0;
        setClickable(true);
    }

    @Override
    public void onClick(View view) {
        TextView square = (TextView) view;
        if (turn)square.setText("X");
        else square.setText("O");
        turnCounter++;
        square.setClickable(false);
        turn = !turn;

        if(!gameOver){
            // horizontal:
            winningCombo(this.square[0], this.square[1], this.square[2]);
            winningCombo(this.square[3], this.square[4], this.square[5]);
            winningCombo(this.square[6], this.square[7], this.square[8]);
            // vertical:
            winningCombo(this.square[0], this.square[3], this.square[6]);
            winningCombo(this.square[1], this.square[4], this.square[7]);
            winningCombo(this.square[2], this.square[5], this.square[8]);
            // diagonal:
            winningCombo(this.square[0], this.square[4], this.square[8]);
            winningCombo(this.square[2], this.square[4], this.square[6]);
            if(!gameOver){
                if (turnCounter == 9){
                    message("Draw!");
                    gameOver = true;
                }
            }
        }
    }

    public void winningCombo(TextView a, TextView b, TextView c) {
        boolean playerWon = false;
        if (a.getText() == b.getText() && b.getText() == c.getText() && !a.isClickable()){
            playerWon = true;
            gameOver = true;
            setClickable(false);
        }
        if (playerWon){
            message(a.getText().toString() + " wins!");
        }
    }

    private void setClickable(boolean clickable){
        for (TextView square : this.square) {
            square.setText("");
            square.setClickable(clickable);
        }
    }

    private void message(String text) { Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();}
}