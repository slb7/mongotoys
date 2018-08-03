import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.BSONDecoder;
import org.bson.BsonDocument;
import org.bson.Document;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class aamongoApplication {
    //    static void findAnother(){
//        coll.find().forEach((d)->{},(d,t)->{
//            if(++count % 1000 == 0){
//                System.out.println(count);
//            }
//            findAnother();
//        });
//    }
    static int count = 0;
    static int batchSize = 10000;

    static MongoCollection <Document> coll;

    static void addMore(Void d, final Throwable t) {
        System.out.println("inserted " + count);
        if(null == d) {
//            System.out.println("d is null");
        }
        if(count < 1000000) addDocuments();
        else {
            System.out.println("done inserting");
            findDoc();
        }
    }
    static Random rand = new Random();
    static int foundCount = 0;
    static BsonDocument proj = BsonDocument.parse("{_id:0,name:1,y:1}");
    static void findDoc() {
        String str = "{name:'x" + rand.nextInt(1000000) + "'}";
        BsonDocument doc = BsonDocument.parse(str);
        coll.find(doc).projection(proj).forEach((d)->{},(d,t)->{
            if(++foundCount % 10000 == 0){
                System.out.println("found " + foundCount);
            }
            findDoc();
        });
    }
    static void addDocuments(){
        ArrayList <Document> docs = new ArrayList <>();
        for(int i = 0;i<batchSize;i++){
            String d = "{name:'x" + (count + i) + "', y:'" + (count + i) +"realllllllllllyLooooooooooooonnnnnnnnnnnnnnnnnnnnnnnggggggggggString'}";
            //System.out.println(d);
            docs.add(Document.parse(d));
        }
        count += batchSize;
        coll.insertMany(docs, aamongoApplication::addMore);
    }
    public static void main(String[] args) {

        System.out.println("hw");
        MongoClient client = MongoClients.create();
        MongoDatabase db = client.getDatabase("temp");
        coll = db.getCollection("c1");
        coll.deleteMany(BsonDocument.parse("{}"),(d,t)->{
            System.out.println("deleted " + d.getDeletedCount());
            addDocuments();
        });
        try {
            System.in.read();
        } catch (IOException e) {
        }
        //findAnother();
//        coll.find().projection(BsonDocument.parse("{_id:0}")).forEach((d)->{
//            System.out.println(d.toJson());
//        },(d,t)->{
//            if(t == null){
//                System.out.println("done");
//            }
//            else System.out.println(t.getMessage());
//        });
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }
}
