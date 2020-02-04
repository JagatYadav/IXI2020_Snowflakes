package MLModel;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class AutoScrape
{
  private static final int MAX_PAGES_TO_SEARCH = 1;
  private Set<String> pagesVisited = new HashSet<String>();
  private List<String> pagesToVisit = new LinkedList<String>();


 
  public void search(String url, String[] searchWords)
 
  {
	  for (String searchWord : searchWords) {
	  
	  while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
      {
          String currentUrl;
          Scrap srp = new Scrap();
          if(this.pagesToVisit.isEmpty())
          {
              currentUrl = url;
              this.pagesVisited.add(url);
          }
          else
          {
              currentUrl = this.nextUrl();
          }
          srp.crawl(currentUrl); 
          
          boolean success = srp.searchForWord(searchWord);
          if(success)
          {
              System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
              break;
          }
          this.pagesToVisit.addAll(srp.getLinks());
      } //End while loop
	  }//End for loop
      System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
  }//End of search
  
 


  private String nextUrl()
  {
      String nextUrl;
      do
      {
          nextUrl = this.pagesToVisit.remove(0);
      } while(this.pagesVisited.contains(nextUrl));
      this.pagesVisited.add(nextUrl);
      return nextUrl;
  }}
















