package edu.eci.arep;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Projections.excludeId;

public class MongoPlayerDAO {


    private MongoClientURI uri;
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> coll;

    private static ObjectMapper JSON_MAPPER = new ObjectMapper();

    {
        String conn = "mongodb://gameUser:gamePassword@cluster0-shard-00-00.8odye.mongodb.net:27017,cluster0-shard-00-01.8odye.mongodb.net:27017,cluster0-shard-00-02.8odye.mongodb.net:27017/players?ssl=true&replicaSet=atlas-11wodn-shard-0&authSource=admin&retryWrites=true&w=majority";

        mongoClient = MongoClients.create(conn);
        db = mongoClient.getDatabase("players");
        coll = db.getCollection("players");
    }

    public void savePlayer(Player a){
        Document insert = new Document("x", a.getX()).append("y", a.getY());
        coll.insertOne(insert);
        ObjectId id = (ObjectId)insert.get( "_id" );
        a.setId(id.toString());
        System.out.println(id.toString());
    }

    public void updatePlayer(Player p){
        BasicDBObject query = new BasicDBObject();
        query.put("_id", p.getId());
        System.out.println("HERE " + p.getId());

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("x", p.getX());
        newDocument.put("y", p.getY());

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);
        coll.updateOne(
                new BasicDBObject("_id", new ObjectId(p.getId())),
                new BasicDBObject("$set", newDocument)
        );
    }

    public void deletePlayers(){
        BasicDBObject document = new BasicDBObject();
        coll.deleteMany(document);
    }

    public Player getPlayer(String id)  {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", new ObjectId(id));
        MongoCursor<Document> cursor = coll.find(whereQuery).projection(excludeId()).limit(1).iterator();
        String json = "";
        while(cursor.hasNext()) {
            json = cursor.next().toJson();
        }
        Player ans = null;
        try {
            ans = JSON_MAPPER.readValue(json, Player.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ans.setId(id);
        return ans;
    }
}
