package edu.gatech.seclass.tourneymanager;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class EndMatch extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_match);

        Intent intent = getIntent();
        String username1 = intent.getStringExtra("player1");
        String username2 = intent.getStringExtra("player2");

        Button btn1 = (Button) findViewById(R.id.btn_winner1);
        btn1.setTag(username1);
        btn1.setText(username1);
        btn1.setOnClickListener(this);

        Button btn2 = (Button) findViewById(R.id.btn_winner2);
        btn2.setTag(username2);
        btn2.setText(username2);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String username = v.getTag().toString();
        Intent result = new Intent();
        result.putExtra("winner", username);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

}
