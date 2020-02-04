package MLModel;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AnalyticalModel {
	

  public final static void main(String[] args) throws Exception{
	  
	  Logger.getLogger("org").setLevel(Level.ALL);
		Logger.getLogger("akka").setLevel(Level.ALL);

		
		// Create a SparkSession
		SparkSession spark = SparkSession.builder().appName("KNN").master("local").getOrCreate();
	  	  
		ParseMasterURL pmURL = new ParseMasterURL();
    String site = "https://opportunitydesk.org/2020/02/03/apsa-research-development-group-2020/#respond";
    
    List<String> links = ParseMasterURL.extractLinks(site);
    
	 String[] str = {  "Deadline","Eligibility"}; 
	AutoScrape scrap = new AutoScrape();
	scrap.search(site, str);
	    
    for (String link : links) {
    	scrap.search(link, str);
      System.out.println(link);
    }
	
	
	// Loads data
	Dataset<Row> rawUserOpportunities = spark.read().option("header", "true").csv("Data/opportunitiesdata.csv");
	
	rawUserOpportunities.show();
	// Ignore rows having null values
			Dataset<Row> datasetClean = rawUserOpportunities.na().drop();
			datasetClean.show();
			
	
  }
}