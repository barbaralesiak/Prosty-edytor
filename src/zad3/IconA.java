package zad3;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.*;

public class IconA implements Icon{
private Color col;
private int w =40;
	
	
	public IconA(Color c){
		col =c;
	}
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return w;
	}

	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return w;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		// TODO Auto-generated method stub
		g.setColor(col);
		w = ((JComponent) c).getHeight() / 2;
		int p = w / 4, d = w / 2;
		g.fillOval(x + p, y + p, d, d);
		
	}

}
