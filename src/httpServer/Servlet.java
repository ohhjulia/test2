package httpServer;

import java.io.IOException;

import answer.Answer;

public class Servlet {
	public void service(Request req, Response rep){
		
			Integer answer=Answer.maxProduct(req.getParameterValue("I"));
			
			rep.println("<html><head><title>maxProduct</title>");
			rep.println("</head><body>");
			if(!req.getUrl().equals("/answer/")){
				rep.println("The resource not found");
			}
			else if(answer==null){
				rep.println("Please input more than three numbers");
			}else{
				rep.println("The maximum Product is£º").println(answer.toString());
			}
			rep.println("</body></html>");
		
			
	}
}
