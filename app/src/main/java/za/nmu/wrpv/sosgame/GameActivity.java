package za.nmu.wrpv.sosgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private Button[][] buttons;
    private boolean playerOneTurn;
    private boolean pressedS;
    private int fillCount;

    private int player1Points;
    private int player2Points;

    private TextView txtPlayerTurn;
    private TextView txtPlayer1Points;
    private TextView txtPlayer2Points;

    private Button btnPlayAgain;
    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        playerOneTurn = true;
        pressedS = true;
        fillCount = 0;
        player1Points = 0;
        player2Points = 0;
        buttons = new Button[5][5];
        txtPlayerTurn = findViewById(R.id.txtPlayerTurn);
        txtPlayer1Points = findViewById(R.id.txtPlayerOne);
        txtPlayer2Points = findViewById(R.id.txtPlayerTwo);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnHome = findViewById(R.id.btnHome);
        btnPlayAgain.setVisibility(View.GONE);
        btnPlayAgain.setVisibility(View.GONE);

        for(int r = 0; r < 5; r++) {
            for(int c = 0; c < 5; c++) {

            }
        }


    }

    public void onBoardButtonClick(View view) {
        if(!((Button) view).getText().toString().equals(""))
            return;

        if(pressedS) {
            ((Button) view).setText("s");
        } else {
            ((Button) view).setText("o");
        }
        if(playerOneTurn) {
            ((Button) view).setTextColor(Color.BLUE);
        } else {
            ((Button) view).setTextColor(Color.RED);
        }
        fillCount++;
        checkForSOS();
        if (checkForWin()) {
            txtPlayerTurn.setTypeface(null, Typeface.BOLD);
            if (player1Points > player2Points) {
                txtPlayerTurn.setText("Player 1 won!!!");
                txtPlayerTurn.setTextColor(Color.GREEN);
            } else if (player2Points > player1Points) {
                txtPlayerTurn.setText("Player 2 won!!!");
                txtPlayerTurn.setTextColor(Color.GREEN);
            } else {
                txtPlayerTurn.setText("It's a draw");
            }
            (findViewById(R.id.btnS)).setVisibility(View.GONE);
            (findViewById(R.id.btnO)).setVisibility(View.GONE);
            btnPlayAgain.setVisibility(View.VISIBLE);
            btnHome.setVisibility(View.VISIBLE);
        } else {
            playerOneTurn = !playerOneTurn;
            if (!playerOneTurn) {
                txtPlayerTurn.setText("Player 2's Turn");
            } else {
                txtPlayerTurn.setText("Player 1's Turn");
            }
        }
    }

    public boolean checkForWin() {
        return fillCount == 25;
    }

    private void checkForSOS() {
        String[][] board = new String[5][5];
        for(int r = 0; r < 5; r++) {
            for(int c = 0; c < 5; c++) {
                board[r][c] = buttons[r][c].getText().toString();
            }
        }

        //Checking operation for columns
        for(int r = 0; r < 2; r++) {
            for(int c = 0; c < 5; c++) {
                if((board[r][c].equals("s") || board[r][c].equals("S")) && board[r+1][c].equals("o")
                        && (board[r+2][c].equals("s") || board[r+2][c].equals("S"))) {
                    setUpperCase(r, c, "S");
                    setUpperCase(r+1, c, "O");
                    setUpperCase(r+2, c, "S");

                    if(playerOneTurn) {
                        player1Points++;
                        txtPlayer1Points.setText(player1Points);
                    } else {
                        player2Points++;
                        txtPlayer2Points.setText(player2Points);
                    }
                }
            }
        }

        //Checking operation for rows
        for(int c = 0; c < 2; c++) {
            for(int r = 0; r < 5; r++) {
                if((board[r][c].equals("s") || board[r][c].equals("S")) && board[r][c+1].equals("o")
                        && board[r][c+2].equals("s")) {
                    setUpperCase(r, c, "S");
                    setUpperCase(r, c+1, "O");
                    setUpperCase(r, c+2, "S");

                    if(playerOneTurn) {
                        player1Points++;
                        txtPlayer1Points.setText(player1Points);
                    } else {
                        player2Points++;
                        txtPlayer2Points.setText(player2Points);
                    }
                }
            }
        }

        //Checking operation for back diagonal
        for(int r = 0; r < 2; r++) {
            for(int c = 0; c < 3; c++) {
                if((board[r][c].equals("s") || board[r][c].equals("S")) && board[r+1][c+1].equals("o")
                        && board[r+2][c+2].equals("s")) {
                    setUpperCase(r, c, "S");
                    setUpperCase(r+1, c+1, "O");
                    setUpperCase(r+2, c+2, "S");

                    if(playerOneTurn) {
                        player1Points++;
                        txtPlayer1Points.setText(player1Points);
                    } else {
                        player2Points++;
                        txtPlayer2Points.setText(player2Points);
                    }
                }
            }
        }

        //Checking operation for forward diagonal
        for(int r = 0; r < 2; r++) {
            for(int c = 2; c < 5; c++) {
                if((board[r][c].equals("s") || board[r][c].equals("S")) && board[r+1][c-1].equals("o")
                        && board[r+2][c-2].equals("s")) {
                    setUpperCase(r, c, "S");
                    setUpperCase(r+1, c-1, "O");
                    setUpperCase(r+2, c-2, "S");

                    if(playerOneTurn) {
                        player1Points++;
                        txtPlayer1Points.setText(player1Points);
                    } else {
                        player2Points++;
                        txtPlayer2Points.setText(player2Points);
                    }
                }
            }
        }
    }

    public void setUpperCase(int r, int c, String str) {
        String buttonId = "button" + r + c;
        int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
        Button btn = findViewById(resourceId);

        if (str.equals("S")) {
            int textColor = btn.getCurrentTextColor();
            if (playerOneTurn && textColor == 0xFF0000) {
                btn.setTextColor(Color.GREEN);
            } else if (!playerOneTurn && textColor == 0x0000FF) {
                btn.setTextColor(Color.GREEN);
            }
        }
        btn.setText(str);
    }

    public void onSClicked(View view) {
        pressedS = true;
        (findViewById(R.id.btnS)).setBackgroundResource(R.drawable.grey_button);
    }

    public void onOClicked(View view) {
        pressedS = false;
        (findViewById(R.id.btnO)).setBackgroundResource(R.drawable.grey_button);
    }

    public void onPlayAgainClicked(View view) {
        Intent gameIntent = new Intent(this, GameActivity.class);
        startActivity(gameIntent);
    }

    public void onHomeClicked(View view) {
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }
}