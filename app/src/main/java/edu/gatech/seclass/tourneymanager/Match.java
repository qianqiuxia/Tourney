package edu.gatech.seclass.tourneymanager;

/**
 * Created by edchen on 3/2/17.
 */

public class Match {
    private MATCHSTATUS status;
    private String[] playerPair = new String[2];;
    private String loser;
    private String winner;

    public enum MATCHSTATUS {
        ready, started, finished
    }

    Match(String p1, String p2){
        this.setPlayers(p1, p2);
    }

    public MATCHSTATUS getStatus() {
        return this.status;
    }

    public void setStatus(MATCHSTATUS status) {
        this.status = status;
    }

    public void setPlayers(String p1, String p2){
        playerPair[0] = p1;
        playerPair[1] = p2;
    }
    public String[] getPlayers(){
        return this.playerPair;
    }
    public int getNumPlayers(){
        return this.getPlayers().length;
    }
    public void setWinner(String winner){
        this.winner = winner;
    }
    public String getWinner(){
        return this.winner;
    }

    public void setLoser(String loser){
        this.loser = loser;
    }
    public String getLoser(){
        return this.loser;
    }

    public String getPlayerUsername(int i){
        if (i == 1){
            return this.playerPair[0];
        }
        if (i == 2){
            return this.playerPair[1];
        }
        return "";
    }

    public String getMatchDescription(){
        StringBuilder result = new StringBuilder();
        result.append(this.playerPair[0]);
        result.append(" / ");
        result.append(this.playerPair[1]);
        if (getStatus() == MATCHSTATUS.ready) {
            result.append("\n(ready)");
        }
        if (getStatus() == MATCHSTATUS.started) {
            result.append("\n(in progress)");
        }
        if (getStatus() == MATCHSTATUS.finished){
                result.append("\n(");
                result.append(this.getWinner());
                result.append(" won)");
        }
        return result.toString();
    }

    public void startMatch(){
        this.setStatus(MATCHSTATUS.started);
        Tournament currentTournament = Tournament.getInstance();
        currentTournament.startMatch(this);
    }

    public void endMatch(String winner, String loser){
        this.setStatus(MATCHSTATUS.finished);
        this.setWinner(winner);
        this.setLoser(loser);
        Tournament currentTournament = Tournament.getInstance();
        currentTournament.endMatch(this);
    }
}
