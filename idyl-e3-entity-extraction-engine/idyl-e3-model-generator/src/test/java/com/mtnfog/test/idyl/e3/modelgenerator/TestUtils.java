package com.mtnfog.test.idyl.e3.modelgenerator;

import java.io.File;
import java.io.IOException;

public class TestUtils {

	public static File getRandomFile(String prefix, String extension) throws IOException {
		
		File tempFile = File.createTempFile(prefix, "." + extension);
		tempFile.deleteOnExit();
		
		return tempFile;
		
	}
	
	public static File getRandomModelFile() throws IOException {
		
		File tempFile = File.createTempFile("model", ".bin");
		tempFile.deleteOnExit();
		
		return tempFile;
		
	}
	
}
