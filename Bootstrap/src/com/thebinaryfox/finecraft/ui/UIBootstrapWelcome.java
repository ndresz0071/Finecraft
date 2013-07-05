package com.thebinaryfox.finecraft.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
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
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.thebinaryfox.finecraft.bs.Bootstrap;

/**
 * The welcome window where you select a launcher.
 * 
 * @author TheBinaryFox
 */
public class UIBootstrapWelcome extends BSUIWindow {

	// CONSTANT
	static private final Color background = new Color(235, 235, 235);
	static private final Color selection = new Color(220, 220, 220);
	static private final Color text = new Color(100, 100, 100);
	static private final Border other_fail = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 100, 100)), BorderFactory.createEmptyBorder(5, 5, 5, 5));
	static private final Border other_okay = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(text), BorderFactory.createEmptyBorder(5, 5, 5, 5));
	static private final Color other_failc = new Color(255, 210, 210);
	static private final Color other_okayc = Color.white;

	// Fields: instance private
	private JLabel title;
	private JLabel titletext;
	private JList recommended;
	private JTextField other;
	private JButton use;
	private JScrollPane recommendedp;
	private HashMap<String, String> recommendedurls;
	private DefaultListModel mod;

	// Methods: instance protected
	@Override
	protected void init() {
		Bootstrap.getLogger().x();
		Bootstrap.getLogger().i("Displaying welcome UI.");

		// Create
		title = new JLabel("Welcome to Finecraft!");
		titletext = new JLabel(
				"<html><body style=\"width: 100%\">Before you can start, you need to pick a launcher to use. Below is a list of recommended launchers to use. If you wish to use a custom launcher, please select \"Other\" and enter its update cast URL. You can always change this setting later through the bootstrap configuration utility provided with the Finecraft download.</body></html>");

		title.setFont(title.getFont().deriveFont(40f).deriveFont(Font.BOLD));
		title.setForeground(text);
		titletext.setFont(titletext.getFont().deriveFont(13f));
		titletext.setForeground(text);

		other = new BSUITextField("URL");
		other.setBorder(other_okay);
		other.setBackground(other_okayc);
		other.setForeground(Color.black);
		other.setDisabledTextColor(Color.gray);
		other.setVisible(false);
		other.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (other.getText().trim().isEmpty())
					return;

				try {
					other.setDisabledTextColor(Color.gray);
					other.setBackground(other_okayc);
					other.setBorder(other_okay);
					next(new URL(other.getText()));
					clean();
				} catch (Exception ex) {
					other.setDisabledTextColor(new Color(200, 100, 100));
					other.setBackground(other_failc);
					other.setBorder(other_fail);
				}
			}

		});

		use = new JButton("Select Launcher");
		use.setOpaque(true);
		use.setForeground(Color.black);
		use.setBackground(new Color(215, 215, 215));
		use.setBorder(other_okay);
		use.setVisible(false);
		use.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					next(new URL(recommendedurls.get(recommended.getSelectedValue().toString())));
					clean();
				} catch (Exception ex) {
				}
			}

		});

		mod = new DefaultListModel();
		mod.addElement("`dDownloading...");
		mod.addElement("Other");

		recommended = new BSUIList();
		recommended.setModel(mod);
		recommended.setSelectionBackground(selection);
		recommended.setSelectionForeground(text);
		recommended.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		recommended.setBorder(BorderFactory.createEmptyBorder());
		recommended.setBackground(Color.white);
		recommended.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (recommended.getSelectedIndex() == -1) {
					other.setVisible(false);
					use.setVisible(false);
					getLayout().putConstraint("South", recommendedp, -20, "South", getPanel());
					getPanel().validate();
					return;
				}

				if (recommended.getSelectedValue().toString().equals("Other")) {
					other.setVisible(true);
					use.setVisible(false);
					getLayout().putConstraint("South", recommendedp, -5, "North", other);
				} else {
					other.setVisible(false);
					use.setVisible(true);
					getLayout().putConstraint("South", recommendedp, -5, "North", use);
				}

				getPanel().validate();
			}

		});

		recommendedp = new JScrollPane(recommended);
		recommendedp.setBorder(BorderFactory.createLineBorder(text));

		recommended_download();

		// Add
		JPanel container = getPanel();
		container.setBackground(background);
		container.add(title);
		container.add(titletext);
		container.add(recommendedp);
		container.add(other);
		container.add(use);

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
		layout.putConstraint("South", recommendedp, -20, "South", container);

		layout.putConstraint("West", other, 20, "West", container);
		layout.putConstraint("East", other, -20, "East", container);
		layout.putConstraint("South", other, -20, "South", container);

		layout.putConstraint("West", use, 20, "West", container);
		layout.putConstraint("East", use, -20, "East", container);
		layout.putConstraint("South", use, -20, "South", container);
	}

	// Methods: instance private
	/**
	 * Check if the launcher cast at the URL is valid. If it is, continue to the next screen.
	 * 
	 * @param url
	 *            the url to check.
	 * @throws IOException
	 */
	private void next(URL url) throws IOException {
		Bootstrap.getLogger().i("Checking launcher update cast at " + url);
		// TODO check if valid launcher update cast
		throw new IOException("Unimplemented!");
	}

	/**
	 * Null all objects contained.
	 */
	private void clean() {
		title = null;
		titletext = null;
		recommended = null;
		other = null;
		use = null;
		recommendedp = null;
		recommendedurls = null;
		mod = null;
	}

	/**
	 * Download the list of recommended launchers.
	 */
	private void recommended_download() {
		new Thread("FCBS: Download Recommended List") {
			@Override
			public void run() {
				Bootstrap.getLogger().i("Download recommended launcher list...");
				while (true) {
					try {
						Thread.sleep(3000); // TODO replace with a download

						break;
					} catch (Exception ex) {
						// Retry
						try {
							Bootstrap.getLogger().i("Download of recommended launcher list failed. Retrying in 5...");
							mod.set(0, "`dRetrying in 5...");
							Thread.sleep(1000);
							mod.set(0, "`dRetrying in 4...");
							Thread.sleep(1000);
							mod.set(0, "`dRetrying in 3...");
							Thread.sleep(1000);
							mod.set(0, "`dRetrying in 2...");
							Thread.sleep(1000);
							mod.set(0, "`dRetrying in 1...");
							Thread.sleep(1000);
							mod.set(0, "`dDownloading...");
						} catch (Exception ex2) {
							continue;
						}
					}
				}

				recommendedurls = new LinkedHashMap<String, String>();
				for (int i = 0; i < 10; i++) {
					recommendedurls.put("Debug Item #" + i, "-");
				}

				// Animate
				Bootstrap.getLogger().i("Inserting recommended launchers with animation.");
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
