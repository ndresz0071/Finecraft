package com.thebinaryfox.finecraft.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class UIBootstrapWelcome extends BSUIWindow {
	
	static final Color background = new Color(235, 235, 235);
	static final Color text = new Color(100, 100, 100);
	
	private JLabel title;
	private JLabel titletext;

	@Override
	protected void init() {
		title = new JLabel("Welcome to Finecraft!");
		titletext = new JLabel(
				"<html><body style=\"width: 100%\">Before you can start, you need to pick a launcher to use. Below is a list of recommended launchers to use. If you wish to use a custom launcher, please select \"Other\" and enter its update cast URL. You can always change this setting later through the bootstrap configuration utility provided in the download.</body></html>");

		title.setFont(title.getFont().deriveFont(40f).deriveFont(Font.BOLD));
		title.setForeground(text);
		titletext.setFont(titletext.getFont().deriveFont(13f));
		titletext.setForeground(text);

		// Add
		JPanel container = getPanel();
		container.setBackground(background);
		container.add(title);
		container.add(titletext);

		// Layout
		SpringLayout layout = getLayout();
		layout.putConstraint("North", title, 20, "North", container);
		layout.putConstraint("HorizontalCenter", title, 0, "HorizontalCenter", container);

		layout.putConstraint("North", titletext, 5, "South", title);
		layout.putConstraint("West", titletext, 20, "West", container);
		layout.putConstraint("East", titletext, -20, "East", container);
	}
	
	@Override
	protected boolean animate() {
		return true;
	}
}
