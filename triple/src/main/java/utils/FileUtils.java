package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	File inputFile;
	File outputFile;

	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;

	public FileUtils(String pathname) {
		this(new File(pathname));
	}

	public FileUtils(File file) {
		this.inputFile = file;
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

	public void write(String pathname, String output) {
		if (bufferedWriter == null) {
			try {
				File f = new File(pathname);
				bufferedWriter = new BufferedWriter(new FileWriter(f));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			bufferedWriter.write(output);
			bufferedWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
