package project6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class BookmarkList {
   
   String bookmarkFileName; // 파일 경로
   
   // Bookmark를 저장하는 ArrayList
   public ArrayList<Bookmark> bArray = new ArrayList<Bookmark>();
   
   public BookmarkList(String bookmarkFileName) {
      this.bookmarkFileName = bookmarkFileName;
      
      try{
            //파일 객체 생성
            File file = new File(bookmarkFileName);
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            
            String line = "";
            while((line = bufReader.readLine()) != null){
                
                String[] tempArray = line.split(";|,| ");
                String[] strArray = new String[5];
                
                int flag = 1;
                if (tempArray[0].equals("//"))
                   flag = 0;
                if (flag == 1) {
                   
                   int strIdx=0;
                   
                   // null이 아닌 값들만 총 5개만 골라서 strArray에 삽입
                   for (int i=0; i<tempArray.length; i++) {                      
                      if(tempArray[i]!=null && !tempArray[i].isEmpty()) {
                         strArray[strIdx]=tempArray[i];
                         strIdx++;
                      }                                  
                   }
                   
                   // Array 5개 중에 null을 공백으로 바꿔주기 
                   for (int i=0; i<strArray.length; i++) {
                	  if (strArray[i]==null)
                		  strArray[i]="";
                   }                     
                    
                    // Bookmark 객체 생성
                    bArray.add(new Bookmark(strArray[0], strArray[1], strArray[2], strArray[3], strArray[4]));
                    
                }
            }           
            bufReader.close();
            
        }catch (FileNotFoundException e) {
           System.out.println("Unknwon BookmarkList data File");
        }catch(IOException e){
            System.out.println(e);
        }
   }
   
   // methods
   public int numBookmarks() {
	   return this.bArray.size();
   }
   
   public Bookmark getBookmark(int i) {
	   return bArray.get(i);
   }
   
   public void mergeByGroup() {	   
	   for(int i=0; i<numBookmarks(); i++) {
		   for (int j=i+1; j<numBookmarks(); j++) {
			   if(!(bArray.get(i).getGroup().equals("")) && !(bArray.get(j).getGroup().equals(""))) {
				   if((bArray.get(i).getGroup()).equals(bArray.get(j).getGroup())) {
					   bArray.add(i+1, bArray.get(j));
					   bArray.remove(j+1);
				   }
			   }
		   }
	   }
   }
   
   public void fileWriter() {
	   try {
		   // BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
		   BufferedWriter fw = new BufferedWriter(new FileWriter(bookmarkFileName, false));
					
			// 파일안에 문자열 쓰기
			for (int i=0; i<this.numBookmarks(); i++) {
				fw.write(bArray.get(i).getName()+", "+bArray.get(i).getCtime()+", "+bArray.get(i).getUrl()+", "+bArray.get(i).getGroup()+", "+bArray.get(i).getNote());
				fw.write("\r\n");
			}
			fw.flush();
			// 객체 닫기
			fw.close(); 
	   }catch(Exception e){
			e.printStackTrace();
		}
   }
   
   public void add(Bookmark bookmark) {
	   this.bArray.add(bookmark);
   }
   
   public void delete(int idx) {
	   this.bArray.remove(idx);
   }
}
