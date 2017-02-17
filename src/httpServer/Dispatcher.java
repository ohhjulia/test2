package httpServer;

import java.io.IOException;
import java.net.Socket;

import util.CloseUtil;

/**
 * Ϊÿ�����󴴽�һ���˶���
 * @author cpy
 *
 */
public class Dispatcher implements Runnable {
	private Socket client;
	private Request req;
	private Response rep;
	private int code=200;
	Dispatcher(Socket client){
		this.client=client;
	    try {
			req=new Request(client.getInputStream());
			rep=new Response(client.getOutputStream());
		} catch (IOException e) {
			code=500;
			return;
		}
		
	}


	@Override
	public void run() {
		//��Ӧ����   /answer/?I=3,8,0,1,2
		if(!req.getUrl().equals("/answer/")){
			code=404;
		}

		Servlet servlet=new Servlet();
		
		servlet.service(req, rep);
		try {
			rep.pushToClient(code);
		} catch (IOException e) {
			
		}
/*		try {
			rep.pushToClient(500);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		CloseUtil.closeSocket(client);
		}
	
}
