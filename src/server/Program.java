package server;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.widgets.Button;
import common.Settings;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

import client.Client;
import common.Settings;

public class Program extends Shell {
	private Text text;
	private Label label1,label2;
	private Server server = new Server();
	
	public Program(Display display) throws UnknownHostException, IOException {
		super(display, SWT.SHELL_TRIM);
		FillLayout fillLayout= new FillLayout();
	    fillLayout.type= SWT.VERTICAL;
		setLayout(fillLayout);
		
		Listener startSrv = new Listener() {
			@Override
			public void handleEvent(Event event) {
				try {
					server.start(Settings.PORT);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
  	  	};
	  	
		label1=new Label(this, SWT.NULL);
		label1.setText("Bun Venit!");
		
		Button b1 = new Button(this, SWT.NONE);
	    b1.setText("Porneste Server-ul");
	    b1.addListener(SWT.Selection, startSrv);

	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	public static void main(String[] args) {
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
	
	

}
