package edu.gatech.seclass.tourneymanager;

/**
 * Created by edchen on 3/2/17.
 */

public class PrizeRecord {
    private String playerUsername;
    private int prizeAmount;
    private int place;
    private String date;

    PrizeRecord(String username, int amount){
        this.setPlayer(username);
        this.setAmount(amount);
    }

    PrizeRecord(String username, int amount, String date){
        this.setPlayer(username);
        this.setAmount(amount);
        this.setDate(date);
    }

    public void setPlayer(String p){
        this.playerUsername = p;
    }
    public String getPlayer(){
        return this.playerUsername;
    }

    public void setAmount(int amount){
        this.prizeAmount = amount;
    }
    public int getAmount(){
        return this.prizeAmount;
    }

    public void setPlace(int place){
        this.place = place;
    }
    public int getPlace(){
        return this.place;
    }

    public void setDate(String d){
        this.date = d;
    }
    public String getDate(){
        return this.date;
    }
}
