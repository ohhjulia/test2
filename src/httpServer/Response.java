package httpServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

import util.CloseUtil;

/**
 * 封装响应信息
 * @author hp
 *
 */
public class Response {
	
	public static final String CRLF="\r\n";
	public static final String BLANK="";
	private BufferedWriter bw;
	
	//正文
	private StringBuilder content;
	
	private StringBuilder headInfo;
	private int len=0;
	
	public Response(){
		headInfo=new StringBuilder();
		content=new StringBuilder();
		len=0;
	}
	
	public Response(Socket client){
		this();
		try {
			bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			headInfo=null;
		}
	}
	
	public Response(OutputStream os){
		this();
		bw=new BufferedWriter(new OutputStreamWriter(os));

	}
	
	public Response print(String info){
		content.append(info);
		len+=info.getBytes().length;
		return this;
	}
	
	public Response println(String info){
		content.append(info).append(CRLF);
		len+=(info+CRLF).getBytes().length;
		return this;
	}
		
	
	
	//构建响应头
	private void createHeadInfo(int code){
		//1)http的响应行 HTTP协议版本 状态码 描述
		headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
		switch(code){
			case 200:
				headInfo.append("OK");
				break;
			case 404:
				headInfo.append("NOT FOUND");
			    break;
			case 500:
				headInfo.append("SERVER ERROR");
				break;
		}
		headInfo.append(CRLF);
		//2)http响应头
		headInfo.append("Date:").append(new Date()).append(CRLF);
		headInfo.append("Content-Type:text/html;charset=GBK").append(CRLF);
		headInfo.append("Content-Length").append(len).append(CRLF);
		headInfo.append(CRLF);
	}
	
	void pushToClient(int code) throws IOException{
		if(headInfo==null){
			code = 500;
		}
		createHeadInfo(code);
		bw.append(headInfo.toString());
		bw.append(content.toString());
		bw.flush();
	}
	public void close(){
		CloseUtil.closeIO(bw); 
	}
}
