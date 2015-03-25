package com.donkersg.pingwatcher.settings;

public class SettingsState {

	public String domain;
	public String logDirectory;
	public int highMs;
	public int lowMs;
	public int pingWait;

	public SettingsState() {
		this.highMs = 250;
		this.lowMs = 80;
		this.pingWait = 1;
		this.domain = "google.com";
		this.logDirectory = "/";
	}
}
