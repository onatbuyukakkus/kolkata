package barcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.Code128Constants;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import UUID.RandomNumberGenerator;

/**
 * This class is not utilized in the final project, as we do not work with the bar code as an image 
 * 
 */
public class ImageFromBarcodeGenerator {

	/**
	 * Default Constructor
	 */
	public ImageFromBarcodeGenerator() {

	}

	/** 
	 * Generating a 128 bit bar code image from UUID as String
	 * Note! The main should be changed into a JUnit test case.
	 * @param uuid The unique string of digits used to create the bar code. Allowed object is {@link String}
	 * @throws IOException
	 */
	public void generate128(String uuid) throws IOException {
		// Current directory
		String currentDirectory = System.getProperty("user.dir");

		// Location of output file
		String outputfilename = currentDirectory + "/src/main/java/barcode/" + uuid + ".png";

		Code128Bean barcode = new Code128Bean();

		barcode.setCodeset(Code128Constants.CODESET_C);
		final int dpi = 200;

		//Open output file
		File outputFile = new File(outputfilename);
		OutputStream out = new FileOutputStream(outputFile);

		try 
		{
			BitmapCanvasProvider canvasProvider = new BitmapCanvasProvider(
					out, "image/png", dpi, BufferedImage.TYPE_BYTE_GRAY, false, 0);

			barcode.generateBarcode(canvasProvider,uuid);

			canvasProvider.finish();
		} 
		finally 
		{
			out.close();
		}

	}
}