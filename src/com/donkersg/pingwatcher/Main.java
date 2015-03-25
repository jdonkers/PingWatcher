package com.donkersg.pingwatcher;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

import com.donkersg.pingwatcher.settings.SettingsState;
import com.donkersg.pingwatcher.settings.SettingsUtility;

public class Main {

	public static void main(String args[]) {
		@SuppressWarnings("unused")
		PingTrayIcon trayIcon;
		PingWatcher pingWatcher;
		SettingsState settings;

		settings = SettingsUtility.GetSettings();

		pingWatcher = new PingWatcher();
		pingWatcher.SetDomain(settings.domain);
		pingWatcher.SetSleepAmount(settings.pingWait);

		trayIcon = new PingTrayIcon(Main.createImage("/icon.png", "tray icon"), pingWatcher);

		pingWatcher.run();
	}

	// Obtain the image URL
	protected static Image createImage(String path, String description) {
		URL imageURL = Main.class.getResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		} else {
			return (new ImageIcon(imageURL, description)).getImage();
		}
	}
}