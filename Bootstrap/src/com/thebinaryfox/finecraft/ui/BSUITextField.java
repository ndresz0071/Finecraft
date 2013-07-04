package com.thebinaryfox.finecraft.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

public class BSUITextField extends JTextField {

	private static final long serialVersionUID = -1100884327443439632L;

	// Fields
	private String placeholder;

	// Constructors
	public BSUITextField(String placeholder) {
		super("");
		
		this.placeholder = placeholder;
	}

	// Public methods
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	// Overridden methods
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (getText().isEmpty()) {
			if (g instanceof Graphics2D) {
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			}

			g.setColor(getDisabledTextColor());
			g.setFont(getFont());
			g.drawString(placeholder, getInsets().left, getHeight() / 2 + getInsets().top - 1);
		}
	}

}
