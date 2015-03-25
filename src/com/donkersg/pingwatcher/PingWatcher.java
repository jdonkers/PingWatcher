package com.donkersg.pingwatcher;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;

import com.donkersg.pingwatcher.util.PingUtility;

public class PingWatcher extends Observable implements Runnable {

	public ArrayList<PingRecord> records;

	private int sleepAmount;
	private String domain;

	private final int MS_IN_SECOND = 1000;
	private final int DEFAULT_SLEEP = 1;

	private boolean logData;

	private String logDirectory;

	public PingWatcher() {
		this.sleepAmount = DEFAULT_SLEEP;
		this.logData = true;
		this.domain = "localhost";
		this.logDirectory = "\\";
		records = new ArrayList<PingRecord>();
	}

	public void updateLog(PingRecord newRecord) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(logDirectory + "PingLog-" + getDate() + ".txt", true));
			bw.write(newRecord.toString() + "\t" + this.domain);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getDate() {
		Calendar calendar = new GregorianCalendar();
		String month = "" + (calendar.get(Calendar.MONTH) + 1);
		String day = "" + calendar.get(Calendar.DAY_OF_MONTH);
		String year = "" + calendar.get(Calendar.YEAR);

		return year + "." + month + "." + day;
	}

	/**
	 * Returns all ping records that occurred in the past specified number of minutes.
	 * 
	 * @param minutes
	 * @return
	 */
	public ArrayList<PingRecord> GetLastRecords(int minutes) {

		ArrayList<PingRecord> returnRecords;
		int totalRecords;
		int recordsIncluded;
		int firstRecordId;

		if (minutes < 0) {
			throw new IllegalArgumentException("Minutes must be positive");
		}

		returnRecords = new ArrayList<PingRecord>();

		recordsIncluded = (int) Math.round(sleepAmount * MS_IN_SECOND * 0.06 * minutes);
		totalRecords = records.size() - 1;
		firstRecordId = totalRecords - recordsIncluded;

		if (firstRecordId < 0) {
			firstRecordId = 0;
		}

		for (int x = firstRecordId; x < totalRecords; x++) {
			returnRecords.add(records.get(x));
		}

		return returnRecords;
	}

	@Override
	public void run() {
		int responseMs;
		PingRecord newRecord;

		while (true) {
			responseMs = PingUtility.pingHost(this.domain);
			newRecord = new PingRecord(Calendar.getInstance(), responseMs);

			records.add(newRecord);

			this.setChanged();
			this.notifyObservers();

			if (this.logData) {
				this.updateLog(newRecord);
			}

			try {
				Thread.sleep(sleepAmount * MS_IN_SECOND);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}

	public void SetSleepAmount(int sleepAmount) {
		this.sleepAmount = (sleepAmount < 0) ? DEFAULT_SLEEP : sleepAmount;
	}

	public int GetSleepAmount() {
		return this.sleepAmount;
	}

	public void SetDomain(String domain) {
		this.domain = domain;
	}

	public String GetDomain() {
		return this.domain;
	}
}
