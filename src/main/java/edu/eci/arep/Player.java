package edu.eci.arep;

public class Player {



    private String id;
    private int x;
    private int y;


    public Player(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

}
