package edu.gatech.seclass.tourneymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EndTournamentConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_tournament_confirmation);

        Button btnYes = (Button) findViewById(R.id.btn_end_tournament_yes);
        Button btnNo = (Button) findViewById(R.id.btn_end_tournament_no);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tournament currentTournament = Tournament.getInstance();
                currentTournament.endTournament(EndTournamentConfirmation.this);
                finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
