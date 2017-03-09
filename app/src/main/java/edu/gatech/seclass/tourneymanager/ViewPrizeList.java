package edu.gatech.seclass.tourneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewPrizeList extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prize_list);

        TextView titleText = (TextView) findViewById(R.id.textPrizeListTitle);
        Mode currentMode = Mode.getInstance();
        if (currentMode.getMode() == Mode.MODES.manager) {
            titleText.setText("Manager Mode");
        } else {
            titleText.setText("Player Mode");
        }
        DBHelper db = new DBHelper(ViewPrizeList.this);
        ArrayList<PrizeRecord> prizes = db.getPrizes();

        LinearLayout layout = (LinearLayout) findViewById(R.id.prizes_list);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (PrizeRecord pr : prizes){
            Button entry = new Button(ViewPrizeList.this);
            StringBuilder entryText = new StringBuilder();
            entryText.append(pr.getPlayer());
            entryText.append(" : ");
            entryText.append(Integer.valueOf(pr.getAmount()));
            entry.setText(entryText.toString());
            entry.setTag(pr.getPlayer());
            entry.setLayoutParams(btnParams);
            if (currentMode.getMode() == Mode.MODES.manager){
                entry.setOnClickListener(this);
            } else {
                entry.setEnabled(false);
            }
            layout.addView(entry);
        }
        if (prizes.size() == 0){
            TextView entry = new TextView(ViewPrizeList.this);
            entry.setText("No prizes yet.");
            layout.addView(entry);
        }
    }

    @Override
    public void onClick(View v) {
        String username = v.getTag().toString();
        Intent intent = new Intent(ViewPrizeList.this, ViewPlayerPrizes.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

}
