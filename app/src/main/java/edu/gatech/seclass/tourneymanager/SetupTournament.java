package edu.gatech.seclass.tourneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class SetupTournament extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_tournament);

        DBHelper db = new DBHelper(SetupTournament.this);
        ArrayList<String> players = db.getAllPlayerUsernames();

        LinearLayout layout = (LinearLayout) findViewById(R.id.setup_tournament_player_list);
        int num = players.size();
        while (--num >= 0){
            CheckBox playerCheckbox = new CheckBox(this);
            playerCheckbox.setText(players.get(num));
            playerCheckbox.setTag(players.get(num));
            layout.addView(playerCheckbox);
        }

        Button btnReviewTournamentSetup = (Button) findViewById(R.id.setup_tournament_review);
        btnReviewTournamentSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText housePercentageField = (EditText) findViewById(R.id.setup_tournament_house_percentage);
                EditText entryFeeField = (EditText) findViewById(R.id.setup_tournament_entry_fee);

                boolean valid = true;

                // check house percentage >= 0 && <= 100
                int housePercentage = validateNumber(housePercentageField.getText().toString(), 100);
                if (housePercentage < 0){
                    valid = false;
                    housePercentageField.setError("Invalid house percentage.");
                }

                //check entry fee >= 0
                int entryFee = validateNumber(entryFeeField.getText().toString());
                if (entryFee < 0){
                    valid = false;
                    entryFeeField.setError("Invalid fee");
                }

                // check number of players
                ArrayList<String> entrants = new ArrayList<String>();
                LinearLayout layout = (LinearLayout) findViewById(R.id.setup_tournament_player_list);
                int num = layout.getChildCount();
                while (--num >= 0) {
                    CheckBox thisCheckbox = (CheckBox) layout.getChildAt(num);
                    if (thisCheckbox.isChecked()) {
                        entrants.add(thisCheckbox.getTag().toString());
                    }
                }
                if (entrants.size() != 8 && entrants.size() != 16){
                    // show error message
                    CharSequence text = "Invalid number of entrants. Select 8 or 16.";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(SetupTournament.this, text, duration);
                    toast.show();
                    valid = false;
                }
                if (valid){
                    double total = 1.0 * entryFee * entrants.size();
                    double houseCutValue = total * housePercentage / 100.0;
                    double remainder = total - houseCutValue;
                    Intent intent = new Intent(SetupTournament.this, SetupTournamentConfirmation.class);
                    intent.putExtra("houseCut", Double.toString(houseCutValue));
                    intent.putExtra("remainder", Double.toString(remainder));
                    intent.putStringArrayListExtra("entrants", entrants);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        Tournament currentTournament = Tournament.getInstance();
        if (currentTournament.getStatus() == Tournament.Status.ONGOING){
            finish();
        }
    }

    private int validateNumber(String test){
        int testInt;
        if (test == null || test.isEmpty()){
            return -1;
        }
        try{
            testInt = Integer.parseInt(test);
        } catch (NumberFormatException nfe){
            return -1;
        }
        if (testInt < 0){
            return -1;
        }
        return testInt;
    }

    private int validateNumber(String test, int upper){
        int testInt = validateNumber(test);
        if (testInt <= upper){
            return testInt;
        }
        return -1;
    }
}
