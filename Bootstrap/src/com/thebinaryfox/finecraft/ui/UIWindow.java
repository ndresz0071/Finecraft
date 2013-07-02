package com.thebinaryfox.finecraft.ui;

/**
 * The root UI window for Finecraft.
 * 
 * @author TheBinaryFox
 */
public abstract class UIWindow {

	// Static public
	/**
	 * Make the window. 
	 */
	static public void make() {
		
	}
	
	// Static private
	static private void change_do(UIWindow window) {
		
	}
	
	// Public
	public final void change() {
		UIWindow.change_do(this);
	}
	
	// Abstract
	
}
