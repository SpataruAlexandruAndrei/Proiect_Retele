package client;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import common.Settings;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class Program extends Shell {
	private Text text1, text2;
	private Client client;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Program shell = new Program(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Program(Display display) throws UnknownHostException, IOException {
		super(display, SWT.SHELL_TRIM);
		client = new Client(Settings.HOST, Settings.PORT);
		setLayout(new GridLayout(1, false));
		
//		List list = new List(this, SWT.BORDER);
//		GridData gd_list = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
//		gd_list.heightHint = 396;
//		gd_list.widthHint = 849;
//		list.setLayoutData(gd_list);
		Listener executa = new Listener() {
			@Override
			public void handleEvent(Event event) {
				try {
					client.executaScript(text1.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
  	  	};
  	  	Listener rezultat = new Listener() {
			@Override
			public void handleEvent(Event event) {
				try {
					text2.setLayoutData(new RowData(120, 80));
					text2.setText(client.afisareRezultat());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	  	};
		text1 = new Text(this, SWT.BORDER);
		text1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button b1 = new Button(this, SWT.NONE);
	    b1.setText("Executa Script");
	    b1.addListener(SWT.Selection, executa);
	    
	    Button b2 = new Button(this, SWT.NONE);
	    b2.setText("Afiseaza rezultat");
	    b2.addListener(SWT.Selection, rezultat);
	    
		text2 = new Text(this, SWT.BORDER | SWT.WRAP);

		createContents();
		
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(864, 503);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
