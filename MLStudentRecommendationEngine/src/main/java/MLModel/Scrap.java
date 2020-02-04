package MLModel;


import java.io.IOException;

import java.util.*;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrap
{
    // use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>();
    private Document htmlDocument;


    //It makes an HTTP request, checks the response
    public boolean crawl(String url)
    {
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            
            System.out.println(htmlDocument);
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() == 200) 
            {
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                this.links.add(link.absUrl("href"));
                System.out.println(link);
            }
            return true;
        }
        catch(IOException ioe)
        {
            // not successful in our HTTP request
            return false;
        }
    }


    // Performs a search on the body of on the HTML document that is retrieved

    public boolean searchForWord(String searchWord)
    {
        
        if(this.htmlDocument == null)
        {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return false;
        }
        System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = this.htmlDocument.body().text();
        
        //return if word was found
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
    }


    public List<String> getLinks()
    {
        return this.links;
    }

}

