package com.thebinaryfox.finecraft.ui;

import static com.thebinaryfox.finecraft.bs.Configuration.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 * The root UI window for Finecraft.
 * 
 * @author TheBinaryFox
 */
public abstract class BSUIWindow {

	// Static private
	static private JFrame window;
	static final Color background = new Color(50, 50, 55);
	static final Color text = new Color(235, 235, 235);

	// Static public
	/**
	 * Make the window.
	 */
	static public void make() {
		if (window != null)
			return;

		window = new JFrame();
		window.setTitle("Finecraft");
		window.setMinimumSize(new Dimension(500, 300));
		window.setSize(asInteger(WINDOW_WIDTH), asInteger(WINDOW_HEIGHT));
		window.setResizable(asBoolean(WINDOW_RESIZE));
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Static private
	/**
	 * Change the UI window.
	 * 
	 * @param ui
	 *            the UI window.
	 */
	static private void change_do(BSUIWindow ui) {
		ui.getPanel().removeAll();
		ui.getPanel().setBackground(background);
		ui.getPanel().setLayout(new SpringLayout());
		ui.init();
		ui.getPanel().validate();
		window.setVisible(true);
	}

	// Public
	/**
	 * Change to this window.
	 */
	public final void change() {
		BSUIWindow.change_do(this);
	}

	// Protected
	/**
	 * Get the window content.
	 * 
	 * @return the window's content pane.
	 */
	protected JPanel getPanel() {
		return (JPanel) window.getContentPane();
	}

	/**
	 * Get the window's layout manager.
	 * 
	 * @return the window's layout manager.
	 */
	protected SpringLayout getLayout() {
		return (SpringLayout) window.getContentPane().getLayout();
	}

	/**
	 * Set the window's glass pane.
	 * 
	 * @param panel
	 *            the glass pane.
	 */
	protected void setGlass(JPanel panel) {
		window.setGlassPane(panel);
		panel.setVisible(true);
	}

	/**
	 * Get a BufferedImage containing the contents of the window.
	 * 
	 * @return
	 */
	protected BufferedImage snapshot() {
		BufferedImage bi = new BufferedImage(window.getContentPane().getWidth(), window.getContentPane().getHeight(), BufferedImage.TYPE_INT_RGB);
		window.getContentPane().paint(bi.getGraphics());
		return bi;
	}

	// Abstract
	/**
	 * Initialize the UI.
	 */
	protected abstract void init();
}
