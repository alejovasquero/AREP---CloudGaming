package elements;

import exceptions.GameException;
import persistence.MongoPlayerDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Board {

    public static int WIDTH =800;
    public static int HEIGHT =800;
    private AtomicInteger numberOfPlayers = new AtomicInteger(0);
    private HashMap<String, Player> players = new HashMap<String, Player>();

    private MongoPlayerDAO mongoPlayerDAO = new MongoPlayerDAO();


    public Player createPlayer(int x, int y){
        Player added = new Player(x,y);
        mongoPlayerDAO.savePlayer(added);
        players.put(added.getId(), added);
        return added;
    }

    public void deletePlayers(){
        mongoPlayerDAO.deletePlayers();
    }

    public Player movePlayer(String id, String direction){
        Player a = players.get(id);
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
