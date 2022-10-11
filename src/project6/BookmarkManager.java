package project6;
import javax.swing.*;
import java.awt.*;

public class BookmarkManager extends JFrame{
	BookmarkList bList = new BookmarkList("./bookmark.txt");
	
	BookmarkManager() {
		
		BookmarkListPanel bookmarkListPanel = new BookmarkListPanel(bList);
		ButtonPanel buttonPanel = new ButtonPanel(bList, bookmarkListPanel);
		
		setTitle("Bookmark Manager");
		setSize(700, 500);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		add(bookmarkListPanel.scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.EAST);

		setVisible(true);
	}
}

