package edu.gatech.seclass.tourneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectMode extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        final Tournament currentTournament = Tournament.getInstance();
        final Mode currentMode = Mode.getInstance();

        final Button playerModeBtn = (Button) findViewById(R.id.player_mode);
        playerModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMode.setMode(Mode.MODES.player);
                if (currentTournament.getStatus() == Tournament.Status.ONGOING){
                    // ongoing tournament
                    Intent intent = new Intent(SelectMode.this, ViewMatches.class);
                    startActivity(intent);
                } else {
                    // no tournament, show prize list
                    Intent intent = new Intent(SelectMode.this, ViewPrizeList.class);
                    startActivity(intent);
                }
            }
        });

        final Button managerModeBtn = (Button) findViewById(R.id.manager_mode);
        managerModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMode.setMode(Mode.MODES.manager);
                if (currentTournament.getStatus() == Tournament.Status.ONGOING){
                    // ongoing tournament
                    Intent intent = new Intent(SelectMode.this, ViewMatches.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SelectMode.this, ViewManagerScreen.class);
                    startActivity(intent);
                }
            }
        });
    }

}
