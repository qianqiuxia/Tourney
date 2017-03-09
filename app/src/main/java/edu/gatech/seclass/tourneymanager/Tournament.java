package edu.gatech.seclass.tourneymanager;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by edchen on 3/2/17.
 */

public final class Tournament{
    private static final Tournament instance = new Tournament();

    private Status currentStatus;
    private int houseCut;
    private ArrayList<String> playerList;
    private int[] potentialPrizes;
    private ArrayList<Match> ongoingMatches;
    private ArrayList<Match> futureMatches;
    private ArrayList<Match> matchesForThisRound;
    private ArrayList<Match> completedMatches;
    private Match finalMatch;
    private Match semifinalMatch;

    private String date;

    public enum Status {
        NONE, ONGOING, COMPLETED
    }

    private Tournament(){
        // TODO read tournament variables from storage
        this.setStatus(Status.NONE);
        this.setHouseCut(0);
        playerList = null;
        potentialPrizes = null;
        date = null;
        this.ongoingMatches = new ArrayList<Match>();
        this.futureMatches = new ArrayList<Match>();
        this.completedMatches = new ArrayList<Match>();
        this.matchesForThisRound = new ArrayList<Match>();
    }

    public static Tournament getInstance(){
        return instance;
    }


    public void setStatus(Status s){
        this.currentStatus = s;
    }

    public Status getStatus(){
        return this.currentStatus;
    }

    public int getHouseCut(){
        return this.houseCut;
    }

    private void setHouseCut(int profit){
        this.houseCut = profit;
    }

    public void setPlayerList(ArrayList<String> entrants){
        this.playerList = entrants;
    }

    public void setPrizes(int[] prizes){
        this.potentialPrizes = prizes;
    }

    public int getPrize(int i){
        if (i == 0){
            return potentialPrizes[0];
        }
        if (i == 1){
            return potentialPrizes[1];
        }
        if (i == 2){
            return potentialPrizes[2];
        }
        return -1;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Match> getFutureMatches() {
        return this.futureMatches;
    }

    public ArrayList<Match> getOngoingMatches() {
        return this.ongoingMatches;
    }

    public ArrayList<Match> getCompletedMatches() {
        return this.completedMatches;
    }

    public void startTournament(int profit, int[] prizes, ArrayList<String> entrants){
        this.setHouseCut(profit);
        this.setPrizes(prizes);
        this.setPlayerList(entrants);
        this.createFutureMatches();
        this.setDate(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        this.setStatus(Status.ONGOING);
    }

    public void createFutureMatches(){
        // populate futureMatches
        // if there are still matches, do not do anything
        if (ongoingMatches.size() != 0 || futureMatches.size() != 0){
            return;
        }
        // no future matches, no ongoing matches, and no completed matches mean a new tournament,
        // use playerList to create matches
        if (completedMatches.size() == 0){
            // setup new tournament
            clearAllMatches();
            int numPlayers = playerList.size();
            for (int i = 0; i < numPlayers; i += 2){
                Match newMatch = new Match(playerList.get(i), playerList.get(i+1));
                newMatch.setStatus(Match.MATCHSTATUS.ready);
                futureMatches.add(newMatch);
                matchesForThisRound.add(newMatch);
            }
            return;
        }
        // no matches to play and completed matches, use matchesForThisRound to build future matches
        int numMatches = matchesForThisRound.size();
        if (numMatches == 2){
            finalMatch = new Match(matchesForThisRound.get(0).getWinner(),
                    matchesForThisRound.get(1).getWinner());
            finalMatch.setStatus(Match.MATCHSTATUS.ready);
            futureMatches.add(finalMatch);
            semifinalMatch = new Match(matchesForThisRound.get(0).getLoser(),
                    matchesForThisRound.get(1).getLoser());
            semifinalMatch.setStatus(Match.MATCHSTATUS.ready);
            futureMatches.add(semifinalMatch);
            matchesForThisRound.clear();
        } else {
            for (int i = 0; i < numMatches; i+=2){
                Match matchA = matchesForThisRound.get(0);
                Match matchB = matchesForThisRound.get(1);
                Match newMatch = new Match(matchA.getWinner(), matchB.getWinner());
                newMatch.setStatus(Match.MATCHSTATUS.ready);
                futureMatches.add(newMatch);
                matchesForThisRound.add(newMatch);
                matchesForThisRound.remove(matchA);
                matchesForThisRound.remove(matchB);
            }
        }
    }

    public void startMatch(Match m){
        // move match from future to ongoing
        this.ongoingMatches.add(m);
        this.futureMatches.remove(m);
    }

    public void endMatch(Match m){
        // move match from ongoing to completed
        this.completedMatches.add(m);
        this.ongoingMatches.remove(m);
        createFutureMatches();
    }

    public void endTournament(Context myContext) {
        if (matchesForThisRound.size() == 0) {
            DBHelper db = new DBHelper(myContext);
            db.recordPrizes(this.getDate(),
                    finalMatch.getWinner(), this.getPrize(0),
                    finalMatch.getLoser(), this.getPrize(1),
                    semifinalMatch.getWinner(), this.getPrize(2));
            db.recordProfit(this.getDate(), this.getHouseCut());
        }
        this.setStatus(Status.COMPLETED);
        this.setHouseCut(0);
        this.date = null;
        this.potentialPrizes = null;
        this.playerList = null;
        this.clearAllMatches();
    }

    private void clearAllMatches(){
        this.matchesForThisRound.clear();
        this.ongoingMatches.clear();
        this.futureMatches.clear();
        this.completedMatches.clear();
    }
}
