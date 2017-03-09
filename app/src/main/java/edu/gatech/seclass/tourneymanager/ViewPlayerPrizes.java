package edu.gatech.seclass.tourneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewPlayerPrizes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_player_prizes);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        DBHelper db = new DBHelper(ViewPlayerPrizes.this);
        ArrayList<PrizeRecord> prizes = db.getPrizes(username);

        LinearLayout layout = (LinearLayout) findViewById(R.id.player_prize_list);
        int total = 0;

        for (PrizeRecord pr : prizes){
            total += pr.getAmount();
            TextView entry = new TextView(ViewPlayerPrizes.this);
            StringBuilder entryText = new StringBuilder();
            entryText.append(pr.getDate());
            entryText.append(" : ");
            entryText.append(Integer.valueOf(pr.getAmount()));
            entry.setText(entryText.toString());
            layout.addView(entry);
        }
        TextView textProfitTotal = (TextView) findViewById(R.id.player_prize_total);
        textProfitTotal.setText("\nTotal Prizes: " + Integer.toString(total) + "\n");
    }
}
