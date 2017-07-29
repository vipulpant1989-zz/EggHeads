package com.vision.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;

/**
 * Created by vipul.pant on 7/29/17.
 */
public class ClassifierTrainerHelper {



    public static void trainClassifier(){
        createTxtFiles();
        createSamples();
        mergeSamples();
        startTraining();
    }



    private static void createTxtFiles(){
        String samplePath = FileSystems.getDefault().getPath("create_text.sh").toAbsolutePath().toString();
        try {
            executeProcess(samplePath , null);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }


    private static void createSamples(){
        try {
            String command = "opencv_createsamples -vec samples/positive.vec -info positives.txt -bgcolor 0 -bgthresh 0 -maxxangle 1.1\\" +
                    " -maxyangle 1.1 maxzangle 0.5 -maxidev 40 -w 80 -h 40";
            executeProcess(command , null);
            command = "opencv_createsamples -vec samples/negatives.vec -info negatives.txt -bgcolor 0 -bgthresh 0 -maxxangle 1.1\\" +
                    " -maxyangle 1.1 maxzangle 0.5 -maxidev 40 -w 80 -h 40";
            executeProcess(command , null);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private static void mergeSamples(){
        String command = "python ./tools/mergevec.py -v samples/ -o samples.vec";
        try {
            System.out.println(command);
            executeProcess(command , null);
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

    private static void startTraining(){
        String command = "opencv_traincascade -data classifier -vec samples.vec -bg negatives.txt" +
                " -numStages 20 -minHitRate 0.999 -maxFalseAlarmRate 0.5 -numPos 1000" +
                " -numNeg 600 -w 80 -h 40 -mode ALL -precalcValBufSize 1024\\" +
                " -precalcIdxBufSize 1024 -featureType LBP";
        try {
            System.out.println(command);
            executeProcess(command , null);
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }


    private static void executeProcess(String command, String args) throws IOException{
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            System.out.println("Executed --- "+ line);
        }
        System.out.println(process + " Executed");
    }
}
