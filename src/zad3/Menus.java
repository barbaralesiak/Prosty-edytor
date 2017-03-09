package zad3;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


public class Menus extends JFrame implements ActionListener, MouseListener, WindowListener{
	private JScrollPane sp;
	private JTextArea ar;
	static String colorsS [] = {"Blue    ", "Yellow ", "Orange", "Red", "White  ", "Black  ", "Green  "};
	static Color colorsC [] = {Color.blue, Color.yellow, Color.orange, Color.red, Color.white, Color.black, Color.green};
	static String prostyEdytor = "Prosty Edytor - ";
	static String biezacyKatalog = "bez tytułu";
	static JFileChooser jfc = new JFileChooser();	
	static File curentDir = new File(System.getProperty("user.dir"));
	static Desktop desc;
	static JTextField phraze ;
	Highlighter highlighter;
	static String host = "poczta.interia.pl";
	static String port = "587";
	static String from = "prosty.edytor@interia.pl";
	static String password = "1234edytor";
	JFrame f;
	 cos to dodaje
	 
	
	/**
	 * Constructs new Simple Editor with TextArea and options to edit, save or send text.
	 */
	public Menus ()	{
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setTitle(prostyEdytor + biezacyKatalog);
		JTextArea textArea = new JTextArea();
		ar = textArea;
		ar.addMouseListener(this);
		JScrollPane scrollPane = new JScrollPane (textArea);
		scrollPane.setPreferredSize(new Dimension(600,400));
		scrollPane.setBorder(BorderFactory.createTitledBorder("Text"));
		sp = scrollPane;
		f = new JFrame();
		f.getContentPane().add(scrollPane);
		this.add(scrollPane);
		highlighter= ar.getHighlighter();
		JMenuItem open = createFileBut("Open",'D');
		open.addActionListener(this);
		JMenuItem save =  createFileBut("Save", 'S');
		save.addActionListener(this);
		JMenuItem saveAs= createFileBut("Save As", 'B');
		saveAs.addActionListener(this);
		JMenuItem exit = createFileBut("Exit", 'E');
		exit.addActionListener(this);
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.add(open);
		file.add(save );
		file.add(saveAs );
		JButton line = new JButton(new IconB());
		line.setAlignmentX(CENTER_ALIGNMENT);
		file.add(line);
		file.add(exit);
		bar.add(file);
		JMenu sign = new JMenu("Sign");
		JMenu optionsS = new JMenu("Sign options");
		JMenuItem sincerley = createFileBut("Yours sincerley",'P');
		sincerley.addActionListener(this);
		optionsS.add(sincerley);
		JMenuItem faith = createFileBut("Yours faithfully", 'S');
		faith.addActionListener(this);
		optionsS.add(faith);
		JMenuItem regards = createFileBut("Best regards",'D');
		regards.addActionListener(this);
		optionsS.add(regards);
		sign.add(optionsS);
		bar.add(sign);
		JMenu Fontsize = new JMenu("Font size");
		for(int i =8; i<26; i+=2){
		JMenuItem b = createButFontSize(i);
		b.addActionListener(this);
		Fontsize.add(b);
		
		}	
			
		
		
		JMenu Background = new JMenu("Background"); 
		for(int i = 0; i<colorsS.length; i++){
			JMenuItem but = createBackGround(colorsS[i], colorsC[i]);
			
			but.putClientProperty("BackGround", colorsC[i]);
			but.addActionListener(this);
			Background.add(but);
		}
		
		JMenu Foreground = new JMenu("Foreground");
			for(int i =0; i< colorsS.length; i++){
				JMenuItem butF = createBackGround(colorsS[i], colorsC[i]);
				butF.putClientProperty("Foreground", colorsC[i]);
				butF.addActionListener(this);
				Foreground.add(butF);
		}
			JMenu Options = new JMenu("Options");
			Options.add(Foreground);
			Options.add(Background);
			Options.add(Fontsize);
			JMenu send = new JMenu("Send");
			JMenuItem email = new JMenuItem("Send email");
			send.add(email);
			email.addActionListener(this);
			JMenuItem configure = new JMenuItem("Configure email");
			send.add(configure);
			configure.addActionListener(this);
			JMenuItem defConfig = new JMenuItem("Set email configuration to default values");
			send.add(defConfig);
			defConfig.addActionListener(this);
			bar.add(Options);
			bar.add(send);
			JButton search = new JButton("search");
			search.setActionCommand("search");
			search.addActionListener(this);
			bar.add(search);
			phraze = new JTextField();
			phraze.addActionListener(this);
			bar.add(phraze);
			setJMenuBar(bar);
			pack();
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			this.addWindowListener(this);
			this.setVisible(true);
			
		
	}
	
	
	
		class But extends JMenuItem{
			int n;
			public But(String s, int in){
				super(s);
				n = in;
			}
			int getNr(){
				return n;
			}
		}
		
		/**
		 * This method creates instances of But class .
		 * @param nr is number which will be shown on created But.
		 * @return class But instance.
		 */
		JMenuItem createButFontSize(int nr){
			JMenuItem b = new But(nr + " pts", nr);
			b.setBorder(BorderFactory.createRaisedBevelBorder());
			return b;
		
		}
	
		
	/**
	 * This method creates JMenuItems with specified icon and text on it. 
	 * @param s is text to be shown on JMenuItem which is created.
	 * @param c1 is colour of Icon which will be shown on the JMenuItem.
	 * @return JMenuItem.
	 */
	JMenuItem createBackGround(String s, Color c1 ){
		JMenuItem bc = new JMenuItem( s);
		bc.setIcon(new IconA(c1));
		bc.setBorder(BorderFactory.createRaisedBevelBorder());
		return bc;
	}
	
	/**
	 * This method creates JMenuItems with specified text  mnemonic and accelerator.
	 * @param s is text to be shown on JMenuItem.
	 * @param r is char to be used to set mnemonic and accelerator.
	 * @return JMenuItem.
	 */
	JMenuItem createFileBut(String s,char r){
		JMenuItem j = new JMenuItem(s);
		j.setMnemonic((int)r);
		((JMenuItem) j).setAccelerator(KeyStroke.getKeyStroke("control " + r));
		j.setBorder(BorderFactory.createRaisedBevelBorder());
		return j;
	}

	
	/**
	 * This method performs actions chosen by user. 
	 * Actions can be chosen by clicking one of the menu items in menu bar.
	 * @param e is action chosen by user by clicking on one of the options in menu.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String which = e.getActionCommand();
		
		if(which.equals("Open")){
			open();
		}
			
		if(which.equals("Save")){
			this.saving();
		}
		
		if(which.equals("Save As")){
			jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
			jfc.showSaveDialog(null);
		}
		
		if(which.equals("Exit") ){
			makeSureIfClose();
		 
		}
		
		if(which.equals("Yours sincerley")){
			String now = ar.getText();
			now = now + '\n' + "Yours sincerley";
			ar.setText(now);
		}
		
		if(which.equals("Yours faithfully")){
			String now = ar.getText();
			now = now + '\n' + "Yours faithfully";
			ar.setText(now);
		}
		
		if(which.equals("Best regards")){
			String now = ar.getText();
			now = now + '\n' + "Best regards";
			ar.setText(now);
		}
		
		if(which.equals("search")){
			String searchedPattern = phraze.getText();
			search(searchedPattern);
			
		}
		
		
			if(which.equals("Send email")){
				
				sendEmail();
			
				
				
			}
			if(which.equals("Configure email")){
				configure();
				
				

			}
	

	if(which.equals("Set email configuration to default values")){
		
	 host = "poczta.interia.pl";
	 port = "587";
	from = "prosty.edytor@interia.pl";
	password = "1234edytor";
}
	
	if(e.getSource() instanceof But){
	But but1= (But)e.getSource();
	float size = but1.n;
	ar.setFont(ar.getFont().deriveFont(size));
	
	
}

	JComponent src = (JComponent)e.getSource();
	
	if(src.getClientProperty("Foreground") != null){
		
		ar.setForeground((Color)src.getClientProperty("Foreground"));
	}
	if(src.getClientProperty("BackGround")!=null){
		ar.setBackground((Color)src.getClientProperty("BackGround"));
	}
	
		
	
	}
	
	/**
	 * This method reminds user to save text changes before closing window.
	 */
	private void makeSureIfClose(){
		String opcje[] = {"save", "cancel", "don't save"};
		int rc = JOptionPane.showOptionDialog(null,"Save changes ?" , "Text was modified", 
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.QUESTION_MESSAGE,
				null,
				opcje,
				opcje[0]);
		 if(rc == 2){
		this.dispose();}
		 if(rc == 0){
			this.saving();
			this.f.dispose(); }
		
	}
	
	/**
	 * This method transforms text with its settings into HTML message and sends it as email
	 * to recipient given by user. It informs user if the message was sent. 
	 * 
	 */
	private void sendEmail(){
		String mailTo = JOptionPane.showInputDialog("Please enter recipent");
		
		while(mailTo!=null && emailValidate(mailTo)==false){
			mailTo = JOptionPane.showInputDialog(null, mailTo + " is incorect adress. Try again ");
			}
		String subject =null;
		if(mailTo!=null){
			
		subject = JOptionPane.showInputDialog("Please enter the subject of message");}
		String message = this.ar.getText();
		EmailBodyMaker mailer = new EmailBodyMaker(ar);
		if(mailTo !=null && subject!=null){
		try {
			
			mailer.sendHtmlEmail(host, port, from, password, mailTo, subject, mailer.getBody());
			JOptionPane.showMessageDialog(null, "Message sent");
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Failed to sent email");
			e1.printStackTrace();
		}
		}
		
		
	}
	
	/**
	 * This method lets user to configure email settings to send email from his own mailbox. 
	 * Setting are not stored. After closing the application all settings return to its default values.
	 * 
	 */
	private void configure(){
		JPanel panel = new JPanel();
		JLabel email = new JLabel("email adress");
		
		JLabel password1 = new JLabel("eamilbox password");
		JPasswordField enterpassword = new JPasswordField(25);
		JLabel domain = new JLabel("domain");
		JTextField enterdomain = new JTextField(25);
		JLabel port1 = new JLabel("port");
		JTextField enterport = new JTextField(5);
		JTextField enteremail = new JTextField(25);
		panel.add(email);
		panel.add(enteremail);
		panel.add(password1);
		panel.add(enterpassword);
		panel.add(domain);
		panel.add(enterdomain);
		panel.add(port1);
		panel.add(enterport);
		panel.setLayout(new GridLayout(4, 2));
		
		Object []options = {panel, "set"};
		int rc = JOptionPane.showOptionDialog(
		           null,
		           null,                    
		           "Configure email",            
		           JOptionPane.DEFAULT_OPTION,
		           JOptionPane.PLAIN_MESSAGE,
		           null,
		           options,                   
		           options[1]                 
		         );
		
		
		if(rc==1){ 
			if(emailValidate(enteremail.getText())){
				try{
				Integer.parseInt(enterport.getText());
				} catch (NumberFormatException exc){
					JOptionPane.showMessageDialog(null,"port number is incorrect");
					return;
					
				}
				from = enteremail.getText();}
				else return;
			
			host = enterdomain.getText();
			port = enterport.getText();
			char [] input = enterpassword.getPassword();
			String x = "";
			for(int i =0; i<input.length; i++){
				x+=input[i];
			}
			password = x;
		}
		
	}
	
	/**
	 * This method shows open dialog. Opens selected by user file and coping text to text area in editor.
	 * Sets documents preferences if exists, if not sets it to default values. Sets file directory in title frame in editor.
	 */
	private void open(){
		jfc.setCurrentDirectory(curentDir);	
		int returnVal = jfc.showOpenDialog(null);
		if(returnVal== JFileChooser.APPROVE_OPTION){
			curentDir = new File( jfc.getSelectedFile().getParent());
			File file = jfc.getSelectedFile();
			StringBuffer txt = new StringBuffer();
			biezacyKatalog = jfc.getSelectedFile().getAbsolutePath();
			this.setTitle(prostyEdytor + biezacyKatalog );
			Scanner scan;
			FileReader fr=null;
			Color backCol = null;
			Color foreCol = null;
			Font ft = null;
			String backColString= null;
			String fontNameString = null;
			String fontColorString = null;
			String fontSizeString = null;
			String fontStyleString = null;
			
			
			try {
				fr = new FileReader(file);
				scan = new Scanner(fr);
				while(scan.hasNextLine()){
				
					txt.append(scan.nextLine()).append('\n');
				}
				String all = txt.toString();
				if(all.contains("#ustawienia")){
				int index = all.indexOf("#ustawienia");
				String preferences = txt.substring(index);
				all = all.substring(0, index);
				scan = new Scanner(preferences);
				
				while(scan.hasNextLine()){
					String name = scan.nextLine();
							String data = scan.nextLine();
							if(scan.hasNextLine())	 backColString = scan.nextLine().substring(10);
							if(scan.hasNextLine()) fontNameString = scan.nextLine().substring(8);
							if(scan.hasNextLine()) fontColorString = scan.nextLine().substring(10);
							if(scan.hasNextLine()) fontSizeString = scan.nextLine().substring(9);
							if(scan.hasNextLine()) fontStyleString = scan.nextLine().substring(10);}	
				}	
						
				ar.setText(all);
				if(backColString !=null){
				int c = Integer.parseInt(backColString);
				backCol = new Color(c);}
				else backCol = Color.white;
				if(fontColorString!=null){
				foreCol = new Color(Integer.parseInt(fontColorString));}
				else foreCol = Color.black;
				if(fontNameString!=null&& fontStyleString !=null && fontSizeString != null ){
				int fStyle = Integer.parseInt(fontStyleString);
				int fSize = Integer.parseInt(fontSizeString);
				ft = new Font(fontNameString,fStyle, fSize);}
				else ft = new Font("sanserif", Font.PLAIN, 12);
				fr.close();
				ar.setFont(ft);
				ar.setBackground(backCol);
				ar.setForeground(foreCol);
			} catch (FileNotFoundException e1) {
				System.out.println("błąd przy dostepie do pliku");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally{try {
				fr.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}}
			
			
			}
			}
	
	
	/**
	 * This method searches for given phrase in text and highlights it if found.
	 * If the searched phrase is not found it informs user about that by showing message box.
	 * @param s
	 */
	private void search(String s){
		
		String pattern = s;
		
		if(pattern.equals("")) return;
		String allText = ar.getText();
		HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
		int index = 0;
		int counter = 0;
		while(allText.indexOf(pattern, index) !=-1){
			index = index + pattern.length();
			counter++;}
		int [] tab = new int[counter];
		index = 0;
		counter = 0;
		while(allText.indexOf(pattern, index)!=-1){
			int n = allText.indexOf(pattern, index);
			tab[counter] = n;
			counter++;
			index = index+ pattern.length(); 
			
		}
			
			try {
				for(int i =0; i<tab.length; i++){
				highlighter.addHighlight(tab[i], tab[i] + pattern.length(), painter);}
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			
			
		}
		if(!allText.contains(pattern)){
			index = 0;
			JOptionPane.showMessageDialog(null, "sorry, no matches");
		} 
		
		
		
	}
	
	/**
	 * This method saves text with text properties.
	 * If the directory wasn't set before choosing save option,
	 * it opens save dialog.
	 */
	private  void saving(){
		if(biezacyKatalog.equals("bez tytułu")){
			jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
			int retVal = jfc.showSaveDialog(null);
			if(retVal == JFileChooser.APPROVE_OPTION){
				biezacyKatalog = jfc.getSelectedFile().getAbsolutePath();
				this.setTitle(prostyEdytor + biezacyKatalog );
			}
		}
		
		if(!biezacyKatalog.equals("bez tytułu")){
			 
			try {
				FileWriter	fw = new FileWriter(biezacyKatalog);
			
			String sep = System.getProperty("line.separator");
				
				Scanner scan1 = new Scanner(ar.getText());
				while(scan1.hasNextLine()){
					fw.write(scan1.nextLine()+ sep);
					
				} 
				
				scan1.close();
				Properties props = new Properties();
				props.load(new FileReader(biezacyKatalog)); 
				if(!props.isEmpty()) props.clear();
				props.setProperty("fontSize",Integer.toString(ar.getFont().getSize()) );
				props.setProperty("fontColor", Integer.toString(ar.getForeground().getRGB()));
				props.setProperty("backColor", Integer.toString(ar.getBackground().getRGB()));
				props.setProperty("fontName",ar.getFont().getFontName() );
				props.setProperty("fontStyle", Integer.toString(ar.getFont().getStyle()));
				
				props.store(fw, "ustawienia");
				fw.close();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
				
			}
		
	}
	

		
	}
 
	
	/**
	 * This method checks if the given email address has correct syntax.
	 * @param adress email address to be checked
	 */
	private boolean emailValidate(String adress){
		boolean isValid = false;
		
		try {
			InternetAddress internetadress = new InternetAddress(adress);
			internetadress.validate();
			isValid = true;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Incorrect email adress");
		}
		return isValid;
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	/**
	 * This method removes highlighting  from text immediately after mouse button is released.
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		highlighter.removeAllHighlights();
	
		
	}


	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		makeSureIfClose();
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

