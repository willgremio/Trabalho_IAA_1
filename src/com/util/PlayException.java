package com.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlayException extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private boolean yes = false;
	private boolean no = false;
	private int retorno = -1;
	
	/**
	 * @param msg - Mensagem
	 * @param title - Titulo
	 * @param tipo - Tipo ( ok = comfirmar ), ( sn = Com botões Sim ou Não )
	 */
	public PlayException(String msg, String title, String tipo) {
		setBounds(100, 100, 655, 101);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblTeste = new JLabel(msg);
			contentPanel.add(lblTeste);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{

				if (tipo.equalsIgnoreCase("ok")) {

					JButton okButton = new JButton("OK");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							retorno = JOptionPane.OK_OPTION;
							dispose();
						}
					});
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);

				} else if (tipo.equalsIgnoreCase("sn")) {

					JButton btnNewButton = new JButton("Sim");
					btnNewButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							retorno = JOptionPane.YES_OPTION;
							dispose();
						}
					});
					buttonPane.add(btnNewButton);

					JButton btnNo = new JButton("Não");
					btnNo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							retorno = JOptionPane.NO_OPTION;
							dispose();
						}
					});
					buttonPane.add(btnNo);
				}
			}

		}
		setLocationRelativeTo(null);
		setResizable(false);
		getRootPane().setDefaultButton(null);
		setTitle(title);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setVisible(true);

	}
	
	public static int showDlg(String msg, String title, String tipo) {
		PlayException oPlayException = new PlayException(msg, title, tipo);
		return oPlayException.retorno;
	}

}
