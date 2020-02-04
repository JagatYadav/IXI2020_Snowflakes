package MLModel;

import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseMasterURL {

	
	
	  ParseMasterURL() {}
	  
	  public static List<String>extractLinks(String url) throws IOException { final
	  ArrayList<String> result = new ArrayList<String>();
	  
	  Document doc = Jsoup.connect(url).get();
	  
	  Elements links = doc.select("a[href]"); Elements media = doc.select("[src]");
	  Elements imports = doc.select("link[href]");
	  
	
	  for (Element link : links) { result.add(link.attr("abs:href")); }
	  for (Element src : media) { result.add(src.attr("abs:src")); }
	  for (Element link : imports) { result.add(link.attr("abs:href"));
	  } return result; }
	 
}
