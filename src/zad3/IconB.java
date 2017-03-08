package zad3;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JComponent;

public class IconB implements Icon{

	private int h = 4;
	private int w = 100;
	private Color c;
	
	
	
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return h;
	}

	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return w;
	}

	@Override
	public void paintIcon(Component a, Graphics g, int x, int y) {
		// TODO Auto-generated method stub
		g.setColor(Color.red);
		//w = ((JComponent) a).getHeight() / 2;
		g.fillRect(x, y, w, h);
	}

}
