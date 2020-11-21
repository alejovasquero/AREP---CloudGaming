package edu.eci.arep.threads;

import edu.eci.arep.Board;
import edu.eci.arep.Directions;
import edu.eci.arep.Player;

import javax.swing.*;

public class BoardThread extends Thread{

    private Player player;
    private Directions direction;


    public BoardThread(Player player, Directions direction){
        this.player = player;
        this.direction = direction;
    }

    @Override
    public void run() {
        int steps = 0;
        int x = player.getX();
        int y = player.getY();
        while (x >=0 && x <= Board.WIDTH && y <= Board.HEIGHT && y >= 0){
            if(direction.equals(Directions.down)){
                y--;
            } else if(direction.equals(Directions.up)){
                y++;
            } else if(direction.equals(Directions.right)){
                x++;
            } else {
                x--;
            }
            steps++;
        }
        System.out.println("TOTAL STEPS ´= " + steps);
    }


}
