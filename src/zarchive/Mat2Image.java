package zarchive;

import java.awt.image.BufferedImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Mat2Image {
	Mat mat = new Mat();
	BufferedImage img;
	byte[] dat;
	public Mat2Image() {
	}
	public Mat2Image(Mat mat) {
		getSpace(mat);
	}
	public void getSpace(Mat mat) {
		this.mat = getFilteredMat(mat);
		printMat(this.mat.submat(new Rect(10,10,20,20)));
		int w = mat.cols(), h = mat.rows();
		if (dat == null || dat.length != w * h * 3)
			dat = new byte[w * h * 3];
		if (img == null || img.getWidth() != w || img.getHeight() != h
				|| img.getType() != BufferedImage.TYPE_3BYTE_BGR)
			img = new BufferedImage(w, h, 
					BufferedImage.TYPE_3BYTE_BGR);
	}
	BufferedImage getImage(Mat mat){
		getSpace(mat);
		this.mat.get(0, 0, dat);
		img.getRaster().setDataElements(0, 0, 
				this.mat.cols(), this.mat.rows(), dat);
		return img;
	}
	static{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}


	//Filter Mat using hsv colors
	public Mat getFilteredMat(Mat mat) {
		Mat hsv = mat.clone();
		Scalar minc = new Scalar(0,0,0, 0);
		Scalar maxc = new Scalar(360,255,255, 0);
		Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV);
		Core.inRange(hsv, minc, maxc, hsv);
		return hsv;
	}
	
	//Print mat
	public void printMat(Mat mat) {
	    System.out.print("[");
	    for (int y = 0; y < mat.rows(); y++) {
	      for (int x = 0; x < mat.cols(); x++) {
	        double[] pixel = mat.get(y, x);
	        System.out.printf(" %3d ", (int) pixel[0]);
	      }
	      if(y == mat.rows() - 1) System.out.println("]");
	      System.out.print("\n");
	    }
	  }
}