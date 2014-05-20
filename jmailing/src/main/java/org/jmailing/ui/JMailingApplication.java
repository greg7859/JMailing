package org.jmailing.ui;

import java.awt.EventQueue;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.lang.StringUtils;
import org.jmailing.injector.JMailingModule;
import org.jmailing.io.project.MailingProjectRetriever;
import org.jmailing.io.project.MailingProjectStorer;
import org.jmailing.io.project.ProjectNameList;
import org.jmailing.io.smtp.SmtpIO;
import org.jmailing.model.project.MailingProject;
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
		
		try {
			BufferedImage bufImg = ImageIO.read( ClassLoader.getSystemResource( "icons/app/mail.png" ) );
			frame.setIconImage(bufImg);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);
		// New Project
		JMenuItem newMenuItem = new JMenuItem("New project");
		newMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				MailingProjectPanel panel = injector
						.getInstance(MailingProjectPanel.class);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.setVisible(true);
			}
		});
		fileMenu.add(newMenuItem);
		// Load
		JMenuItem loadMenuItem = new JMenuItem("Load project");
		loadMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				ProjectNameList pList = injector
						.getInstance(ProjectNameList.class);
				Collection<String> list = pList.list();

				String[] projectNames = list.toArray(new String[list.size()]);

				String name = (String) JOptionPane.showInputDialog(frame,
						"Choose the project...", "Select the project to load",
						JOptionPane.QUESTION_MESSAGE, null, projectNames,
						projectNames[0]);
				if (StringUtils.isNotBlank(name)) {
					// FIXME : Check if the project does not exist
					MailingProjectRetriever storer = injector
							.getInstance(MailingProjectRetriever.class);
					try {
						storer.load(name);
						MailingProjectPanel panel = injector
								.getInstance(MailingProjectPanel.class);
						frame.getContentPane().removeAll();
						frame.getContentPane().add(panel);
						frame.setVisible(true);

					} catch (IOException e) {
						JOptionPane.showMessageDialog(frame,
								"Error during the loading", "Error",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			}
		});
		fileMenu.add(loadMenuItem);

		// Save
		JMenuItem saveMenuItem = new JMenuItem("Save project");
		saveMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				MailingProject project = injector
						.getInstance(MailingProject.class);
				save(project.getName());
			}
		});
		fileMenu.add(saveMenuItem);

		// Save
		JMenuItem saveAsMenuItem = new JMenuItem("Save As project");
		saveAsMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				save(null);
			}
		});
		fileMenu.add(saveAsMenuItem);

		fileMenu.add(new JSeparator());

		JMenuItem quitMenuItem = new JMenuItem("Exit");
		quitMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				System.exit(0);
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
				SmtpDialog smtpDialog = injector.getInstance(SmtpDialog.class);
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
	}

	private void save(String name) {
		if (name == null) {
			name = JOptionPane.showInputDialog(frame, "Name");
		}

		if (StringUtils.isNotBlank(name)) {
			// FIXME : Check if the project does not exist
			MailingProjectStorer storer = injector
					.getInstance(MailingProjectStorer.class);
			try {
				MailingProject project = injector
						.getInstance(MailingProject.class);
				project.setName(name);
				storer.save(name);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "Error during the save",
						"Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
}
