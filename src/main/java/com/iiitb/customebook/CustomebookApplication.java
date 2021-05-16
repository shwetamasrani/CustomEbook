package com.iiitb.customebook;

import com.iiitb.customebook.util.CustomEBookConstants;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;
import java.io.File;


@SpringBootApplication
public class CustomebookApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CustomebookApplication.class, args);

        makeDirectory(CustomEBookConstants.PATH_BOOKS);
        makeDirectory(CustomEBookConstants.PATH_BOOKS_XML);
        makeDirectory(CustomEBookConstants.PATH_BOOKS_ORDERS);

    }

    private static void makeDirectory(String path) {

        File f1 = new File(path);
        //Creating a folder using mkdir() method
        boolean bool = f1.mkdir();
        if(bool){
            System.out.println("Folder is created successfully");
        }else{
            System.out.println("Error Found!");
        }

    }



}
