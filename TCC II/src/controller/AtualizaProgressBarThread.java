package controller;

import view.Simulador;
//atualiza a barra de progresso
public class AtualizaProgressBarThread extends Thread {
	private Simulador pFormulario;
	private String texto;

	public AtualizaProgressBarThread(Simulador pFormulario, String texto) {
		this.pFormulario = pFormulario;
		this.texto = texto;
	}

	@Override
	public void run() {
		try {
			pFormulario.lblStatus.setText(texto);
			pFormulario.jProgressBar.setVisible(true);
			for(int i=0; i<=10; i++) {
				Thread.sleep(100);
				
				pFormulario.jProgressBar.setValue(i);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
