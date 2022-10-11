package project6;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BookmarkListPanel extends JPanel{
	BookmarkList bList;
	
	DefaultTableModel model; 
	JTable table;
	JScrollPane scrollPane;
	
	int numOfRow;
	
	ArrayList<String> groupList = new ArrayList<String>();
	ArrayList<Integer> groupListCounts = new ArrayList<Integer>();
	ArrayList<Integer> groupCheck = new ArrayList<Integer>();
	
	int rowOfContents = 0;
	
	int flag = 0;
	
	BookmarkListPanel(BookmarkList bList) {
		this.bList = bList;
		
		// BookmarkList에서 만들어진 Bookmark들을 출력
		bList.mergeByGroup();
		for (int i=0; i<bList.numBookmarks(); i++) {
			
			if (!bList.bArray.get(i).getGroup().equals("") && !groupList.contains(bList.bArray.get(i).getGroup())) {
				groupList.add(bList.bArray.get(i).getGroup());
				groupListCounts.add(0);
				groupCheck.add(0);
			}
			
			String groupOfI = bList.bArray.get(i).getGroup();
			int iOfGroupList = groupList.indexOf(bList.bArray.get(i).getGroup());
			
			if (!groupOfI.equals("") && groupListCounts.get(iOfGroupList)>=0) {
				groupListCounts.set(groupList.indexOf(bList.bArray.get(i).getGroup()), groupListCounts.get(iOfGroupList)+1);
			}
		}
		
		String[] headers = {"", "Group", "Name", "URL", "Created Time", "Memo"};
		String[][] contents = new String[bList.bArray.size()+groupList.size()][6];
		
		for (int i=0; i<bList.numBookmarks(); i++) {
			if(!bList.bArray.get(i).getGroup().equals("") && groupCheck.get(groupList.indexOf(bList.bArray.get(i).getGroup())) == 0){
				contents[rowOfContents][0] = "v";
				contents[rowOfContents][1] = bList.bArray.get(i).getGroup();
				contents[rowOfContents][2] = "";
				contents[rowOfContents][3] = "";
				contents[rowOfContents][4] = "";
				contents[rowOfContents][5] = "";
				rowOfContents++;
				groupCheck.set(groupList.indexOf(bList.bArray.get(i).getGroup()),1);
			}
			contents[rowOfContents][0] = "";
			contents[rowOfContents][1] = bList.bArray.get(i).getGroup();
			contents[rowOfContents][2] = bList.bArray.get(i).getName();
			contents[rowOfContents][3] = bList.bArray.get(i).getUrl();
			contents[rowOfContents][4] = bList.bArray.get(i).getCtime();
			contents[rowOfContents][5] = bList.bArray.get(i).getNote();
			rowOfContents++;
		}
		
		model = new DefaultTableModel(contents, headers);
		model.setColumnCount(headers.length);
		model.setColumnIdentifiers(headers);
		
		table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(1);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(650, 300));
		
		// group이 있는 bookmark들을 접고 시작
		System.out.println("===== System Started ====");
		for (int i=0; i<groupList.size(); i++) {
			close(groupList.get(i));
		}
		flag = 1;
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 0) {
					JTable target = (JTable)e.getSource();
					if (target != table) return;
				}
				if (table.getSelectedColumn() == 0) {
					String openMark = (String) table.getValueAt(table.getSelectedRow(), 0);
					String sGroup = (String) table.getValueAt(table.getSelectedRow(), 1);
					
					if(openMark.equals(">")) {
						open(sGroup);
					}
					else if (openMark.equals("v")) {
						close(sGroup);
					}
				}
			}
		});
		printBList();
		
	}


	 public void open(String group) {
		 String[] contents = new String[6];
		 int groupRow=0;
		 for (int i=0; i<model.getRowCount(); i++) {
			 if (model.getValueAt(i, 0).equals("") && model.getValueAt(i, 1).equals(group)) {
				 model.removeRow(i);
				 
			 }	 
		 }
		 for (int i=0; i<model.getRowCount(); i++) {
			 if(model.getValueAt(i, 1).equals(group)) {
				 groupRow = i;
				 break;
			 } 
		 }
		 model.setValueAt("v", groupRow, 0);
		 for (int i=0; i<bList.numBookmarks(); i++) {
			 if (bList.bArray.get(i).getGroup().equals(group)) {
				 groupRow=groupRow+1;
				 contents[0] = "";
				 contents[1] = bList.bArray.get(i).getGroup();
				 contents[2] = bList.bArray.get(i).getName();
				 contents[3] = bList.bArray.get(i).getUrl();
				 contents[4] = bList.bArray.get(i).getCtime();
				 contents[5] = bList.bArray.get(i).getNote();
				 model.insertRow(groupRow, contents);
			 }
		 }
		 remakeBookmarkList();
		 // GUI랑 실제 BookmarkList 객체 비교
		 printBList();
	 }
	 
	 public void close(String group) {
		 ArrayList<Integer> rowPosition = new ArrayList<Integer>();
		 int row;
		 int groupRow=0;
		 
		 for (int i=0; i<model.getRowCount(); i++) {
			 if(model.getValueAt(i, 1).equals(group) && !model.getValueAt(i, 2).equals(""))
				 rowPosition.add(i);
			 if(model.getValueAt(i, 1).equals(group) && model.getValueAt(i, 0).equals("v"))
				 groupRow = i;
		 }
		 
		 model.setValueAt(">", groupRow, 0);
		 
		 for (int i=rowPosition.size()-1; i>=0; i--) {
			 row = rowPosition.get(i);
			 model.removeRow(row);
		 }
		 
		 remakeBookmarkList();
		 if (flag==1) {
			// GUI랑 실제 BookmarkList 객체 비교
			 printBList();
		 }
		
	 }
	 
	 public void remakeBookmarkList() {
		ArrayList<Bookmark> tempBList = new ArrayList<Bookmark>();
		String name;
		String ctime;
		String url;
		String group;
		String note;
		    
		for (int i=0; i<model.getRowCount(); i++) {
			name = (String) model.getValueAt(i, 2);
			ctime = (String) model.getValueAt(i, 4);
			url = (String) model.getValueAt(i, 3);
			group = (String) model.getValueAt(i, 1);
			note = (String) model.getValueAt(i, 5);
				
			if (model.getValueAt(i, 0).equals("")) {
				tempBList.add(new Bookmark(name, ctime, url, group, note));
			}
			else if (model.getValueAt(i, 0).equals("v")) {
				continue;
			}
			else if (model.getValueAt(i, 0).equals(">")) {
				String groupName = (String) model.getValueAt(i, 1);
				for (int j=0; j<bList.numBookmarks(); j++) {
					if (bList.getBookmark(j).getGroup().equals(groupName)) {
						tempBList.add(bList.getBookmark(j));
					}
				}
			}
		}
		bList.bArray = tempBList;
	}
	 
	public void printBList() {
		System.out.println("Current bList is.. ");
		for (int i=0; i<bList.numBookmarks(); i++) {
			bList.getBookmark(i).print();
		}
		System.out.println("");
	}
}
