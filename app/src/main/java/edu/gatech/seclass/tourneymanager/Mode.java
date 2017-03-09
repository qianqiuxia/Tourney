package edu.gatech.seclass.tourneymanager;

/**
 * Created by edchen on 3/2/17.
 */

public final class Mode {
    private static final Mode instance = new Mode();

    private Mode(){}

    public static Mode getInstance(){
        return instance;
    }

    private MODES mode;

    public void setMode(MODES m){
        this.mode = m;
    }
    public MODES getMode(){
        return this.mode;
    }
    public enum MODES {
        manager, player
    }
}
