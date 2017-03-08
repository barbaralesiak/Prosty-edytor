/**
 *
 *  @author Lesiak-Draus Barbara PD2159
 *
 */

package zad3;

import javax.swing.SwingUtilities;

public class Main {

  public static void main(String[] args) {
	  SwingUtilities.invokeLater(new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			new Menus();
		}
		  
	  });
	  
  }
}
