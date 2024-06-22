package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JProgressBar;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JTextField;


import controller.ListenerSimulador;
import model.dao.MensagemDao;


@SuppressWarnings("serial")
public class Simulador extends JFrame {
	public JDesktopPane DPane;
	private JPanel pnlMain;
	protected JPanel pnlCenter;
	public JButton btnEnviarMensagens, btnPararEnvio, btnReprocessarMensagens, btnSair;
	public JProgressBar jProgressBar;
	private JLabel lblMensagensCriadas, lblMensagensEnviadas, lblMensagensReprocessadas, lblMensagensPendentes;
	public JLabel lblStatus;
	private JLabel lblSimulacao;
	public JTextField txtCriadas, txtEnviadas, txtReprocessadas, txtPendentes;
	
	public static void main(String[] args) {
		Simulador sv = new Simulador();
		sv.setVisible(true);
		
	}
	
	public Simulador() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(JOptionPane.showConfirmDialog(DPane,"Deseja encerrar a aplicação?",
						"Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
			}
		});
		/*
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			// UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		}*/
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(600, 400);
		this.setTitle("Protótipo Simulador");

		
		pnlMain = new JPanel();
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlMain);
		pnlMain.setLayout(new BorderLayout(5, 5));
		
		//painel central
	    pnlCenter =  new JPanel();
	    pnlCenter.setBorder(new LineBorder(Color.GRAY));
	    pnlMain.add(pnlCenter, BorderLayout.CENTER);
	    GridBagLayout gridCentral = new GridBagLayout();
	    gridCentral.columnWidths = new int[]{0, 300, 200, 30, 0, 0};
	    gridCentral.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
	    //gridCentral.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	    gridCentral.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	    pnlCenter.setLayout(gridCentral);		
	    
	    AddObjGridBagLayout.restricoes.insets = new Insets(5, 5, 5, 5); //Construtor: Insets(int top, int left, int bottom, int right);
	    lblSimulacao = new JLabel("Protótipo Simulador");
	    lblSimulacao.setHorizontalAlignment(SwingConstants.CENTER);
	    lblSimulacao.setFont(new Font("Tahoma", Font.BOLD, 18));
	    AddObjGridBagLayout.addObjetoGridBagLayout(pnlCenter, lblSimulacao, 1, 0, 2);
	    
	    //label da barra de progresso
	    AddObjGridBagLayout.restricoes.anchor = GridBagConstraints.WEST;
	    lblMensagensCriadas = new JLabel("Mensagens Criadas:");
	    AddObjGridBagLayout.addObjetoGridBagLayout(pnlCenter, lblMensagensCriadas, 1, 1, 1);
	    lblMensagensEnviadas = new JLabel("Mensagens Enviadas:");
	    AddObjGridBagLayout.addObjetoGridBagLayout(pnlCenter, lblMensagensEnviadas, 1, 2, 1);
	    lblMensagensPendentes = new JLabel("Mensagens Pendentes:");
	    AddObjGridBagLayout.addObjetoGridBagLayout(pnlCenter, lblMensagensPendentes, 1, 3, 1);
	    lblMensagensReprocessadas = new JLabel("Mensagens Reprocessadas:");
	    AddObjGridBagLayout.addObjetoGridBagLayout(pnlCenter, lblMensagensReprocessadas, 1, 4, 1);

	    
	    lblStatus = new JLabel("");
	    AddObjGridBagLayout.addObjetoGridBagLayout(pnlCenter, lblStatus, 1, 5, 1);
	    
	    //Barra de progresso
	    //AddObjGridBagLayout.restricoes.fill = GridBagConstraints.BOTH;    
	    jProgressBar = new JProgressBar();
	    jProgressBar.setMaximum(10);
	    AddObjGridBagLayout.addObjetoGridBagLayout(pnlCenter, jProgressBar, 2, 5, 1);
	    jProgressBar.setVisible(false);
	    
        
	    //Campo de texto da barra de progresso
	    MensagemDao mensagemDao = new MensagemDao();
	    AddObjGridBagLayout.restricoes.insets = new Insets(5, 5, 5, 30);	
	    txtCriadas = new JTextField();
	    txtCriadas.setFont(new Font("Tahoma", Font.BOLD, 12));
	    txtCriadas.setText(String.valueOf(mensagemDao.getQtdMensagensCriadas()));
	    txtCriadas.setEditable(false);
	    AddObjGridBagLayout.addObjetoGridBagLayout(pnlCenter, txtCriadas, 2, 1, 1);    	     

	    txtEnviadas = new JTextField();
	    txtEnviadas.setFont(new Font("Tahoma", Font.BOLD, 12));
	    txtEnviadas.setText(String.valueOf(mensagemDao.getQtdMensagensEnviadas()));
	    txtEnviadas.setEditable(false);
	    AddObjGridBagLayout.addObjetoGridBagLayout(pnlCenter, txtEnviadas, 2, 2, 1);    

	    txtPendentes = new JTextField();
	    txtPendentes.setFont(new Font("Tahoma", Font.BOLD, 12));
	    txtPendentes.setText(String.valueOf(mensagemDao.getQtdMensagensPendentes()));
	    txtPendentes.setEditable(false);
	    AddObjGridBagLayout.addObjetoGridBagLayout(pnlCenter, txtPendentes, 2, 3, 1);    	      

	    txtReprocessadas = new JTextField();
	    txtReprocessadas.setFont(new Font("Tahoma", Font.BOLD, 12));
	    txtReprocessadas.setText(String.valueOf(mensagemDao.getQtdMensagensReprocessadas()));
	    txtReprocessadas.setEditable(false);
	    AddObjGridBagLayout.addObjetoGridBagLayout(pnlCenter, txtReprocessadas, 2, 4, 1);  
	    
		// Parte inferior do painel
		JPanel pnlBottom = new JPanel();
		pnlBottom.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlMain.add(pnlBottom, BorderLayout.SOUTH);

		btnEnviarMensagens = new JButton("Enviar mensagens");
		btnEnviarMensagens.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlBottom.add(btnEnviarMensagens);
		
		btnPararEnvio  = new JButton("Parar envio");
		btnPararEnvio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlBottom.add(btnPararEnvio);
		
		btnReprocessarMensagens = new JButton("Reprocessar mensagens");
		btnReprocessarMensagens.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlBottom.add(btnReprocessarMensagens);
		
		btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlBottom.add(btnSair);
		
		// aqui é setado o controller desse frame
		ListenerSimulador listener = new ListenerSimulador(this);
		btnEnviarMensagens.addActionListener(listener);
		btnPararEnvio.addActionListener(listener);
		btnReprocessarMensagens.addActionListener(listener);
		btnSair.addActionListener(listener);
		        
	}
}
