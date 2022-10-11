package project6;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Bookmark {
	
	private String name;
	private String ctime;
	private String url;
	private String group;
	private String note;
   
   // Constructor
   public Bookmark() {
	   
   }
   public Bookmark (String name, String ctime, String url, String group, String note) {
      this.name = name;
      this.ctime = ctime;
      this.url = url;
      this.group = group;
      this.note = note; 
      
      // ctime과 url 체크
      this.validationDate(ctime);
      this.isURL(url);
   }
   
   // Methods
   // 출력 methods
   public void print() {
      System.out.println(name+","+ctime+","+url+","+group+","+note);
   }
 
   public void isURL(String url) {
	   if (url.equals("")) {
		   System.out.print("MalformedURLException: wrong URL - No URL ; invalid Bookmark info line: ");
		   this.print();
	   }   		
   }
   // Date format check method
   public void validationDate(String checkDate) {
	   try {
	    	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
	    	  
	    	  dateFormat.setLenient(false);
	          dateFormat.parse(checkDate);	                    
	      } catch(ParseException e) {
	    	  System.out.print("Date Format Error -> No Created Time invalid Bookmark info line: ");
	    	  this.print();
	      }
   }
   
   public String getName() {
	   return name;
   }
   public String getCtime() {
	   return ctime;
   }
   public String getUrl() {
	   return url;
   }
   public String getGroup() {
	   return group;
   }
   public String getNote() {
	   return note;
   }
}