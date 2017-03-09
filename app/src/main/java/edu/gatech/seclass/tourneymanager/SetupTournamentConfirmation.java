package edu.gatech.seclass.tourneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SetupTournamentConfirmation extends AppCompatActivity {

    private int houseCut;
    private int[] prizes;
    private ArrayList<String> entrants;

    private void setHouseCut(int i){
        this.houseCut = i;
    }
    private int getHouseCut(){
        return this.houseCut;
    }
    private void setPrizes(int[] prizes){
        this.prizes = prizes;
    }
    private int[] getPrizes(){
        return this.prizes;
    }
    private void setEntrants(ArrayList<String> entrants){
        this.entrants = entrants;
    }
    private ArrayList<String> getEntrants(){
        return entrants;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_tournament_confirmation);

        Intent intent = getIntent();
        Double profit = Double.parseDouble(intent.getStringExtra("houseCut"));
        Double remainder = Double.parseDouble(intent.getStringExtra("remainder"));
        ArrayList<String> entrants = intent.getStringArrayListExtra("entrants");

        int houseCut = (int) Math.round(profit);
        this.setHouseCut(houseCut);
        int[] prizes = new int[]{
                (int) Math.round(remainder * 0.5),
                (int) Math.round(remainder * 0.3),
                (int) Math.round(remainder * 0.2)
        };
        this.setPrizes(prizes);
        this.setEntrants(entrants);

        EditText houseCutField = (EditText) findViewById(R.id.houseProfit);
        EditText prize1st = (EditText) findViewById(R.id.prizes_1st);
        EditText prize2nd = (EditText) findViewById(R.id.prizes_2nd);
        EditText prize3rd = (EditText) findViewById(R.id.prizes_3rd);

        houseCutField.setText(String.valueOf(houseCut));
        prize1st.setText(String.valueOf(prizes[0]));
        prize2nd.setText(String.valueOf(prizes[1]));
        prize3rd.setText(String.valueOf(prizes[2]));

        LinearLayout layout = (LinearLayout) findViewById(R.id.comfirm_player_list);
        int num = entrants.size();
        while (--num >= 0) {
            TextView entrant = new TextView(SetupTournamentConfirmation.this);
            entrant.setText(entrants.get(num));
            layout.addView(entrant);
            }

        final Button startTournament = (Button) findViewById(R.id.btn_confirm_start_tournament);
        startTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tournament currentTournament = Tournament.getInstance();
                currentTournament.startTournament(
                        SetupTournamentConfirmation.this.getHouseCut(),
                        SetupTournamentConfirmation.this.getPrizes(),
                        SetupTournamentConfirmation.this.getEntrants()
                );
                finish();
            }
        });
    }

}
