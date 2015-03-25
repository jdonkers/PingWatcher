package com.donkersg.pingwatcher;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.donkersg.pingwatcher.graph.GraphFrame;
import com.donkersg.pingwatcher.settings.SettingsFrame;

public class PingTrayIcon extends TrayIcon {

	public final PingWatcher pingWatcher;

	public PingTrayIcon(Image image, final PingWatcher pWatcher) {
		super(image);

		this.pingWatcher = pWatcher;
		final PopupMenu popup = new PopupMenu();
		final SystemTray tray = SystemTray.getSystemTray();

		this.setToolTip("Ping-Watcher");
		this.setImageAutoSize(true);

		MenuItem graphItem = new MenuItem("Graph");
		MenuItem settingsItem = new MenuItem("Settings");
		MenuItem exitItem = new MenuItem("Exit");

		popup.add(graphItem);
		popup.add(settingsItem);
		popup.addSeparator();
		popup.add(exitItem);

		this.setPopupMenu(popup);

		try {
			tray.add(this);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
			return;
		}

		final TrayIcon trayIcon = this;

		graphItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphFrame graph = new GraphFrame();
				pingWatcher.addObserver(graph);
			}
		});

		settingsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SettingsFrame();
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				System.exit(0);
			}
		});
	}
}