package br.edu.judge.forcode.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class ProcessUtils {

	/**
	 * Set of utilities.
	 * 
	 * @author rerissondaniel
	 */

	private ProcessUtils() {
	}

	public static void killUnixProcess(Process process)
			throws IllegalArgumentException, NoSuchFieldException,
			SecurityException, IllegalAccessException, IOException {
		/**
		 * This method just works in an Unix environment.
		 * 
		 * @param process
		 *            An object representing the process to be killed.
		 * 
		 * */

		if (process.getClass().getName().equals("java.lang.UNIXProcess")) {
			Class<?> cl = process.getClass();
			Field field = cl.getDeclaredField("pid");
			field.setAccessible(true);

			Object pidObject = field.get(process);
			Integer pid = (Integer) pidObject;

			String command = "kill -9 " + (pid + 3);
			Runtime.getRuntime().exec(command);
		} else {
			throw new IllegalArgumentException("Needs to be a UNIXProcess");
		}
	}

	public static void giveExecutePermission(File file) throws IOException,
			InterruptedException {
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec("chmod +x " + file.getAbsolutePath());
		process.waitFor();
	}

	public static void runScript(File script) throws IOException,
			InterruptedException {
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(script.getAbsolutePath());
		process.waitFor();
	}
}