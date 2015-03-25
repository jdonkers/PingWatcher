package com.donkersg.pingwatcher.settings;

import java.util.prefs.Preferences;

public class SettingsUtility {

	private static SettingsState settingsState;

	private static final String SETTINGS_NODE = "com/donkersg/pingwatcher/prefs";
	private static Preferences prefs = Preferences.userRoot().node(SETTINGS_NODE);

	public static void SaveSettings(SettingsState settings) {

		settingsState = settings;
		prefs.putInt("lowMs", settingsState.lowMs);
		prefs.putInt("highMs", settingsState.highMs);
		prefs.putInt("pingWait", settingsState.pingWait);
		prefs.put("domain", settingsState.domain);
	}

	public static SettingsState GetSettings() {

		if (settingsState == null) {
			LoadSettings();
		}

		return settingsState;
	}

	private static void LoadSettings() {
		settingsState = new SettingsState();

		settingsState.lowMs = prefs.getInt("lowMs", settingsState.lowMs);
		settingsState.highMs = prefs.getInt("highMs", settingsState.highMs);
		settingsState.domain = prefs.get("domain", settingsState.domain);
		settingsState.pingWait = prefs.getInt("pingWait", settingsState.pingWait);
	}
}
