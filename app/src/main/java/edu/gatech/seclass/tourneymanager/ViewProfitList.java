package edu.gatech.seclass.tourneymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewProfitList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profit_list);

        DBHelper db = new DBHelper(ViewProfitList.this);
        ArrayList<ProfitRecord> profits = db.getProfits();

        int totalProfits = 0;

        LinearLayout layout = (LinearLayout) findViewById(R.id.profits_list);
        for (ProfitRecord pr : profits){
            totalProfits += pr.getProfit();
            TextView entry = new TextView(ViewProfitList.this);
            StringBuilder entryText = new StringBuilder();
            entryText.append(pr.getDate());
            entryText.append(" : ");
            entryText.append(Integer.valueOf(pr.getProfit()));
            entry.setText(entryText.toString());
            layout.addView(entry);
        }
        TextView textProfitTotal = (TextView) findViewById(R.id.textProfitsTotal);
        textProfitTotal.setText("\nTotal Profits: " + Integer.toString(totalProfits) + "\n");
    }
}
