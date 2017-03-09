package edu.gatech.seclass.tourneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewMatches extends AppCompatActivity implements View.OnClickListener{

    static final int SELECT_WINNER = 1;

    private ArrayList<Match> matchList = new ArrayList<Match>();
    private int endMatchIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_matches);

        Mode currentMode = Mode.getInstance();
        TextView titleText = (TextView) findViewById(R.id.textMatchesListTitle);
        if (currentMode.getMode() == Mode.MODES.manager) {
            titleText.setText("Manager Mode");
        } else {
            titleText.setText("Player Mode");
        }
        addButtons();
    }

    @Override
    public void onClick(View v) {
        String btnTagValue = v.getTag().toString();
        if (btnTagValue == "end_tournament"){
            Intent intent = new Intent(ViewMatches.this, EndTournamentConfirmation.class);
            startActivity(intent);
        } else {
            int matchIndex = Integer.valueOf(v.getTag().toString());
            Log.d("matchIndex", Integer.toString(matchIndex));
            Match m = matchList.get(matchIndex);
            Match.MATCHSTATUS matchstatus = m.getStatus();
            if (matchstatus == Match.MATCHSTATUS.ready) {
                m.startMatch();
                ((Button) v).setText(m.getMatchDescription());
            }
            if (matchstatus == Match.MATCHSTATUS.started) {
                Intent intent = new Intent(ViewMatches.this, EndMatch.class);
                intent.putExtra("player1", m.getPlayerUsername(1));
                intent.putExtra("player2", m.getPlayerUsername(2));
                endMatchIndex = matchIndex;
                startActivityForResult(intent, SELECT_WINNER);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SELECT_WINNER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String winnerUsername = data.getStringExtra("winner");
                Match m = matchList.get(endMatchIndex);
                if (winnerUsername.equals(m.getPlayerUsername(1))){
                    m.endMatch(m.getPlayerUsername(1), m.getPlayerUsername(2));
                } else {
                    m.endMatch(m.getPlayerUsername(2), m.getPlayerUsername(1));
                }
                LinearLayout layout = (LinearLayout) findViewById(R.id.match_list);
                final int childCount = layout.getChildCount();
                for (int i = 0; i < childCount; i++){
                    Button btn = (Button) layout.getChildAt(i);
                    if (endMatchIndex == Integer.valueOf(btn.getTag().toString())){
                        btn.setText(m.getMatchDescription());
                        btn.setEnabled(false);
                        break;
                    }
                }
                // TODO figure out when to refresh list of matches
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        Tournament currentTournament = Tournament.getInstance();
        if (currentTournament.getStatus() != Tournament.Status.ONGOING){
            finish();
        }

        // TODO handle updating match list better
        addButtons();
    }


    private void addButtons(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.match_list);
        layout.removeAllViews();
        matchList.clear();

        Tournament currentTournament = Tournament.getInstance();
        fillLayoutWithMatches(layout, currentTournament.getFutureMatches());
        fillLayoutWithMatches(layout, currentTournament.getOngoingMatches());
        fillLayoutWithMatches(layout, currentTournament.getCompletedMatches());

        Mode currentMode = Mode.getInstance();
        if (currentMode.getMode() == Mode.MODES.manager) {
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button endTournamentBtn = new Button(this);
            endTournamentBtn.setTag("end_tournament");
            endTournamentBtn.setText("end tournament");
            endTournamentBtn.setLayoutParams(btnParams);
            endTournamentBtn.setOnClickListener(this);
            layout.addView(endTournamentBtn);
        }
    }

    private void fillLayoutWithMatches(LinearLayout layout, ArrayList<Match> matches){

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Mode currentMode = Mode.getInstance();
        int num = matches.size();
        while (--num >= 0) {
            Button matchBtn = new Button(this);
            Match m = matches.get(num);
            matchBtn.setText(m.getMatchDescription());
            matchBtn.setTag(matchList.size());
            matchList.add(m);
            matchBtn.setLayoutParams(btnParams);
            if (currentMode.getMode() == Mode.MODES.manager &&
                    m.getStatus() != Match.MATCHSTATUS.finished) {
                matchBtn.setOnClickListener(this);
            }else {
                matchBtn.setEnabled(false);
            }
            layout.addView(matchBtn);
        }
    }
}
