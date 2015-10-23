package br.edu.judge.forcode.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtils {

	// Returns true if the Files are different
	public static boolean fileDiff(File directory, File fileOne, File fileTwo)
			throws IOException, InterruptedException {

		File script = new File(directory.getPath() + "/diff.sh");

		FileUtils.addContent(script, "diff " + fileOne.getAbsolutePath() + " "
				+ fileTwo.getAbsolutePath() + " > " + directory.getPath()
				+ "/diff.txt");

		ProcessUtils.giveExecutePermission(script);
		ProcessUtils.runScript(script);

		File diff = new File(directory.getPath() + "/diff.txt");

		return diff.length() > 0;
	}

	public static void addContent(File file, String content) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file)));
		writer.write(content);
		writer.close();
	}
}
