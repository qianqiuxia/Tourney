package edu.gatech.seclass.tourneymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        // set deck choices to Player.DeckChoice enum names
        // adapted from http://stackoverflow.com/questions/7182844/adding-enums-to-array-adapter-for-spinner-in-android
        Spinner deckChoices = (Spinner) findViewById(R.id.spinner_deck_choice);
        ArrayAdapter<Player.DeckChoice> adapter = new ArrayAdapter<Player.DeckChoice>(this,
                android.R.layout.simple_list_item_1, Player.DeckChoice.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deckChoices.setAdapter(adapter);

        final Button add_player_button = (Button) findViewById(R.id.button_add_player);
        add_player_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player p = new Player();
                EditText playerNameField = (EditText) findViewById(R.id.input_player_name);
                p.setName(playerNameField.getText().toString());
                EditText playerUsernameField = (EditText) findViewById(R.id.input_username);
                p.setUsername(playerUsernameField.getText().toString());
                EditText playerPhoneField = (EditText) findViewById(R.id.input_phone_number);
                p.setPhoneNumber(playerPhoneField.getText().toString());
                Spinner choices = (Spinner) findViewById(R.id.spinner_deck_choice);
                p.setDeckChoice((Player.DeckChoice) choices.getSelectedItem());

                // TODO: validate for existing user or catch DB error
                if (true) {
                    DBHelper db = new DBHelper(AddPlayer.this);
                    db.addPlayer(p);

                    // switch back to parent activity
                    finish();
                }
            }
        });

    }
}
