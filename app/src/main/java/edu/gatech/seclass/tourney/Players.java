package edu.gatech.seclass.tourney;


public class Players {

    private String _name;
    private String _username;
    private int _phonenumber;
    private int _deckchoice;
    private int _id;

    public Players(){

    }

    public Players(String name) {
        this._name = name;
    }

    public void set_username(String username) {
        this._username = username;
    }

    public void set_phonenumber(int phonenumber) {
        this._phonenumber = phonenumber;
    }

    public void set_deckchoice(int deckchoice) {
        this._deckchoice = deckchoice;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public String get_name() {
        return _name;
    }

    public String get_username() {
        return _username;
    }

    public int get_phonenumber() {
        return _phonenumber;
    }

    public int get_deckchoice() {
        return _deckchoice;
    }

    public int get_id() {
        return _id;
    }

}
