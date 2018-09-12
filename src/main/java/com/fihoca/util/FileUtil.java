package com.fihoca.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {

	public static File createFile(String fileName) throws IOException {

		File file = new File(fileName);

		try {

			if (!file.exists()) {
				file.createNewFile();
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

		return file;
	}

}
