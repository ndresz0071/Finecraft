package com.thebinaryfox.finecraft.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 * The root UI window for Finecraft.
 * 
 * @author TheBinaryFox
 */
public abstract class UIWindow {

	// Static private
	static private JFrame window;

	// Static public
	/**
	 * Make the window.
	 */
	static public void make() {
		if (window != null)
			return;

		window = new JFrame();
		window.setTitle("Finecraft");
	}

	// Static private
	static private void change_do(UIWindow ui) {
		ui.getPanel().removeAll();
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
		UIWindow.change_do(this);
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
		return (SpringLayout) window.getLayout();
	}

	// Abstract
	/**
	 * Initialize the UI.
	 */
	protected abstract void init();
}
