package org.jmailing.ui;

import java.awt.EventQueue;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jmailing.injector.JMailingModule;
import org.jmailing.io.smtp.SmtpIO;
import org.jmailing.model.project.MailingProject;
import org.jmailing.model.smtp.Smtp;
import org.jmailing.service.mail.EmailService;
import org.jmailing.ui.about.AboutDialog;
import org.jmailing.ui.project.MailingProjectPanel;
import org.jmailing.ui.smtp.SmtpDialog;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class JMailingApplication {

	private JFrame frame;
	Injector injector = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Injector injector = Guice
							.createInjector(new JMailingModule());
					try {
						SmtpIO io = injector.getInstance(SmtpIO.class);
						io.load();

					} catch (IOException e) {

					}
					JMailingApplication window = new JMailingApplication();
					window.injector = injector;
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

		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
//				MailingProject project = injector
//						.getInstance(MailingProject.class);
//				MailingProjectPanel panel = new MailingProjectPanel(project);
//				frame.getContentPane().add(panel);
//				frame.setVisible(true);
				MailingProjectPanel panel = injector
					.getInstance(MailingProjectPanel.class);
				frame.getContentPane().add(panel);
				frame.setVisible(true);
			}
		});
		fileMenu.add(newMenuItem);
		fileMenu.add(new JSeparator());

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

		JMenuItem mntmMail = new JMenuItem("SMTP Settings");
		mntmMail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Smtp smtp = injector.getInstance(Smtp.class);
				SmtpIO smtpIO = injector.getInstance(SmtpIO.class);
				EmailService emailSvc = injector
						.getInstance(EmailService.class);
				SmtpDialog smtpDialog = new SmtpDialog(smtp, smtpIO, emailSvc);
				smtpDialog.setVisible(true);
			}
		});
		mnConfiguration.add(mntmMail);

		menuBar.add(Box.createHorizontalGlue());

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		menuBar.add(helpMenu);
		// JMenuItem helpMenuItem = new JMenuItem("Aide");
		// helpMenu.add(helpMenuItem);

		JMenuItem aboutMenuItem = new JMenuItem("About...");
		aboutMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				new AboutDialog().setVisible(true);
			}
		});
		helpMenu.add(aboutMenuItem);

		// Notifier notifieur = new Notifier(
		// "Bienvenu",
		// "Merci d'utiliser JMailing pour vos envoi de mailing avec attachement PDF.",
		// NotificationType.INFO
		// );
		// notifieur.start();

	}
}
