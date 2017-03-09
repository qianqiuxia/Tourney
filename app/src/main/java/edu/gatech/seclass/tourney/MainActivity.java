package edu.gatech.seclass.tourney;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    EditText playerInput;
    TextView playerText;
    MyDBHandler dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerInput = (EditText) findViewById(R.id.playerInput);
        playerText = (TextView) findViewById(R.id.playerText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        printDatabase();

    }

    public void addButtonClicked(View view){
        setContentView(R.layout.add_player);
    }

    //Add a player to the database
    public void addPlayerButtonClicked(View view){
        Players player = new Players(playerInput.getText().toString());
        dbHandler.addPlayer(player);
        printDatabase();
    }

    //Delete player
    public void deletePlayerButtonClicked(View view){
        String inputText = playerInput.getText().toString();
        dbHandler.deletePlayer(inputText);
        printDatabase();

    }

    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        playerText.setText(dbString);
        playerInput.setText("");
    }
}
