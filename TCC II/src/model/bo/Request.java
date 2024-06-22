package model.bo;

import com.thoughtworks.xstream.XStream;

public class Request {
	String request;
	
	public Request() {
		super();
		this.request = "";
	}

	public String getRequest() {
		return request;
	}
	
	public void setRequest(Mensagem mensagem) {
        // Criando instância do XStream
        XStream xStream = new XStream();
        
        // Definindo aliases para os elementos
        xStream.processAnnotations(Body.class);
        xStream.processAnnotations(Mensagem.class);
        
        // Convertendo para XML
        Body body = new Body();
        body.setPost(mensagem);
        
        //convertendo para String
        String xml = xStream.toXML(body); 
		
        // Corpo da requisição
        this.request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:que=\"http://queues/\">\n"
                + "<soapenv:Header/>\n"
                + xml + "\n"
                + "</soapenv:Envelope>";
	}
}
