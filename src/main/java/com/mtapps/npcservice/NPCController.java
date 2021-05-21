package com.mtapps.npcservice;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

@Controller
@CrossOrigin
public class NPCController {

//	@Autowired
//	private MongoClientFactoryBean mongo;

	@Autowired
	private MongoClient mongoClient;

	/*
	 * public NPCData getNPCNew(@RequestParam(name = "name", required = false,
	 * defaultValue = "Stranger") String name) {
	 * 
	 * NPCData npc = null;
	 * 
	 * MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "TFT");
	 * NPCData newNPC = new NPCData(); newNPC.setName("Granite");
	 * newNPC.setST("15"); mongoOps.insert(newNPC);
	 * 
	 * npc = mongoOps.findOne(new Query(where("name").is("Granite")),
	 * NPCData.class);
	 * 
	 * System.out.println("npc --> " + npc); System.out.println("got one " +
	 * npc.getName()); System.out.println("st --> " + npc.getST());
	 * System.out.println("dx --> " + npc.getDX()); System.out.println("iq --> " +
	 * npc.getIQ()); System.out.println("ma --> " + npc.getMA());
	 * 
	 * return npc; }
	 */

	@GetMapping(value = "/getNPC", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ArrayList<NPCData> getNPC(@RequestParam(name = "name", required = false, defaultValue = "*") String name) {

		ArrayList<NPCData> npcs = null;
		// MongoClient mongoClient;

		try {

			MongoOperations mongoOps = new MongoTemplate( mongoClient, "TFT");
			Query qry = new Query();
			if( !name.equals("*") ) {
				qry = new Query(where("name").is( name ));
			}
			
			npcs = (ArrayList<NPCData>) mongoOps.find( qry, NPCData.class, "NPC" );

			for (NPCData npc : npcs) {
				System.out.println("npc --> " + npc);
				System.out.println("got one " + npc.getName());
				System.out.println("st --> " + npc.getST());
				System.out.println("dx --> " + npc.getDX());
				System.out.println("iq --> " + npc.getIQ());
				System.out.println("ma --> " + npc.getMA());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return npcs;
	}
	
	@GetMapping(value = "/getNPCNames", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ArrayList<NPCName> getNPCNames(HttpServletResponse resp) {

		ArrayList<NPCName> names = new ArrayList<NPCName>();
		System.out.println( "running with cors allowed -- addHeader");
		resp.setHeader("Content-Type", "application/text");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		
		try {
			MongoOperations mongoOps = new MongoTemplate( mongoClient, "TFT");
			Query qry = new Query();
			qry.fields().include("name").exclude("_id");
			
			names = (ArrayList<NPCName>) mongoOps.find( qry, NPCName.class, "NPC" );
			
			for (NPCName name : names) {
				System.out.println("name --> " + name.getName());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return names;
	}

	@GetMapping(value = "/getDB", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getDB() {
		String dbName = "unknown";
		try {

			MongoClient mongoClient = MongoClients.create();
			MongoDatabase db = mongoClient.getDatabase("TFT");
			Document qry = new Document("name", "Faran");
			MongoIterable<Document> myIter = db.getCollection("NPC").find(qry);
			System.out.println("DB name --> " + db.getName());

			MongoCursor<Document> cursor = myIter.iterator();
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				System.out.println("got one " + doc.get("name"));
			}

			mongoClient.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return dbName;
	}
}
