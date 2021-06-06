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

import com.mtapps.service.ServiceResponse;

@Controller
@CrossOrigin
public class NPCController {

//	@Autowired
//	private MongoClientFactoryBean mongo;

	@Autowired
	private MongoClient mongoClient;

	@GetMapping(value = "/getNPC", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceResponse<ArrayList<NPCData>> getNPC(@RequestParam(name = "name", required = false, defaultValue = "*") String name) {

		ArrayList<NPCData> npcs = null;
		// MongoClient mongoClient;

		ServiceResponse<ArrayList<NPCData>> resp = new ServiceResponse(npcs, "");
		try {

			MongoOperations mongoOps = new MongoTemplate( mongoClient, "TFT");
			Query qry = new Query();
			if( !name.equals("*") ) {
				qry = new Query(where("name").is( name ));
			}
			qry.fields().include("name", "ST", "DX", "IQ", "MA").exclude("_id");
			
			npcs = (ArrayList<NPCData>) mongoOps.find( qry, NPCData.class, "NPC" );
			/*
			for (NPCData npc : npcs) {
				System.out.println("npc --> " + npc);
				System.out.println("got one " + npc.getName());
				System.out.println("st --> " + npc.getST());
				System.out.println("dx --> " + npc.getDX());
				System.out.println("iq --> " + npc.getIQ());
				System.out.println("ma --> " + npc.getMA());
			}
			*/
			resp.setData( npcs );
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return resp;
	}
	
	@GetMapping(value = "/getNPCNames", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "*")
	@ResponseBody
	public ServiceResponse<NPCName[]> getNPCNames(/* HttpServletResponse resp */) {


		ArrayList<NPCName> rawNames = new ArrayList<NPCName>();
		ArrayList<NPCName> returnValue = new ArrayList<NPCName>();
		
//		resp.setHeader("Content-Type", "application/text");
//		resp.setHeader("Access-Control-Allow-Origin", "*");
		
		try {
			MongoOperations mongoOps = new MongoTemplate( mongoClient, "TFT");
			Query qry = new Query();
			qry.fields().include("name").exclude("_id");
			
			rawNames = (ArrayList<NPCName>) mongoOps.find( qry, NPCName.class, "NPC" );
			/*
			for( int i = 0; i < rawNames.size(); i++ ) {
				returnValue.add( ) = rawNames.get(i);
			}
			*/

			/*
			for (NPCName name : names) {
				System.out.println("name --> " + name.getName());
			}
			*/
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		ServiceResponse<NPCName[]> respBody = new ServiceResponse(rawNames.toArray(), "");


		return respBody;
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
