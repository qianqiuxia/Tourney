package edu.gatech.seclass.tourneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeletePlayerConfirmation extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_confirmation);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        TextView message = (TextView) findViewById(R.id.textView9);
        message.setText("\nDelete " + username + "?\n");

        Button deleteCancel = (Button) findViewById(R.id.delete_cancel);
        deleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button deleteConfirm = (Button) findViewById(R.id.delete_confirm);
        deleteConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(DeletePlayerConfirmation.this);
                db.deletePlayer(username);
                finish();
            }
        });


    }
}
