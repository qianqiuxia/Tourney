package edu.gatech.seclass.tourneymanager;

import java.io.Serializable;

/**
 * Created by edchen on 2/27/17.
 */

public class Player implements Serializable {
    private String username;
    private String name;
    private String phoneNumber;
    private DeckChoice deck_choice;

    public enum DeckChoice {
        Engineer, Buzz, Sideways, Wreck, T, RAT
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setDeckChoice(DeckChoice choice){
        this.deck_choice = choice;
    }

    public DeckChoice getDeckChoice(){
        return this.deck_choice;
    }
}
