package MongoDBtest;

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDbTest {

	

		private static final Class<Object> Resultset = null;
		private static volatile MongoClient mongoConnection;
		 MongoClient mongo;
		
		 
		@BeforeTest
		public void setconnection(){
			
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
		public void Mongotest() {  
		      
		      // Connecting with database
		      MongoDatabase database = mongo.getDatabase("mydb"); 
	  
		      for (String name : database.listCollectionNames()) { 
		          System.out.println(name); 
		       } 
		      
		      //Create new collection
		      MongoCollection<Document> collection = database.getCollection("Details");
		    
		      Document doc1 = new Document("Name","Vaishali").append("Age",22).append("City","Baraut")
		    		  .append("College","AKGEC").append("Salary",28000).append("Gender","F");
		      Document doc2 = new Document("Name","Voila Davis").append("Age",45).append("City","NY")
		    		  .append("College","Harvard").append("Salary",76990).append("Gender","F");
		      Document doc3 = new Document("Name","Joe Biden").append("Age",75).append("City","NY")
		    		  .append("College","Harvard").append("Salary",10000000).append("Gender","M");
		      Document doc4 = new Document("Name","Kamla Harris").append("Age",40).append("City","California")
		    		  .append("College","Cambridge").append("Salary",987654).append("Gender","F");
		      
		      //Inserting in the created document
		      
		        ArrayList<Document> list = new ArrayList<Document>();
		        list.add(doc1);
		        list.add(doc2);
		        list.add(doc3);
		        list.add(doc4);
		        
		        collection.insertMany(list);
		        
		        
		        System.out.println("Document inserted");
		        
		       // View the inserted data
		        
		       String name;
		       ArrayList arr = new ArrayList();
		       
                FindIterable<Document> data = collection.find();
                MongoCursor<Document> it = data.iterator();
                while(it.hasNext())
                {
                	// to view the name from the document
                	 name = it.next().get("Name").toString();
                     arr.add(name);
                     System.out.println(name);
           
                }
                
              // Validations  
                
              Assert.assertEquals(collection.count(),20);
              Assert.assertEquals(arr.get(0),"Vaishali");
              Assert.assertEquals(arr.get(2),"Joe Biden");
              
         
		   } 
	}