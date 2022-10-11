package project6;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel{
	BookmarkList bList;
	JTable table;
	BookmarkInfo bookmarkInfo;
	BookmarkListPanel bookmarkListPanel;
	
	JButton addBt = new JButton("ADD");
	JButton deleteBt = new JButton("DELETE");
	JButton upBt = new JButton("UP");
	JButton downBt = new JButton("DOWN");
	JButton saveBt = new JButton("SAVE");
	
	ButtonPanel(BookmarkList bList, BookmarkListPanel bookmarkListPanel) {
		this.bList = bList;
		this.bookmarkListPanel = bookmarkListPanel;
		setLayout(new GridLayout(5,1));
		
		addBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookmarkInfo = new BookmarkInfo(bList, bookmarkListPanel);
			}
		});
		deleteBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int flag = 0;
				int row = bookmarkListPanel.table.getSelectedRow();
				String group = (String) bookmarkListPanel.model.getValueAt(row, 1);
				int idx = 0;
				for (int i=0; i<bookmarkListPanel.bList.numBookmarks(); i++) {
					if (bookmarkListPanel.bList.bArray.get(i).getName().equals(bookmarkListPanel.model.getValueAt(row, 2)))
							idx = i;
				}
				if (!bookmarkListPanel.model.getValueAt(row, 0).equals("v") && !bookmarkListPanel.model.getValueAt(row, 0).equals(">"))	{
					bookmarkListPanel.model.removeRow(row);
					bookmarkListPanel.bList.delete(idx);
				}
				for (int i=0; i<bookmarkListPanel.bList.numBookmarks(); i++) {
					if (bookmarkListPanel.bList.getBookmark(i).getGroup().equals(group)) {
						flag = 1;
					}
				}
				if (flag == 0) {
					for (int i=0; i<bookmarkListPanel.model.getRowCount(); i++) {
						if (bookmarkListPanel.model.getValueAt(i, 0).equals("v") && bookmarkListPanel.model.getValueAt(i, 1).equals(group)) {
							bookmarkListPanel.model.removeRow(i);
							break;
						}
					}
				}
				bookmarkListPanel.printBList();
			}
		});
		upBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = bookmarkListPanel.table.getSelectedRow();
				int idx = 0;
				int num = 1;
				for (int i = 0; i<bookmarkListPanel.bList.numBookmarks(); i++) {
					if (bookmarkListPanel.bList.bArray.get(i).getName().equals(bookmarkListPanel.model.getValueAt(row, 2))) {
						idx = i;
					}
				}
				if (row != 0) {
					String[] contents = new String[6];
					contents[0] = (String) bookmarkListPanel.model.getValueAt(row, 0);
					contents[1] = (String) bookmarkListPanel.model.getValueAt(row, 1);
					contents[2] = (String) bookmarkListPanel.model.getValueAt(row, 2);
					contents[3] = (String) bookmarkListPanel.model.getValueAt(row, 3);
					contents[4] = (String) bookmarkListPanel.model.getValueAt(row, 4);
					contents[5] = (String) bookmarkListPanel.model.getValueAt(row, 5);

					bookmarkListPanel.model.insertRow(row-1, contents);
					bookmarkListPanel.model.removeRow(row+1);
					
					bookmarkListPanel.remakeBookmarkList();
					// GUI客 合付农府胶飘 按眉 厚背
					bookmarkListPanel.printBList();
				}
			}
		});
		downBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = bookmarkListPanel.table.getSelectedRow();
				int idx = 0;
				int num = 2;
				for (int i = 0; i<bookmarkListPanel.bList.numBookmarks(); i++) {
					if (bookmarkListPanel.bList.bArray.get(i).getName().equals(bookmarkListPanel.model.getValueAt(row, 2))) {
						idx = i;
					}
				}
				if (row!=bookmarkListPanel.model.getRowCount()-1) {
					String[] contents = new String[6];
					contents[0] = (String) bookmarkListPanel.model.getValueAt(row, 0);
					contents[1] = (String) bookmarkListPanel.model.getValueAt(row, 1);
					contents[2] = (String) bookmarkListPanel.model.getValueAt(row, 2);
					contents[3] = (String) bookmarkListPanel.model.getValueAt(row, 3);
					contents[4] = (String) bookmarkListPanel.model.getValueAt(row, 4);
					contents[5] = (String) bookmarkListPanel.model.getValueAt(row, 5);

					bookmarkListPanel.model.insertRow(row+2, contents);
					bookmarkListPanel.model.removeRow(row);
					
					bookmarkListPanel.remakeBookmarkList();
					
					// GUI客 合付农府胶飘 按眉 厚背
					bookmarkListPanel.printBList();
					
				}
				
			}
		});
		saveBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookmarkListPanel.bList.fileWriter();
			}
		});
		
		add(addBt);
		add(deleteBt);
		add(upBt);
		add(downBt);
		add(saveBt);
	}
}
