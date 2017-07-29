import com.vision.utils.ImageDownLoader;
import com.vision.utils.ImageProcessor;
import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.swing.*;
import java.awt.*;
import java.nio.file.FileSystems;

/**
 * Created by vipul.pant on 7/18/17.
 */
public class Main {

    private JFrame frame;
    private JLabel imageLabel;

    private void initGUI() {
        frame = new JFrame("Video Playback Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        imageLabel = new JLabel();
        frame.add(imageLabel);
        frame.setVisible(true);


    }


    public static void main(String[] args){

        Main app = new Main();
        //ImageDownLoader imageDownLoader = new ImageDownLoader();
        //imageDownLoader.downloadImages("banana" , true);
        try {

            app.bufferVideo();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private CascadeClassifier getCascadeClassifier(String fileName){

        return  new CascadeClassifier(FileSystems.getDefault().getPath("/Classifier/banana_classifier.xml").toAbsolutePath().toString());
    }

    private void detectObject(Mat image ,  CascadeClassifier detector){
        MatOfRect detections = new MatOfRect();
        detector.detectMultiScale(image, detections);

        System.out.println(String.format("Detected %s ",
                detections.toArray().length));

        // Draw a bounding box around each face.
        for (Rect rect : detections.toArray()) {
            System.out.println(rect.x + rect.y);
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
                    + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }

        // Save the visualized detection.
        String filename = "faceDetection.png";
        System.out.println(String.format("Writing %s", filename));
    }


    private void bufferVideo() throws  Exception{

        OpenCV.loadShared();
        Mat webcamMatImage = Mat.eye(3 , 3 , CvType.CV_8UC1);
        VideoCapture capture = new VideoCapture(0);
        Image tempImage;
        org.opencv.core.Point point1 = new Point() , point2 = new Point();
        initGUI();
        if(!capture.isOpened()){
            System.err.println("Error");
        }
        CascadeClassifier detector = getCascadeClassifier(null);
        if( capture.isOpened()){
            ImageProcessor imageProcessor = new ImageProcessor();
            while (true){
                capture.read(webcamMatImage);
                if( !webcamMatImage.empty() ){
                    detectObject(webcamMatImage, detector);
                    tempImage= imageProcessor.toBufferedImage(webcamMatImage);
                    ImageIcon imageIcon = new ImageIcon(tempImage, "Detection");
                    imageLabel.setIcon(imageIcon);
                    frame.pack();  //this will resize the window to fit the image
                    Thread.sleep(50);
                }
                else{
                    System.out.println(" Frame not captured or video has finished");
                    break;
                }
            }
        }
        else{
            System.out.println("Couldn't open video file.");
        }

    }

}
