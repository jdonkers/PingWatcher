package com.donkersg.pingwatcher;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PingRecord {

	public Calendar date;
	public int ms;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	public PingRecord(Calendar date, int ms) {
		this.date = date;
		this.ms = ms;
	}

	@Override
	public String toString() {
		return dateFormat.format(this.date.getTime()) + "\t" + ms + "ms";
	}
}