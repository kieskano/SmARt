package controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import model.Number;



public class ImageProcessor {
	
	private final int HUE_MIN = 0;		// The Hue,Saturation, Value (HSV) values with minimum and maximum boundaries
	private final int HUE_MAX = 0;
	private final int SAT_MIN = 0;
	private final int SAT_MAX = 0;
	private final int VAL_MIN = 0;
	private final int VAL_MAX = 0;
	
	public static void main(String[] args) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    	Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
    	System.out.println( "mat = " + mat.dump() );
    	
    	
    
	}

	public ImageProcessor() {
    
	}
	
	public Mat bufferedImageToMat(BufferedImage image) {
		Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
		byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		mat.put(0, 0, data);
		return mat;
	}
  
	public void checkIfTouched(BufferedImage image, ArrayList<Number> numbers) {
		Mat mat = bufferedImageToMat(image);
		
		
		
		
		
    
	}
	
	
  
	
}
