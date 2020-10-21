package MongoDBtest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;



public class MongoDbtest_02 {
	

	private static final Class<Object> Resultset = null;
	private static volatile MongoClient mongoConnection;
	 MongoClient mongo;
	
	
	@BeforeTest
	public void setdbconnection()
	{
		MongoCredential journaldevAuth = MongoCredential.createScramSha256Credential("admin", "admin", "password".toCharArray());
		List<MongoCredential> auths = new ArrayList<MongoCredential>();
		auths.add(journaldevAuth);

		ServerAddress serverAddress = new ServerAddress("tarl.qait.com", 27017);
		 mongo = new MongoClient(serverAddress, auths);

	      List<String> dbs = mongo.getDatabaseNames();
	      System.out.println(dbs); 
	      System.out.println("Connected to the database successfully");  
	}
	
	@Test
	public void test_02() 
	{
		  // Connecting with database
	      MongoDatabase database = mongo.getDatabase("mydb");
	      
	      for (String name : database.listCollectionNames()) { 
	          System.out.println(name); 
	       } 
	      
	      //Create new collection
	      MongoCollection<Document> collection = database.getCollection("FileData");
	      System.out.println("about to");
	      
	     //Creating a JsonParser Object
	     JSONParser jsonParser = new JSONParser();
	     JSONArray jsonArray = null;

	     
	    
	     //Prasing the json file content
	     
	     try {
	    	 System.out.println("i'm in try block");
	         jsonArray = (JSONArray) jsonParser.parse(new FileReader("C://Users//vaishali//eclipse-workspace//Mongodbtest//src//test//resources//Data//Sample.json"));
	         System.out.println("i'hv parsed the data");
	     
	       org.json.JSONArray value = new org.json.JSONArray( jsonArray.toJSONString());
	       ArrayList<Document> list = new ArrayList<Document>();
	     
	       // inserting parsed json data into document
	      for(int i=0;i<jsonArray.size();i++)
	      {
	    	  System.out.println("Inside loop");
	    	  
	    	  Document doc1 = new Document("ID",value.getJSONObject(i).getString("ID"))
	    			  .append("First_Name",value.getJSONObject(i).getString("First_Name"))
	    			  .append("Last_Name",value.getJSONObject(i).getString("Last_Name"))
		    		  .append("Date_Of_Birth",value.getJSONObject(i).getString("Date_Of_Birth"))
		    		  .append("Place_Of_Birth",value.getJSONObject(i).getString("Place_Of_Birth"))
		    		  .append("Country",value.getJSONObject(i).getString("Country"));
	    	
	    	list.add(doc1);
	    
	      }
	      
	      // insert data into collection
	      collection.insertMany(list);
	      System.out.println("successfully inserted");
	     
	     }
	      catch (FileNotFoundException e) {
	            e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (ParseException e) {
	         e.printStackTrace();
	      }
	     
	     System.out.println("Outside the loop");
	     org.json.JSONArray value = new org.json.JSONArray( jsonArray.toJSONString());
	     
	     // Validating inserted data 
		String id = value.getJSONObject(0).getString("ID");
		String First_name = value.getJSONObject(0).getString("First_Name");
	     
		Assert.assertEquals(id,"1");
	    Assert.assertEquals(First_name,"Shikhar");
	     
	    
	}

}
