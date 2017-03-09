package edu.gatech.seclass.tourneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewManagerScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_manager_screen);

        final Button btnAddPlayer = (Button) findViewById(R.id.manager_add_player);
        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewManagerScreen.this, AddPlayer.class);
                startActivity(intent);
            }
        });

        final Button btnDeletePlayer = (Button) findViewById(R.id.manager_delete_player);
        btnDeletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewManagerScreen.this, DeletePlayer.class);
                startActivity(intent);
            }
        });
        final Button btnSetupTournament = (Button) findViewById(R.id.manager_setup_tournament);
        btnSetupTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewManagerScreen.this, SetupTournament.class);
                startActivity(intent);
            }
        });

        final Button btnViewPrizes = (Button) findViewById(R.id.manager_view_prize_list);
        btnViewPrizes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewManagerScreen.this, ViewPrizeList.class);
                startActivity(intent);
            }
        });

        final Button btnViewProfits = (Button) findViewById(R.id.manager_view_profit_list);
        btnViewProfits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewManagerScreen.this, ViewProfitList.class);
                startActivity(intent);
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

}
