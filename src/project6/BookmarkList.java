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
   
   String bookmarkFileName; // ���� ���
   
   // Bookmark�� �����ϴ� ArrayList
   public ArrayList<Bookmark> bArray = new ArrayList<Bookmark>();
   
   public BookmarkList(String bookmarkFileName) {
      this.bookmarkFileName = bookmarkFileName;
      
      try{
            //���� ��ü ����
            File file = new File(bookmarkFileName);
            //�Է� ��Ʈ�� ����
            FileReader filereader = new FileReader(file);
            //�Է� ���� ����
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
                   
                   // null�� �ƴ� ���鸸 �� 5���� ��� strArray�� ����
                   for (int i=0; i<tempArray.length; i++) {                      
                      if(tempArray[i]!=null && !tempArray[i].isEmpty()) {
                         strArray[strIdx]=tempArray[i];
                         strIdx++;
                      }                                  
                   }
                   
                   // Array 5�� �߿� null�� �������� �ٲ��ֱ� 
                   for (int i=0; i<strArray.length; i++) {
                	  if (strArray[i]==null)
                		  strArray[i]="";
                   }                     
                    
                    // Bookmark ��ü ����
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
		   // BufferedWriter �� FileWriter�� �����Ͽ� ��� (�ӵ� ���)
		   BufferedWriter fw = new BufferedWriter(new FileWriter(bookmarkFileName, false));
					
			// ���Ͼȿ� ���ڿ� ����
			for (int i=0; i<this.numBookmarks(); i++) {
				fw.write(bArray.get(i).getName()+", "+bArray.get(i).getCtime()+", "+bArray.get(i).getUrl()+", "+bArray.get(i).getGroup()+", "+bArray.get(i).getNote());
				fw.write("\r\n");
			}
			fw.flush();
			// ��ü �ݱ�
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
