package de.dis2011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Kleine Helferklasse zum Einlesen von Formulardaten
 */
public class FormUtil {
	/**
	 * Liest einen String vom standard input ein
	 * 
	 * @param label
	 *            Zeile, die vor der Eingabe gezeigt wird
	 * @return eingelesene Zeile
	 */
	public static String readString(String label) {
		String ret = null;
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));

		try {
			System.out.print(label + ": ");
			ret = stdin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * Liest einen Integer vom standard input ein
	 * 
	 * @param label
	 *            Zeile, die vor der Eingabe gezeigt wird
	 * @return eingelesener Integer
	 */
	public static int readInt(String label) {
		int ret = 0;
		boolean finished = false;

		while (!finished) {
			String line = readString(label);

			try {
				ret = Integer.parseInt(line);
				finished = true;
			} catch (NumberFormatException e) {
				System.err.println("Invalid Input : Please give an integer!");
			}
		}

		return ret;
	}

	public static boolean readBoolean(String label) {
		String line = readString(label);
		return line.equals("y");
	}

	public static double readDouble(String label) {
		double ret = 0;
		boolean finished = false;

		while (!finished) {
			String line = readString(label);

			try {
				ret = Double.parseDouble(line);
				finished = true;
			} catch (NumberFormatException e) {
				System.err
						.println("Invalid Input : Please give a float number!");
			}
		}

		return ret;
	}

	public static Date readDate(String label) {
		Date ret = null;
		boolean finished = false;
		final DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM,
				Locale.GERMANY);

		while (!finished) {
			String line = readString(label);

			try {
				ret = new Date(df.parse(line).getTime());
				finished = true;
			} catch (ParseException e) {
				System.err
						.println("Invalid Input : Please give a date(DD.MM.YYYY)!");
			}
		}

		return ret;
	}
}
