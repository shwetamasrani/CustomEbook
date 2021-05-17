package com.iiitb.customebook;

import com.iiitb.customebook.util.CustomEBookConstants;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.List;


@SpringBootApplication
public class CustomebookApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CustomebookApplication.class, args);

        makeDirectory(CustomEBookConstants.PATH_BOOKS);
        makeDirectory(CustomEBookConstants.PATH_BOOKS_XML);
        makeDirectory(CustomEBookConstants.PATH_BOOKS_ORDERS);
        tryingIndex();
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

    static final float INCH = 72;
    public static List<PDAnnotation> annotations;

    @SuppressWarnings("null")
    public static void tryingIndex() throws Exception {
        PDRectangle position = null;
        PDDocument document = new PDDocument();
        try {
            for (int i = 0; i < 10; i++) {
                PDPage page = new PDPage();
                document.addPage(page);
            }
            PDPage page1 = document.getPage(0);

            HashMap<Integer, String> pgs = new HashMap<Integer, String>();

            for (int i = 0; i < document.getNumberOfPages(); i++) {
                pgs.put(i, "Jump to Page" + i);

            }

            PDFont font = PDType1Font.HELVETICA_BOLD;
            PDPageContentStream contents = new PDPageContentStream(document,
                    page1);
            contents.beginText();
            contents.setFont(font, 18);
            contents.newLineAtOffset(50, 600);
            contents.setLeading(20f);
            contents.showText("PDFBox");

            position = new PDRectangle();
            for (int i = 0; i < document.getNumberOfPages(); i++) {

                contents.newLine();
                contents.showText(pgs.get(i));
                contents.newLine();
            }
            contents.endText();
            contents.close();

            PDRectangle[] pos1 = new PDRectangle[document.getNumberOfPages()];
            for(int i=0;i<document.getNumberOfPages();i++){
                pos1[i]=new PDRectangle();
                pos1[i].setLowerLeftX(50);
                pos1[i].setLowerLeftY(575-(i*40));
                pos1[i].setUpperRightX(INCH + 100);
                pos1[i].setUpperRightY(585-(i*40));
                getPage(document, page1, pgs.get(i), i, pos1[i]);
            }

            document.save(CustomEBookConstants.PATH_BOOKS+"/index.pdf");
            System.out.println("Completed");
        } finally {
            document.close();
        }
    }

    public static void getPage(PDDocument document, PDPage page1, String txt,
                               int pageno, PDRectangle position) throws IOException {
        annotations = page1.getAnnotations();
        PDBorderStyleDictionary borderThick = new PDBorderStyleDictionary();
        borderThick.setWidth(INCH / 12); // 12th inch

        PDBorderStyleDictionary borderThin = new PDBorderStyleDictionary();
        borderThin.setWidth(INCH / 72); // 1 point

        PDBorderStyleDictionary borderULine = new PDBorderStyleDictionary();
        borderULine.setStyle(PDBorderStyleDictionary.STYLE_UNDERLINE);
        borderULine.setWidth(INCH / 72); // 1 point

        float pw = page1.getMediaBox().getUpperRightX();
        float ph = page1.getMediaBox().getUpperRightY();
        float textWidth = PDType1Font.TIMES_BOLD.getStringWidth("PDFBox") / 1000 * 18;

        PDAnnotationLink pageLink = new PDAnnotationLink();
        pageLink.setBorderStyle(borderULine);
        textWidth = PDType1Font.TIMES_BOLD.getStringWidth(txt) / 1000 * 18;

        pageLink.setRectangle(position);
        PDActionGoTo actionGoto = new PDActionGoTo();
        PDPageDestination dest = new PDPageFitWidthDestination();
        dest.setPage(document.getPage(pageno));
        actionGoto.setDestination(dest);
        pageLink.setAction(actionGoto);
        annotations.add(pageLink);
    }
}
