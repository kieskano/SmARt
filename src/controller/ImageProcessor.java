package controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import model.Number;



public class ImageProcessor implements ImgProcessor {

  private static final int HUE_MIN = 0;		// The Hue,Saturation, Value/Brightness (HSV) values with minimum and maximum boundaries
  private static final int HUE_MAX = 180;		// HUE range: (0-180) and SAT/VAL range: (0-255)
  private static final int SAT_MIN = 0;
  private static final int SAT_MAX = 255;
  private static final int VAL_MIN = 0;
  private static final int VAL_MAX = 35;

  private static final double MAX_COLORCODE = 255;
  private static final double SENSITIFITY = 0.25;

  public static void main(String[] args) {
    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    ImageProcessor pc = new ImageProcessor();

    BufferedImage image = null;
    try {
      File input = new File("resources/images/background_menu.jpg"); 
      image = ImageIO.read(input);
    } catch (IOException e) {
      e.printStackTrace();
    } 

    long start = System.currentTimeMillis();
    Mat mat = pc.bufferedImageToMat(image);
    Mat mat2 = pc.getFilteredMat(mat);
    System.out.printf("Cols:%4d Rows:%4d\n", mat2.cols(), mat2.rows());
    Mat mat3;
    mat3 = mat2.submat(new Rect(50, 20, 30, 30));
    pc.printMat(mat3);
    System.out.println("Mat average: " + pc.getMeanOfMat(mat3));
    Highgui.imwrite("resources/testimages/test_mat.png", mat2);
    System.out.println("Execution time: " + (System.currentTimeMillis() - start) + " milliseconds.");
  }

  /**
   * Empty constructor
   */
  public ImageProcessor() {	}

  /**
   * Checks whether the number tiles are being touched, and set their status accordingly.
   * @param image - BufferedImage which needs to be checked
   * @param numbers - checked on these locations
   */
  public void checkIfTouched(BufferedImage image, ArrayList<Number> numbers) {
    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    Mat mat1 = bufferedImageToMat(image);
    Mat mat2 = getFilteredMat(mat1);

    double average;
    Mat numberArea;

    for (Number number : numbers) {
      int newX = (int)((double)number.getX() * ((double)SmARt.CAM_DIMENSION.width/(double)SmARt.SCREEN_DIMENSION.width));
      int newY = (int)((double)number.getY() * ((double)SmARt.CAM_DIMENSION.height/(double)SmARt.SCREEN_DIMENSION.height));
      int newSizeWidth = (int)((double)number.getSize() * ((double)SmARt.CAM_DIMENSION.width/(double)SmARt.SCREEN_DIMENSION.width));
      int newSizeHeight = (int)((double)number.getSize() * ((double)SmARt.CAM_DIMENSION.height/(double)SmARt.SCREEN_DIMENSION.height));
      numberArea = mat2.submat(new Rect(newX, newY, newSizeWidth, newSizeHeight));
      average = getMeanOfMat(numberArea);
      //			System.out.printf("X:%4d Y:%4d Size:%3d AND X:%4d Y:%4d SizeW:%3d SizeH:%4d\n", number.getX(), number.getY(), number.getSize(), newX, newY, newSizeWidth, newSizeHeight);

      if (average/MAX_COLORCODE > SENSITIFITY) {
        number.setIsTouched(true);
      } else {
        number.setIsTouched(false);
      }
    }
  }

  /**
   * Returns the average pixel value of the given Mat matrix
   * @param mat Mat of which the average needs to be calculated 
   * @return double average
   */
  public double getMeanOfMat(Mat mat) {
    double result = 0;
    double rows = mat.rows();
    double cols = mat.cols();
    for (int y = 0; y < rows ; y++) {
      for (int x = 0; x < cols; x++) {
        result += mat.get(y,x)[0];
      }
    }
    return result / (rows * cols);
  }

  /**
   * Convert the given BufferedImage into a Mat data-type matrix
   * @param image BufferedImage that needs to be converted to Mat
   * @return Mat pixel matrix of image
   */
  public Mat bufferedImageToMat(BufferedImage image) {
    Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
    byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    mat.put(0, 0, data);
    return mat;
  }


  /**
   * Filters the given Mat matrix, turning all the colors in HSV range to white, and colors out of range to black.
   * @param mat Mat that needs to be filtered
   * @return filtered Mat
   */
  public Mat getFilteredMat(Mat mat) {
    Mat hsv = mat.clone();
    Scalar minc = new Scalar(HUE_MIN, SAT_MIN, VAL_MIN, 0);
    Scalar maxc = new Scalar(HUE_MAX, SAT_MAX, VAL_MAX, 0);
    Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV);
    Core.inRange(hsv, minc, maxc, hsv);
    return hsv;
  }

  /**
   * Prints the given 1 channel matrix (0=black 255=white)
   * @param mat - Mat that needs to be printed
   */
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
