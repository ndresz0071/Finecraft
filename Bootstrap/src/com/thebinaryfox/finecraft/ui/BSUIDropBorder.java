package com.thebinaryfox.finecraft.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class BSUIDropBorder implements Border {

	// Fields: instance privat
	private Color color;
	private int size;

	// Constructors: public
	public BSUIDropBorder(Color color, int size) {
		if (size < 1)
			size = 1;

		this.color = color;
		this.size = size;
	}

	@Override
	public Insets getBorderInsets(Component arg0) {
		return new Insets(size, size, size, size);
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}

	@Override
	public void paintBorder(Component arg0, Graphics g, int x, int y, int width, int height) {
		System.out.println("DDSB: " + x + ", " + y + " : " + width + ", " + height);
		for (int i = 0; i < size; i++) {
			int alpha = (int) ((i + 1) / (float) size * 200f);
			g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
			g.drawRect(x + i, y + i, width - size - i - 1 + (size - i), height - size - i - 1 + (size - i));
		}
	}

}
