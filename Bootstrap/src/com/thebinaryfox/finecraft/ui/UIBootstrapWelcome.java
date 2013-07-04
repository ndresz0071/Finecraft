package com.thebinaryfox.finecraft.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * The welcome window where you select a launcher.
 * 
 * @author TheBinaryFox
 */
public class UIBootstrapWelcome extends BSUIWindow {

	static final Color background = new Color(235, 235, 235);
	static final Color selection = new Color(220, 220, 220);
	static final Color text = new Color(100, 100, 100);

	private JLabel title;
	private JLabel titletext;
	private JList recommended;
	private JTextField other;
	private JButton use;
	private HashMap<String, String> recommendedurls;
	private DefaultListModel mod;

	@Override
	protected void init() {
		// Create
		title = new JLabel("Welcome to Finecraft!");
		titletext = new JLabel(
				"<html><body style=\"width: 100%\">Before you can start, you need to pick a launcher to use. Below is a list of recommended launchers to use. If you wish to use a custom launcher, please select \"Other\" and enter its update cast URL. You can always change this setting later through the bootstrap configuration utility provided in the download.</body></html>");

		title.setFont(title.getFont().deriveFont(40f).deriveFont(Font.BOLD));
		title.setForeground(text);
		titletext.setFont(titletext.getFont().deriveFont(13f));
		titletext.setForeground(text);

		mod = new DefaultListModel();
		mod.addElement("`dLoading...");
		mod.addElement("Other");

		recommended = new BSUIList();
		recommended.setModel(mod);
		recommended.setSelectionBackground(selection);
		recommended.setSelectionForeground(text);
		recommended.setBorder(BorderFactory.createEmptyBorder());
		recommended.setBackground(Color.white);
		JScrollPane recommendedp = new JScrollPane(recommended);
		recommendedp.setBorder(BorderFactory.createLineBorder(text));

		recommended_download();

		// Add
		JPanel container = getPanel();
		container.setBackground(background);
		container.add(title);
		container.add(titletext);
		container.add(recommendedp);

		// Layout
		SpringLayout layout = getLayout();
		layout.putConstraint("North", title, 20, "North", container);
		layout.putConstraint("HorizontalCenter", title, 0, "HorizontalCenter", container);

		layout.putConstraint("North", titletext, 5, "South", title);
		layout.putConstraint("West", titletext, 20, "West", container);
		layout.putConstraint("East", titletext, -20, "East", container);

		layout.putConstraint("North", recommendedp, 10, "South", titletext);
		layout.putConstraint("West", recommendedp, 20, "West", container);
		layout.putConstraint("East", recommendedp, -20, "East", container);
	}

	@Override
	protected boolean animate() {
		return true;
	}

	private void recommended_download() {
		new Thread("FCBS: Download Recommended List") {
			@Override
			public void run() {
				try {
					Thread.sleep(3000); // TODO replace with a download
				} catch (Exception ex) {
				}

				recommendedurls = new LinkedHashMap<String, String>();
				for (int i = 0; i < 10; i++) {
					recommendedurls.put("Debug Item #", "-");
				}

				// Animate
				mod.remove(0);
				String[] keys = recommendedurls.keySet().toArray(new String[0]);
				for (int i = 0; i < keys.length; i++) {
					try {
						mod.add(mod.getSize() - 1, "`a15`" + keys[i]);
						Thread.sleep(1000 / 45);
					} catch (Exception ex) {
					}
				}
			}
		}.start();
	}
}
