package zarchive;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import controller.ImageProcessor;

public class HSVtracking {

	public static void main(String[] args) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

		ImageProcessor ip = new ImageProcessor();
		Mat img1, imgHSV, imgbin;
		
		img1 = Highgui.imread("resources/images/background_menu.jpg");
		//Set ImageProcessor.HUE_MIN etc
		imgHSV = ip.getFilteredMat(img1);
		
		Highgui.imwrite("resource/testimages/hsv_test_2.jpg", imgHSV);
		
		
		

	}
	
	
	

}
