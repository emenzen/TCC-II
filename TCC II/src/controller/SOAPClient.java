package controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import model.bo.Request;
import model.bo.Response;

//realiza o envio de mensagem via post e recebe o retorno do WebService
public class SOAPClient {
	private Response retorno = new Response();

	public Response post(Request request) throws Exception {
		// URL do endpoint
		//URL url = new URL("http://127.0.0.1:8080/queues");
		URL url = new URL("http://notebook2:8080/queues");

		// Criar a conexão HTTP
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		connection.setDoOutput(true);

		// Define o tempo limite de conexão e de leitura em ms
		connection.setConnectTimeout(1000);
		connection.setReadTimeout(1000);

		System.out.println("Request: " + request.getRequest());

		// Enviar a requisição
		try (OutputStream os = connection.getOutputStream()) {
			byte[] input = request.getRequest().getBytes("utf-8");
			os.write(input, 0, input.length);
		} catch (Exception e) {
			retorno.setMessage(e.getStackTrace().toString());
			retorno.setSucess(0);
			e.printStackTrace();
			return retorno;
		}

		// Ler a resposta
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			} 
			//System.out.println("Resposta do servidor: " + response.toString());

			// Cria um DocumentBuilder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Faz o parsing do XML para obter o Document
			Document document = builder.parse(new java.io.ByteArrayInputStream(response.toString().getBytes("utf-8")));

			// Obtém o elemento 'erro'
			NodeList nodeList = document.getElementsByTagName("success");
			if (nodeList.getLength() > 0) {
				Element nodeElement = (Element) nodeList.item(0);
				int success = Integer.valueOf(nodeElement.getTextContent());
				//System.out.println("Valor da tag success: " + erroElement.getTextContent());
				retorno.setSucess(success);
				//System.out.println("Valor da tag success: " + retorno.getSucess());

				nodeList = document.getElementsByTagName("message");
				nodeElement = (Element) nodeList.item(0);
				retorno.setMessage(nodeElement.getTextContent());
				//System.out.println("Valor da tag message: " + retorno.getMessage());

			} else {
				throw new Exception("XML de retorno não está no formato esperado!");
			}
		} catch (Exception e) {
			retorno.setMessage(e.getStackTrace().toString());
			retorno.setSucess(0);
			e.printStackTrace();
			return retorno;
		}

		// Fechar a conexão
		connection.disconnect();
		return retorno;
	}
}
