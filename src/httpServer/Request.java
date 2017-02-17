package httpServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * ��װrequest
 * @author hp
 *
 */
public class Request {
	//����ʽ
	private String method;
	//������Դ
	private String url;
	//�������
	private Map<String,List<String>> parameterMap;
	
	public static final String CRLF="\r\n";
	private InputStream is;
	private String requestInfo;
	
	public Request(){
		method="";
		url="";
		parameterMap=new HashMap<String,List<String>>();
		requestInfo="";
	}
	public Request(InputStream is){
		this();
		this.is=is;
		try {
			byte[] data=new byte[20480];
			int len=is.read(data);
			requestInfo=new String(data,0,len);
		} catch (IOException e) {
			return;
		}
		//����ͷ��Ϣ
		parseRequestInfo();
	}
	private void parseRequestInfo() {
		if(null==requestInfo || (requestInfo=requestInfo.trim()).equals(""))
			return;
		//��������ȡ������ʽ������·�����������
		
		String paramString="";//�����������
		String firstLine = requestInfo.substring(0,requestInfo.indexOf(CRLF));
		int idx=requestInfo.indexOf("/");
		this.method=firstLine.substring(0, idx).trim();
		String urlStr=firstLine.substring(idx, firstLine.indexOf("HTTP/")).trim();;
		if(this.method.equals("GET")){
			if(urlStr.contains("?")){//�Ƿ���ڲ���
				String[] urlArray=urlStr.split("\\?");
				this.url=urlArray[0];
				paramString=urlArray[1];
			}else{
				this.url=urlStr;
			}
		}else if(this.method.equals("POST")){
			this.url=urlStr;
			paramString=requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
		}
		//������Ĳ�����װ��map��
		if(paramString.equals("")){
			return;
		}else{
			parseParams(paramString);
		}
		
/*		System.out.println(paramString);
		System.out.println(url);
		*/
	}
	
	public String getUrl(){
		return url;
	}
	
	private void parseParams(String paramString) {
		//�ָ����
		StringTokenizer token=new StringTokenizer(paramString,"&");
		while(token.hasMoreTokens()){
			String keyValue=token.nextToken();
			String[] keyValues=keyValue.split("=");
			if(keyValues.length==1){
				keyValues=Arrays.copyOf(keyValues, 2);
				keyValues[1]=null;
			}
		//ת����map
			String key = keyValues[0].trim();
			String value=(null==keyValues[1])?null:keyValues[1].trim();
			if(!parameterMap.containsKey(key)){
				parameterMap.put(key,new ArrayList<String>());
			}
			List<String> values=parameterMap.get(key);
			values.add(value);
		}
		
	}
	//����ҳ������ƣ���ȡ��Ӧ��ֵ
	public String[] getParameterValues(String name){
		List<String> values=null;
		if((values=parameterMap.get(name))==null){
			return null;
		}else{
			return values.toArray(new String[0]);
		}
	
	}
	public String getParameterValue(String name){
		String[] values=getParameterValues(name);
		if(values==null)
			return null;
		return values[0];
		
	}

}
