package com.donkersg.pingwatcher.graph;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.donkersg.pingwatcher.PingRecord;
import com.donkersg.pingwatcher.PingWatcher;
import com.donkersg.pingwatcher.settings.SettingsFrame;
import com.donkersg.pingwatcher.settings.SettingsState;
import com.donkersg.pingwatcher.settings.SettingsUtility;

public class GraphFrame extends JFrame implements ActionListener, ItemListener, Observer {

	private GraphCanvas canvasView;
	private GraphFrame thisFrame;

	private static final long serialVersionUID = 1L;

	public void RefreshSettings() {
		SettingsState settings = SettingsUtility.GetSettings();
		canvasView.setHighMsMark(settings.highMs);
		canvasView.setLowMsMark(settings.lowMs);
		canvasView.repaint();
	}

	public GraphFrame() {
		super("Ping Graph");

		thisFrame = this;

		canvasView = new GraphCanvas();

		this.setPreferredSize(new Dimension(600, 450));
		this.add(canvasView, BorderLayout.CENTER);

		this.setJMenuBar(createMenuBar());
		this.pack();

		this.RefreshSettings();

		this.setVisible(true);
	}

	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;

		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menuBar.add(menu);

		menuItem = new JMenuItem("Settings", KeyEvent.VK_T);

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsFrame settings = new SettingsFrame(thisFrame);
				settings.setVisible(true);
			}
		});

		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Exit", KeyEvent.VK_T);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		menu.add(menuItem);

		return menuBar;
	}

	public void actionPerformed(ActionEvent e) {

	}

	public void itemStateChanged(ItemEvent e) {

	}

	@Override
	public void update(Observable obj, Object arg) {

		if (obj instanceof PingWatcher) {
			PingWatcher watcher = (PingWatcher) obj;

			GraphData data = new GraphData();
			ArrayList<PingRecord> records = watcher.GetLastRecords(10);

			for (PingRecord record : records) {
				data.addPoint(new Point(record.date.get(Calendar.SECOND), record.ms));
			}

			canvasView.updateGraph(data);
		}
	}
}