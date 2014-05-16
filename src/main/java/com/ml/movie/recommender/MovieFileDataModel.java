package com.ml.movie.recommender;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.regex.Pattern;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.common.iterator.FileLineIterable;

import com.google.common.base.Charsets;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.Resources;

public class MovieFileDataModel extends FileDataModel {

	private static final long serialVersionUID = 1L;
	private static final String COLON_DELIMTER = "\t";
	private static final Pattern COLON_DELIMITER_PATTERN = Pattern.compile(COLON_DELIMTER);

	public MovieFileDataModel() throws IOException {
		this(readResourceToTempFile("user_ratedmovies-timestamps.dat"));
	}

	/**
	 * @param ratingsFile
	 *            GroupLens ratings.dat file in its native format
	 * @throws IOException
	 *             if an error occurs while reading or writing files
	 */
	public MovieFileDataModel(File ratingsFile) throws IOException {
		super(convertGLFile(ratingsFile));
	}

	private static File convertGLFile(File originalFile) throws IOException {
		// Now translate the file; remove commas, then convert "::" delimiter to comma
		File resultFile = new File(new File(System.getProperty("java.io.tmpdir")), "ratings.txt");
		if (resultFile.exists()) {
			resultFile.delete();
		}
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(resultFile), Charsets.UTF_8);
			for (String line : new FileLineIterable(originalFile, false)) {
				int lastDelimiterStart = line.lastIndexOf(COLON_DELIMTER);
				if (lastDelimiterStart < 0) {
					throw new IOException("Unexpected input format on line: " + line);
				}
				String subLine = line.substring(0, lastDelimiterStart);
				String convertedLine = COLON_DELIMITER_PATTERN.matcher(subLine).replaceAll(",");
				writer.write(convertedLine);
				writer.write('\n');
			}
		} catch (IOException ioe) {
			resultFile.delete();
			throw ioe;
		} finally {
			Closeables.close(writer, false);
		}
		return resultFile;
	}

	public static File readResourceToTempFile(String resourceName)
			throws IOException {
		InputSupplier<? extends InputStream> inSupplier;
		try {
			URL resourceURL = Resources.getResource(resourceName);
			inSupplier = Resources.newInputStreamSupplier(resourceURL);
		} catch (IllegalArgumentException iae) {
			File resourceFile = new File("src/main/resources" + resourceName);
			inSupplier = Files.newInputStreamSupplier(resourceFile);
		}
		File tempFile = File.createTempFile("taste", null);
		tempFile.deleteOnExit();
		Files.copy(inSupplier, tempFile);
		return tempFile;
	}

	@Override
	public String toString() {
		return "MovieDataModel";
	}
}
