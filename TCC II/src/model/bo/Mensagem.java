package model.bo;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import controller.SOAPClient;
import model.dao.MensagemDao;

@XStreamAlias("que:post")
public class Mensagem extends Body {
	@XStreamOmitField //para omitir os campos id e status no XML
	private int id, status;
	private String queuename, message;

	public Mensagem() {
		super();
		this.queuename = "";
		this.message = "";
	}

	public Mensagem(String queuename, String message, int status) {
		this.queuename = queuename;
		this.message = message;
		this.status = status;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getQueuename() {
		return queuename;
	}

	public void setQueuename(String queuename) {
		this.queuename = queuename;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	private String geraMensagem() {
		Obra tcc = new Obra("Trabalho de Conclusão de Curso", "Proposta de um mecanismo de recuperação de mensagens enviadas para um controlador de filas", "Eduardo Menzen", 2024);
		Gson gson = new Gson();
		String jsonString = gson.toJson(tcc); //converte o JSON em String
		return jsonString;
	}
	
	public void criarMensagem() {
		this.setQueuename("queueService");
		this.setMessage(geraMensagem());
	}
		
	public int enviarMensagem(Mensagem mensagem) {
       
        // Realizando a chamado Soap
        SOAPClient cli = new SOAPClient();
        Response retorno = new Response();
        Request request = new Request();
        request.setRequest(mensagem);
        
        try {
			retorno = cli.post(request);
			this.status = retorno.getSucess();
			System.out.println("Valor da tag success: " + retorno.getSucess());
			System.out.println("Valor da tag message: " + retorno.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return this.status;		
	}
	
	public void gravarMensagem() {
		Mensagem mensagem = new Mensagem(this.queuename, this.message, this.status);
		
		MensagemDao mensagemDao = new MensagemDao();
		mensagemDao.insert(mensagem);
	}
	
	public void reprocessarMensagem() {
		ArrayList<Mensagem> mensagemList = new ArrayList<Mensagem>();
		MensagemDao mensagemDao = new MensagemDao();
		//busca mensagem não enviadas
		mensagemList = mensagemDao.getNaoEnviadas();
		int indice = mensagemList.size();
		while(indice > 0) {
			indice--;
			 System.out.println(" mensagemList.size(): " +  mensagemList.size()+ " teste: " + mensagemList.get(indice).geraMensagem());
		}
		System.out.println(" mensagemList.size(): " +  mensagemList.size());
	}
}
