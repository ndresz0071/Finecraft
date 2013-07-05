package com.thebinaryfox.finecraft.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.jhlabs.image.BoxBlurFilter;

public/* abstract */class UIBootstrapGlass extends JPanel implements MouseListener {

	// CONSTANT
	static private final Color defaultglass = new Color(0, 0, 0, 100);
	static private final Color defaultforeground = new Color(100, 100, 100);
	static private final Color defaultbackground = new Color(235, 235, 235);
	static private final Border defaultborder = BorderFactory.createCompoundBorder(new BSUIDropBorder(defaultforeground, 2), BorderFactory.createLineBorder(defaultforeground));

	// Fields: instance private
	private BSUIWindow window;
	private BoxBlurFilter gaussian;
	private Color glass;
	private JPanel content;

	// Constructors: public
	public UIBootstrapGlass(BSUIWindow window) {
		this.window = window;
		this.gaussian = new BoxBlurFilter();
		this.gaussian.setRadius(2);
		this.gaussian.setIterations(4);
		this.content = new JPanel() {
			private static final long serialVersionUID = -5812939921240723929L;

			@Override
			public void paintComponent(Graphics g) {
				// Paint background
				Insets ins = getBorder().getBorderInsets(this);
				g.setColor(getBackground());
				g.fillRect(ins.left, ins.top, getWidth() - ins.left - ins.right, getHeight() - ins.top - ins.bottom);

				// Paint component
				super.paintComponent(g);
			}

			@Override
			public void setOpaque(boolean opaque) {
				super.setOpaque(false);
			}

		};
		this.content.setOpaque(false);

		setOpaque(false);
		super.setBackground(new Color(0, 0, 0, 0));
		addMouseListener(this);

		setGlass(null);
		setForeground(null);
		setBackground(null);
		setBorder(defaultborder);

		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		add(content);

		layout.putConstraint("VerticalCenter", content, 0, "VerticalCenter", this);
		layout.putConstraint("HorizontalCenter", content, 0, "HorizontalCenter", this);
	}

	// Methods: private
	private BufferedImage getBackgroundImage() {
		BufferedImage image = window.snapshot();
		BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		gaussian.filter(image, image2);
		return image2;
	}

	private boolean inBounds(int x, int y) {
		if (y < 0)
			return false;

		return SwingUtilities.getDeepestComponentAt(content, x, y) != null;
	}

	// Methods: public
	/**
	 * Set the color of the glass background.
	 * 
	 * @param color
	 *            the glass background color.
	 */
	public void setGlass(Color color) {
		glass = color == null ? defaultglass : color;
	}

	/**
	 * Get the color of the glass background.
	 * 
	 * @return the color of the glass background.
	 */
	public Color getGlass() {
		return glass;
	}

	// Overriden
	@Override
	public void setBackground(Color color) {
		if (content == null)
			return;

		content.setBackground(color == null ? defaultbackground : color);
	}

	@Override
	public Color getBackground() {
		if (content == null)
			return super.getBackground();

		return content.getBackground();
	}

	@Override
	public void setForeground(Color color) {
		if (content == null)
			return;

		content.setForeground(color == null ? defaultforeground : color);
	}

	@Override
	public Color getForeground() {
		if (content == null)
			return super.getForeground();

		return content.getForeground();
	}

	@Override
	public void setBorder(Border border) {
		if (content == null)
			return;

		if (border == null) {
			content.setPreferredSize(new Dimension(300, 200));
		} else {
			Insets ins = border.getBorderInsets(content);
			content.setPreferredSize(new Dimension(300 + ins.left + ins.right, 200 + ins.top + ins.bottom));
		}

		content.setBorder(border);
	}

	@Override
	public Border getBorder() {
		if (content == null)
			return super.getBorder();

		return content.getBorder();
	}

	@Override
	public void paintComponent(Graphics g) {
		// Draw background image
		if (glass.getAlpha() != 255)
			g.drawImage(getBackgroundImage(), 0, 0, getWidth(), getHeight(), null);

		// Draw glass background
		g.setColor(glass);
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

	@Override
	public void paintBorder(Graphics g) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!inBounds(e.getX(), e.getY()))
			e.consume();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (!inBounds(e.getX(), e.getY()))
			e.consume();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (!inBounds(e.getX(), e.getY()))
			e.consume();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!inBounds(e.getX(), e.getY()))
			e.consume();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!inBounds(e.getX(), e.getY()))
			e.consume();
	}

}
