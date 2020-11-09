import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elements.Board;
import elements.Player;
import spark.Request;
import spark.Response;


import javax.swing.*;
import java.util.Random;

import static spark.Spark.*;

public class SimpleAppGame {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Board board = new Board();

    private static Random random = new Random();
    public static void main(String[] args){
        port(getPort());
        post("/players", (req, resp) -> createPlayer(req, resp));
        post("/delete", (req, resp) -> deletePlayers(req, resp));
        post("/move/:id", (req, resp) -> movePlayer(req, resp));
    }

    private static String createPlayer(Request req, Response resp) throws JsonProcessingException {
        resp.type("application/json");
        resp.status(200);
        int x = random.nextInt(Board.WIDTH);
        int y = random.nextInt(Board.HEIGHT);
        Player addes = board.createPlayer(x,y);
        return "{id : "+ addes.getId() +" } ";
    }

    private static String deletePlayers(Request req, Response resp){
        resp.type("application/json");
        resp.status(200);
        board.deletePlayers();
        return "{}";
    }

    public static String movePlayer(Request req, Response resp) throws JsonProcessingException {
        resp.type("application/json");
        resp.status(200);
        String id = req.params(":id");
        String direction = req.queryParams("direction");
        System.out.println(direction + id);
        if (direction != null && id != null) {
            Player ans = board.movePlayer(id, direction);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ans);
        }
        return "{}";
    }

    /**
     * Cambia el puerto de respuesta, dependiendo del entorno de despliegue
     * @return Puerto a trabajar
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
