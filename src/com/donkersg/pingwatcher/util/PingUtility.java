package com.donkersg.pingwatcher.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingUtility {

	private static Pattern windowsLatencyPattern = Pattern.compile("Average = ([0-9]+)ms");

	private static int getLatencyWindows(String input) {

		Matcher matcher;
		String matchedValue;

		matcher = windowsLatencyPattern.matcher(input);

		if (matcher.find()) {

			// If successful, the ping command should return a string containing
			// the millisecond return time. For our purposes, we take the
			// average, as matched by the regular expression group.
			matchedValue = matcher.group(1);

			return Integer.parseInt(matchedValue);

		} else {
			throw new UnsupportedOperationException();
		}
	}

	public static int pingHost(String host) {
		try {
			String cmd = "";

			if (System.getProperty("os.name").startsWith("Windows")) {
				// For Windows
				cmd = "cmd /c ping " + host + " -n 1";
			} else {
				// For Linux and OSX
				cmd = "ping -c 1 " + host;
			}

			Process myProcess = Runtime.getRuntime().exec(cmd);
			myProcess.waitFor();

			if (myProcess.exitValue() == 0) {

				BufferedReader input = new BufferedReader(new InputStreamReader(myProcess.getInputStream()));

				String response = "";
				String line = "";

				while ((line = input.readLine()) != null) {
					response += line;
				}

				return getLatencyWindows(response);

			} else {

				// FAILED
				return 0;

			}

		} catch (Exception e) {

			e.printStackTrace();
			return 0;
		}
	}
}
