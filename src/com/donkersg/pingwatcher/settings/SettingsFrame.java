package com.donkersg.pingwatcher.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import com.donkersg.pingwatcher.graph.GraphFrame;
import com.donkersg.pingwatcher.util.PingUtility;

public class SettingsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTextField domain;
	private JSpinner spinnerHighAmount;
	private JSpinner spinnerLowAmount;
	private JSpinner spinnerPingTiming;

	GraphFrame callingFrame;

	public SettingsFrame(GraphFrame callingFrame) {
		this.callingFrame = callingFrame;
		createAndShowGUI();
		this.setVisible(true);
	}

	public SettingsFrame() {
		this.callingFrame = null;
		createAndShowGUI();
		this.setVisible(true);
	}

	public void createAndShowGUI() {

		setBounds(100, 100, 297, 321);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblDomainOrIp = new JLabel("Domain or IP to Ping:");

		SettingsState settings = SettingsUtility.GetSettings();
		spinnerHighAmount = new JSpinner(new SpinnerNumberModel(settings.highMs, 30, 5000, 1));
		spinnerPingTiming = new JSpinner(new SpinnerNumberModel(settings.pingWait, 1, 30, 1));
		spinnerLowAmount = new JSpinner(new SpinnerNumberModel(settings.lowMs, 0, 400, 1));
		domain = new JTextField();

		domain.setColumns(40);

		final JLabel lblConnection = new JLabel("");
		JLabel lblHighLatencyAmount = new JLabel("High Latency Amount (in ms)");
		JLabel lblLowLatencyAmount = new JLabel("Low Latency Amount (in ms)");

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				SettingsState settings = SettingsUtility.GetSettings();

				settings.domain = domain.getText().toString();
				settings.highMs = (int) spinnerHighAmount.getValue();
				settings.lowMs = (int) spinnerLowAmount.getValue();
				settings.pingWait = (int) spinnerPingTiming.getValue();

				SettingsUtility.SaveSettings(settings);

				if (callingFrame != null) {
					callingFrame.RefreshSettings();
				}

				setVisible(false);
			}
		});

		JButton btnTestConnection = new JButton("Test Connection");
		btnTestConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int value = PingUtility.pingHost(domain.getText().toString());

				if (value == 0) {
					lblConnection.setText("Failed to connect");
				} else {
					lblConnection.setText("Success! (" + value + "ms)");
				}
			}
		});

		domain.setText(settings.domain);

		JLabel lblPingEveryin = new JLabel("Ping every (in seconds):");

		// ------------------------------------------------
		// Content Pane Generated use JAVA Swing Generator.
		// ------------------------------------------------
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addGroup(
										gl_contentPane
												.createParallelGroup(Alignment.LEADING)
												.addComponent(lblDomainOrIp)
												.addGroup(
														gl_contentPane
																.createSequentialGroup()
																.addComponent(btnTestConnection)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(lblConnection, GroupLayout.DEFAULT_SIZE,
																		153, Short.MAX_VALUE))).addContainerGap())
				.addComponent(domain, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblPingEveryin).addContainerGap())
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addComponent(spinnerPingTiming, GroupLayout.PREFERRED_SIZE, 69,
										GroupLayout.PREFERRED_SIZE).addContainerGap())
				.addGroup(
						gl_contentPane.createSequentialGroup()
								.addComponent(lblHighLatencyAmount, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
								.addContainerGap())
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addComponent(spinnerHighAmount, GroupLayout.PREFERRED_SIZE, 69,
										GroupLayout.PREFERRED_SIZE).addContainerGap())
				.addGroup(
						gl_contentPane.createSequentialGroup()
								.addComponent(lblLowLatencyAmount, GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
								.addContainerGap())
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addComponent(spinnerLowAmount, GroupLayout.PREFERRED_SIZE, 69,
										GroupLayout.PREFERRED_SIZE).addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup().addComponent(btnOk).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addComponent(lblDomainOrIp)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(domain, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnTestConnection)
										.addComponent(lblConnection))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblPingEveryin)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(spinnerPingTiming, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblHighLatencyAmount)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(spinnerHighAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblLowLatencyAmount)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(spinnerLowAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnOk).addContainerGap(32, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}
}
