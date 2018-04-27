package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

	File file;
	private BufferedReader bufferedReader;

	public FileUtils(String pathname) {
		this(new File(pathname));
	}

	public FileUtils(File file) {
		this.file = file;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String readLine() {
		try {
			return bufferedReader.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	public static FileUtils create(String pathname) {
		return new FileUtils(pathname);
	}
}
