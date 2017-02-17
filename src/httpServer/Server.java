package httpServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import answer.Answer;
import util.CloseUtil;


public class Server {  
    
	private ServerSocket server;
	public static final String CRLF="\r\n";
	public static final String BLANK="";
	
	private boolean isShutDown=false;
	//启动服务，监听来自客户端的请求  
	public void start(){

		try {
			server = new ServerSocket(8080);
			this.receive();
		} catch (IOException e) {
			//e.printStackTrace();
			stop();
		}

	}
	

	//接收客户端的请求  
	private void receive(){
		try {
			while(!isShutDown){
				new Thread(new Dispatcher(server.accept())).start();
			}
			
		} catch (IOException e) {
			stop();
		}
	}
	
	//停止服务  
	public void stop(){
		isShutDown=true;
		CloseUtil.closeSocket(server);
	} 
	
      
    public static void main(String[] args) throws IOException {  
        Server server = new Server();
    	server.start();
    	//server.receive();
    }  
}  