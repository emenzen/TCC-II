package controller;

import java.util.ArrayList;

import model.bo.Mensagem;
import model.dao.MensagemDao;
import view.Simulador;

//reenvia as mensagens
public class ReprocessamentoThread extends Thread {
	private Simulador pFormulario;
	private AtualizaProgressBarThread atualizaProgressBarThread;
	public boolean executando = true;

	public ReprocessamentoThread(Simulador pFormulario) {
		this.pFormulario = pFormulario;
	}

	@Override
	public void run() {
		ArrayList<Mensagem> mensagensList = new ArrayList<Mensagem>();
		Mensagem mensagem = new Mensagem();
		MensagemDao mensagemDao = new MensagemDao();
		mensagensList = mensagemDao.getNaoEnviadas();
		int indice = 0;
		do {
			try {
				mensagensList.get(indice).setStatus(mensagem.enviarMensagem(mensagensList.get(indice)));
				mensagemDao.update(mensagensList.get(indice));
				atualizaProgressBarThread = new AtualizaProgressBarThread(pFormulario, "Reprocessando Mensagens");
				pFormulario.jProgressBar.setVisible(true);
				atualizaProgressBarThread.start();
				atualizaProgressBarThread.join();
				//Thread.sleep(40);
				pFormulario.txtReprocessadas.setText(String.valueOf(mensagemDao.getQtdMensagensReprocessadas()));
				pFormulario.txtEnviadas.setText(String.valueOf(mensagemDao.getQtdMensagensEnviadas()));
				pFormulario.txtPendentes.setText(String.valueOf(mensagemDao.getQtdMensagensPendentes()));
			}catch (Exception e1) {
				e1.printStackTrace();
			}
			pFormulario.jProgressBar.setVisible(false);
			pFormulario.lblStatus.setText("");
			indice++;
		} while (indice < mensagensList.size() && executando);
		pFormulario.jProgressBar.setVisible(false);
	}
}
