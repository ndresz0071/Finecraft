package com.thebinaryfox.finecraft.ui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

public class BSUIList extends JList implements ListCellRenderer {

	/**
	 * Selection model with disabled items.
	 * 
	 * @author TheBinaryFox
	 */
	private class SelModel extends DefaultListSelectionModel {
		private static final long serialVersionUID = -3554787083414566796L;

		@Override
		public void setSelectionInterval(int index0, int index1) {
			if (index0 == index1) {
				if (BSUIList.this.getModel().getElementAt(index0).toString().startsWith("`")) {
					return;
				}

				super.setSelectionInterval(index0, index1);
			}
		}
	}

	// Static private
	private static final long serialVersionUID = 4301893039536091492L;

	// Field
	private Color disabledfg = Color.gray;
	private Border ibord = BorderFactory.createEmptyBorder(3, 3, 3, 3);

	// Constructor
	public BSUIList() {
		setCellRenderer(this);
		setSelectionModel(new SelModel());
	}

	// Methods
	public Color getDisabledForeground() {
		return disabledfg;
	}

	public void setDisabledForeground(Color color) {
		disabledfg = color == null ? Color.gray : color;
	}

	public Border getItemBorder() {
		return ibord;
	}

	public void setItemBorder(Border border) {
		ibord = border;
	}

	// Cell renderer
	@Override
	public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
		String text = arg1.toString();
		JLabel renderer = new JLabel();
		renderer.setOpaque(true);
		renderer.setBorder(ibord);

		// Unselectable or animated
		if (text.startsWith("`d")) {
			renderer.setText(text.substring(2));
			renderer.setForeground(getDisabledForeground());
			renderer.setBackground(getBackground());
			return renderer;
		} else if (text.startsWith("`a")) {

			// Get everything after animate code
			text = text.substring(2);
			int aend = text.indexOf('`');
			String ctext = text.substring(aend + 1);

			if (arg0.getModel() instanceof DefaultListModel) {
				// Get frames
				int frames = Integer.parseInt(text.substring(0, aend)) - 1;
				if (frames > 1) {

					// Draw
					Color fg = getForeground();
					renderer.setForeground(new Color(fg.getRed(), fg.getGreen(), fg.getBlue(), (int) (255 - (frames / 15f * 255f))));
					renderer.setBackground(getBackground());
				}

				// Update
				update(new Runnable() {

					private DefaultListModel model;
					private int index;
					private String text;

					@Override
					public void run() {
						model.setElementAt(text, index);
					}

					public Runnable setVars(DefaultListModel model, int index, String text) {
						this.model = model;
						this.index = index;
						this.text = text;
						return this;
					}

				}.setVars((DefaultListModel) arg0.getModel(), arg2, frames > 1 ? ("`a" + frames + "`" + ctext) : ctext));

				if (frames > 1) {
					renderer.setText(ctext);
					return renderer;
				}

				renderer.setForeground(getBackground());
			}

			renderer.setText(ctext);
		} else {
			renderer.setText(text);
		}

		// Selected
		if (arg3) {
			renderer.setForeground(getSelectionForeground());
			renderer.setBackground(getSelectionBackground());
		} else {
			renderer.setForeground(getForeground());
			renderer.setBackground(getBackground());
		}

		return renderer;
	}

	private Thread handler;
	private final Object sync = new Object();
	private ArrayList<Runnable> updates;

	private void update(Runnable r) {
		if (handler == null) {
			updates = new ArrayList<Runnable>();
			handler = new Thread() {
				@Override
				public void run() {
					while (updates != null) {
						try {
							Thread.sleep(1000 / 60);
						} catch (Exception ex) {
						}

						synchronized (sync) {
							while (updates.size() > 0) {
								updates.remove(0).run();
							}
						}
					}
				}
			};

			handler.start();
		}

		synchronized (sync) {
			updates.add(r);
		}
	}

}
