package edu.gatech.seclass.tourneymanager;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class DeletePlayer extends AppCompatActivity {

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_player);

        layout = (LinearLayout) findViewById(R.id.delete_player_list);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        layout.removeAllViews();
        fillListOfPlayers();
    }

    private void fillListOfPlayers() {
        DBHelper db = new DBHelper(DeletePlayer.this);
        ArrayList<String> players = db.getAllPlayerUsernames();

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int num = players.size();
        while (--num >= 0) {
            Button playerBtn = new Button(this);
            playerBtn.setText(players.get(num));
            playerBtn.setLayoutParams(btnParams);

            playerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DeletePlayer.this, DeletePlayerConfirmation.class);
                    intent.putExtra("username", ((Button) v).getText().toString());
                    startActivity(intent);
                }
            });

            layout.addView(playerBtn);
        }
    }
}
