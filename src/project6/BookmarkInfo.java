package project6;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookmarkInfo extends JFrame {
	DefaultTableModel model; 
	JTable table;
	JScrollPane scrollPane;
	JButton inputBt = new JButton("INPUT");
	BookmarkList bList;
	BookmarkListPanel bookmarkListPanel;
	
	BookmarkInfo(BookmarkList bList, BookmarkListPanel bookmarkListPanel) {
		this.bList = bList;
		this.bookmarkListPanel = bookmarkListPanel;
		DefaultTableModel model;
		
		String[] headers = {"Group", "Name", "URL", "Memo"};
		String[][] contents = new String[1][4];
		
		model = new DefaultTableModel(contents, headers);
		model.setColumnCount(headers.length);
		model.setColumnIdentifiers(headers);
		
		table = new JTable(model);
		
		scrollPane = new JScrollPane(table);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		scrollPane.setPreferredSize(new Dimension(650, 300));
		
		setTitle("Input New Bookmark");
		setSize(500, 120);
		setLayout(new BorderLayout());
		
		add(scrollPane, BorderLayout.CENTER);
		add(inputBt, BorderLayout.EAST);
		
		inputBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date time = new Date();
				SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
				
				String group = (String) table.getValueAt(0, 0);
				String name = (String) table.getValueAt(0, 1);
				String url = (String) table.getValueAt(0, 2);
				String memo = (String) table.getValueAt(0, 3);
				String ctime = timeFormat.format(time);
				
				// 변수들 null 체크
				if (name==null)
					name = "";
				if (memo==null)
					memo = "";
				if (group==null)
					group = "";
				
				bookmarkListPanel.bList.add(new Bookmark(name, ctime, url, group, memo));
				
				String[] contents = new String[6];
				contents[0] = "";
				contents[1] = group;
				contents[2] = name;
				contents[3] = url;
				contents[4] = ctime;
				contents[5] = memo;
				if (group.equals("")) {
					bookmarkListPanel.model.addRow(contents);
				}
				else {
					// 기존에 없던 group일 경우 새롭게 그룹추가
					int flag = 0;
					int idx;
					for (idx=0; idx<bookmarkListPanel.model.getRowCount(); idx++) {
						if (bookmarkListPanel.model.getValueAt(idx, 1).equals(group)) {
							flag = 1;
							break;
						}
					}
					if (flag == 0) {		
						bookmarkListPanel.model.addRow(new String[] {"v", group, "", "", "", ""});
						bookmarkListPanel.model.addRow(contents);
					}
					else if (flag == 1) {
						if (bookmarkListPanel.model.getValueAt(idx, 0).equals(">")) {
							
						}
						else if (bookmarkListPanel.model.getValueAt(idx, 0).equals("v")) {
							for (int i=bookmarkListPanel.model.getRowCount()-1; i>idx; i--) {
								if (bookmarkListPanel.model.getValueAt(i, 1).equals(group)) {
									bookmarkListPanel.model.insertRow(i+1, contents);
									break;
								}
							}
						}
					}
				}
				
				
				
				bookmarkListPanel.remakeBookmarkList();
				bookmarkListPanel.printBList();
								
				dispose();
			}
		});
		setVisible(true);
	}
}
