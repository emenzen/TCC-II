package controller;
import model.bo.Mensagem;
import model.dao.MensagemDao;
import view.Simulador;

//controla a criação e envio de novas mensagens
public class EnvioMensagemThread extends Thread {
	private Mensagem mensagem;
	private Simulador pFormulario;
	private AtualizaProgressBarThread atualizaProgressBarThread;
	public boolean executando = true;

	public EnvioMensagemThread(Simulador pFormulario) {
		//this.mensagem = mensagem;
		this.pFormulario = pFormulario;
	}

	@Override
	public void run() {
		MensagemDao mensagemDao = new MensagemDao();
		while (executando) {
			try {
				
				atualizaProgressBarThread = new AtualizaProgressBarThread(pFormulario, "Criando mensagem");
				atualizaProgressBarThread.start();
				mensagem = new Mensagem();
				mensagem.criarMensagem();
				atualizaProgressBarThread.join();
				
				atualizaProgressBarThread = new AtualizaProgressBarThread(pFormulario, "Enviando mensagem");
				atualizaProgressBarThread.start();
				mensagem.setStatus(mensagem.enviarMensagem(mensagem));
				mensagem.gravarMensagem();
				atualizaProgressBarThread.join();
				
				
				pFormulario.txtCriadas.setText(String.valueOf(mensagemDao.getQtdMensagensCriadas()));
				pFormulario.txtEnviadas.setText(String.valueOf(mensagemDao.getQtdMensagensEnviadas()));
				pFormulario.txtPendentes.setText(String.valueOf(mensagemDao.getQtdMensagensPendentes()));

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			pFormulario.jProgressBar.setVisible(false);
			pFormulario.lblStatus.setText("");
		}
	}
}
