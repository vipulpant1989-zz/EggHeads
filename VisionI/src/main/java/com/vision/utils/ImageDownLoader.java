package com.vision.utils;

import com.vision.beans.ImageTO;
import com.vision.beans.PixabayTO;
import com.vision.constant.ClassifierConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vipul.pant on 7/29/17.
 */

@Service
public class ImageDownLoader {

    private static final String api = "https://pixabay.com/api/?key=6024518-696cabcaed5b9ddc7888c7094";
    private static final String category =  "food";
    private static final String image_type= "photo";
    private static final String url = api + "&cat=" + category + "&image_type=" + image_type;;
    private final String locationToStore  = this.getClass().getResource("/").getPath();

    private static final ApplicationContext context = new AnnotationConfigApplicationContext(com.vision.config.BeanConfig.class);



    public void downloadImages(String name , boolean negation) {
        String[] args = name.split(" ");
        Arrays.stream(args).map((arg)->{
             arg = arg + "";
             return arg;
        });
        if(!negation){
            name = "not " + name;
        }
        RestTemplate template = (RestTemplate)context.getBean("restTemplate");
        ResponseEntity<PixabayTO> responseEntity = template.getForEntity(url + "&q="+name, PixabayTO.class);
        List<ImageTO> imageTOS = responseEntity.getBody().getHits();
        final String comparer = name;
        imageTOS.stream().forEach((imageTO -> {
            try {
                if(!negation || !imageTO.getPreviewURL().contains(comparer)){
                    download(imageTO.getPreviewURL(), ClassifierConstants.negativeSample);
                }else if(imageTO.getPreviewURL().contains(comparer)) {
                    download(imageTO.getPreviewURL(), ClassifierConstants.positiveSample);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        if(negation){
            downloadImages(name , false);
        }
        ClassifierTrainerHelper.trainClassifier();

    }

    private File download(String path, String folderName) throws IOException{

        URL url = new URL(path);
        String name = FilenameUtils.getName(path);
        String samplePath = FileSystems.getDefault().getPath(folderName).toAbsolutePath().toString();
        File imageFile = new File(samplePath + File.separator + name);
        FileUtils.copyURLToFile(url , imageFile.getAbsoluteFile(), 10000 , 10000);
        System.out.println(imageFile);
        return imageFile;
    }
}
