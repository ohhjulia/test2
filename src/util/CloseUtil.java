package util;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CloseUtil {
	
	public static void closeIO(Closeable... io){
		for(Closeable temp:io){
			try {
				temp.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void closeSocket(Socket socket){
		try {
			if(null != socket)
			socket.close();
		} catch (Exception e) {
			
		}
	}
	public static void closeSocket(ServerSocket socket){
		try {
			if(null != socket)
			socket.close();
		} catch (Exception e) {
			
		}
	}
}
