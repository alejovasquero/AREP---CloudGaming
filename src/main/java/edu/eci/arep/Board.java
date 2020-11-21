package edu.eci.arep;

import edu.eci.arep.threads.BoardThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Board {

    public static int WIDTH =100000;
    public static int HEIGHT =100000;
    private AtomicInteger numberOfPlayers = new AtomicInteger(0);

    private MongoPlayerDAO mongoPlayerDAO = new MongoPlayerDAO();


    public Player createPlayer(int x, int y){
        Player added = new Player(x,y);
        mongoPlayerDAO.savePlayer(added);
        return added;
    }

    public void deletePlayers(){
        mongoPlayerDAO.deletePlayers();
    }

    public Player movePlayer(String id, String direction){
        ArrayList<Thread> moves = new ArrayList<Thread>();
        Player a = mongoPlayerDAO.getPlayer(id);
        moves.add(new BoardThread(a, Directions.down));
        moves.add(new BoardThread(a, Directions.up));
        moves.add(new BoardThread(a, Directions.right));
        moves.add(new BoardThread(a, Directions.left));
        moves.add(new BoardThread(a, Directions.down));
        moves.add(new BoardThread(a, Directions.up));
        moves.add(new BoardThread(a, Directions.right));
        moves.add(new BoardThread(a, Directions.left));
        for (Thread m: moves){
            m.start();
        }
        for (Thread m: moves){
            try {
                m.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(a!=null){
            if(direction.equals(Directions.down.toString())){
                a.setY(a.getY() - 1);
            } else if(direction.equals(Directions.up.toString())){
                a.setY(a.getY() + 1);
            } else if(direction.equals(Directions.right.toString())){
                a.setX(a.getX() + 1);
            } else if(direction.equals(Directions.left.toString())){
                a.setX(a.getX() - 1);
            }
            mongoPlayerDAO.updatePlayer(a);
        }
        return a;
    }


}
