package test;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import controller.CameraReader;
import controller.ImageProcessor;
import controller.SmARt;

public class Tests {	
	
	 

	public static void main(String[] args) {
		testImage();
	}
	
	
	
	/**
	 * Tests color detection of taken webcam image
	 */
	public static void testImage() {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		
		CameraReader cam = new CameraReader(SmARt.CAM_NR, SmARt.CAM_DIMENSION);
		ImageProcessor pc = new ImageProcessor();
		
		cam.open();
		BufferedImage img = cam.takePhoto();
		cam.close();
		Mat mat = pc.bufferedImageToMat(img);
        Mat mat2 = pc.getFilteredMat(mat);
        
        Highgui.imwrite("resources/testimages/before.jpg", mat);
        Highgui.imwrite("resources/testimages/after.jpg", mat2);
        
        System.out.println("DONE!");
	}
	
	
	/**
	 * Tests Random number generator
	 */
	public static void randomTest() {
		int height = 720;
		int square_size = 100;
		
		int y = 0;
		int y2= 0;
		while (true) {
			y = (int) (Math.random() * (height - 2.0* square_size) + square_size);
			y2 = (int) (Math.random() * height - square_size);
			if(y <0 || y2 <0) System.out.printf("y:%d, y2:%d\n",y,y2);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	

}
