package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.Transport;

public class Server implements AutoCloseable {

	private ServerSocket serverSocket;
	private ExecutorService executorService;
	private String Res = new String();
	
	@Override
	public void close() throws Exception {
		stop();
	}
	
	public void start(int port) throws IOException {
		stop();
		serverSocket = new ServerSocket(port);
		executorService = Executors.newFixedThreadPool(10 * Runtime.getRuntime().availableProcessors());
		while (serverSocket != null && !serverSocket.isClosed()) {
			try {
				final Socket client = serverSocket.accept();
				InputStream in = client.getInputStream();
		        OutputStream out = new FileOutputStream("received.py");
		        
		        byte[] bytes = new byte[16*1024];
	
		        int count;
		        while ((count = in.read(bytes)) > 0) {
		            out.write(bytes, 0, count);
		        }
		        
		        out.close();
	            in.close();
		        
		        ProcessBuilder pb = new ProcessBuilder("python", "received.py");
		        Process p = pb.start();
		        
		        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		        
		        System.out.println("Server side :- \n Rezultat:\n");
	            Res=Res.concat("Rezultat:\n");
	            
	            String s;
	            while ((s = stdInput.readLine()) != null) {
	                System.out.println(s);
	               Res=Res+s+"\n";
	            }
	            
	            Res =Res.concat("Erorr:\n");
	            
	            
	            while ((s = stdError.readLine()) != null) {
	                System.out.println(s);
	                Res=Res+s+"\n";
	            }
	            
	            File file2 = new File ("myres.txt");
	            BufferedWriter out2 = new BufferedWriter(new FileWriter(file2)); 
	            out2.write(Res);
	            out2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	}
	
	public void stop() throws IOException {
		if (serverSocket != null && !serverSocket.isClosed()) {
			serverSocket.close();
		}
		if (executorService != null) {
			executorService.shutdown();
		}
	}

}
