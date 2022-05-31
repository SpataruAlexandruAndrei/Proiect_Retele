package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.eclipse.swt.widgets.Text;

import common.Transport;

public class Client implements AutoCloseable {

	private Socket socket;
	
	public Client(String host, int port) throws UnknownHostException, IOException {
		socket = new Socket(host, port);
	}
	
	public void send(String message) {
		try {
			Transport.send(message, socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void executaScript( String text) throws IOException {
		File file = new File ("myscript.py");
        BufferedWriter out = new BufferedWriter(new FileWriter(file)); 
        out.write(text);
        out.close();
        
		while (socket != null && !socket.isClosed()) {
			File file1 = new File("myscript.py");
	        long length = file1.length();
	        byte[] bytes = new byte[16 * 1024];
	        InputStream in = new FileInputStream(file1);
	        OutputStream out1 = socket.getOutputStream();

	        int count;
	        while ((count = in.read(bytes)) > 0) {
	            out1.write(bytes, 0, count);
	        }

	        out1.close();
	        in.close();
		}
	}
	
	public String afisareRezultat() throws IOException {
		String total="";
	    BufferedReader in = new BufferedReader(new FileReader("myres.txt"));
	    String str;
	    while ((str = in.readLine()) != null)
	    {  System.out.println("read "+str);
	        total=total+str+" \n ";
	    }
	    in.close();
	    return total;
	}
	
	@Override
	public void close() throws IOException {
		if (socket != null && !socket.isClosed()) {
			socket.close();
		}
	}

}
