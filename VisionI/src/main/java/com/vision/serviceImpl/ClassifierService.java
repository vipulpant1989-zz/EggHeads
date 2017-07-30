package com.vision.serviceImpl;

import com.vision.service.IClassifierService;
import nu.pattern.OpenCV;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by vipul.pant on 7/30/17.
 */

@Service
public class ClassifierService implements IClassifierService{

    private Hashtable<String, CascadeClassifier> getCascadeClassifier()throws IOException{

        System.out.println("Classifier Location ============ " +  FileSystems.getDefault().getPath("src/resources/trained_classifier").toAbsolutePath());
        String path = FileSystems.getDefault().getPath("/trained_classifier").toAbsolutePath().toString();

        File folder = null;
        folder = new ClassPathResource("/trained_classifier").getFile();;
        final Hashtable<String, CascadeClassifier> map = new Hashtable<>();
        Arrays.stream(folder.listFiles()).forEach(file -> {
            if(file.isFile()){
                String name = file.getName().split("_")[0];
                System.out.println("Name -------"+name + " " + file.getName());
                map.put(name, new CascadeClassifier(file.getAbsolutePath()));
            }
        });

        return map;
    }

    private String detectObject(Mat image ,  CascadeClassifier detector, String name){
        MatOfRect detections = new MatOfRect();
        detector.detectMultiScale(image, detections);

        System.out.println(String.format("Detected %s ",
                detections.toArray().length));

        // Draw a bounding box around each face.
        for (Rect rect : detections.toArray()) {
            System.out.println(rect.x + rect.y);

        }
        if(!detections.empty() && detections.toArray().length > 1){
            return name;
        }
        return null;
    }

    public List<String> detectImage(byte[] pixels) throws IOException {
        OpenCV.loadShared();
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(pixels));
        Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, ((DataBufferByte) img.getRaster().getDataBuffer()).getData());
        Hashtable<String, CascadeClassifier> detectors = getCascadeClassifier();
        List<String> names = new ArrayList<>();
        detectors.forEach((key, cascadeClassifier) -> {
            System.out.println("Key -------" + key);
            String name = detectObject(mat, cascadeClassifier , key);
            if(name !=null){
                names.add(name);
            }
        });
        return names;
    }
}
