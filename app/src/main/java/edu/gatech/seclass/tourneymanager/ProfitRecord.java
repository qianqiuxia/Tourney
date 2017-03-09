package edu.gatech.seclass.tourneymanager;

/**
 * Created by edchen on 3/2/17.
 */

public class ProfitRecord {
    private String date;
    private int profit;

    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }

    public void setProfit(int profit){
        this.profit = profit;
    }
    public int getProfit(){
        return this.profit;
    }
}
