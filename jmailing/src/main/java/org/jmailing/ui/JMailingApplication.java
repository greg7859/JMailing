package org.jmailing.ui;

import java.awt.EventQueue;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.jmailing.ui.about.AboutDialog;
import org.jmailing.ui.smtp.SmtpDialog;

public class JMailingApplication {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JMailingApplication window = new JMailingApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JMailingApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("JMailing");

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);

		JMenuItem quitMenuItem = new JMenuItem("Exit");
		quitMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				InputEvent.CTRL_MASK));
		fileMenu.add(quitMenuItem);
		
		JMenu mnConfiguration = new JMenu("Configuration");
		menuBar.add(mnConfiguration);
		
		JMenuItem mntmMail = new JMenuItem("SMTP Server");
		mntmMail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				SmtpDialog smtpDialog = new SmtpDialog();
				smtpDialog.setVisible(true);
			}
		});
		mnConfiguration.add(mntmMail);

		menuBar.add(Box.createHorizontalGlue());

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		menuBar.add(helpMenu);
//		JMenuItem helpMenuItem = new JMenuItem("Aide");
//		helpMenu.add(helpMenuItem);
		
		JMenuItem aboutMenuItem = new JMenuItem("About...");
		aboutMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				new AboutDialog().setVisible(true);
			}
		});
		helpMenu.add(aboutMenuItem);
		
//		Notifier notifieur = new Notifier(
//				"Bienvenu", 
//				"Merci d'utiliser JMailing pour vos envoi de mailing avec attachement PDF.", 
//				NotificationType.INFO
//		);
//		notifieur.start();
		
	}
}
