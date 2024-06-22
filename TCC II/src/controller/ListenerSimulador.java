package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.Simulador;

//controla o comportamento do botões da tela do simulador
public class ListenerSimulador implements ActionListener {
	private Simulador pFormulario;
	private EnvioMensagemThread envioMensagemThread;
	private ReprocessamentoThread reprocessamentoThread;

	public ListenerSimulador(Simulador pFormulario) {
		super();
		this.pFormulario = pFormulario;
	}
	
	public void actionPerformed(ActionEvent e) {
		Object origem = e.getSource();
		if(origem == pFormulario.btnEnviarMensagens) {
			//envia várias mensagens via thread
			envioMensagemThread = new EnvioMensagemThread(pFormulario);
			envioMensagemThread.start();

		} else if(origem == pFormulario.btnPararEnvio) {
			envioMensagemThread.executando = false;			

		} else if(origem == pFormulario.btnReprocessarMensagens) {
			if(JOptionPane.showConfirmDialog(pFormulario,"Confirma o reprocessamento das mensagens?",
					"Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			{

				reprocessamentoThread = new ReprocessamentoThread(pFormulario);
				reprocessamentoThread.start();
				//JOptionPane.showMessageDialog(pFormulario, "Fechamento do dia realizado com sucesso.");
			}

		} else { //origem == pFormulario.btnSair
			if(JOptionPane.showConfirmDialog(pFormulario,"Deseja encerrar a aplicação?",
					"Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				try {
					envioMensagemThread.executando = false;
				}catch(Exception e1) {}

			try {
				reprocessamentoThread.executando = false;
			}catch(Exception e1) {}

			pFormulario.dispose();
		}
	}
}
