package com.iiitb.customebook.service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import com.iiitb.customebook.pojo.ItemVO;
import com.iiitb.customebook.util.CustomEBookConstants;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDFMerge {

    public static String merge(Integer orderId, List<ItemVO> orderItems)  {

        String orderFolderPath = CustomEBookConstants.PATH_BOOKS_ORDERS+File.separator+orderId;
        makeDirectory(orderFolderPath);
        try {
            List<String> extractedChapterLocations = extractChapters(orderFolderPath, orderItems);
            String file_location = mergePDFs(extractedChapterLocations, orderId);
            return generate_page_numbers(file_location);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

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

    public static String mergePDFs(List<String> chaptersPDFList, Integer orderId) throws IOException {


        //Create PDFMergerUtility class object
        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        String destinationFileName = CustomEBookConstants.PATH_BOOKS+File.separator+"Order-"+orderId+".pdf";
        //Setting the destination file path
        PDFmerger.setDestinationFileName(destinationFileName);

        for(String chapterLocation: chaptersPDFList){
            File file = new File(chapterLocation);
            PDDocument document = PDDocument.load(file);
            //adding the source files
            PDFmerger.addSource(file);
        }

        //Merging the documents
        PDFmerger.mergeDocuments(null);
        System.out.println("PDF Documents merged to a single file successfully");
        return destinationFileName;

    }

    private static String splitPdf(String bookLocation, int fromPage, int toPage, String orderFolderPath, int count) throws IOException {

        // Loading PDF
        File pdfFile = new File(bookLocation);
        PDDocument document = PDDocument.load(pdfFile);

        if (document.getNumberOfPages() > toPage) {
            System.out.println(document.getDocumentInformation().getTitle());
            try {
                Splitter splitter = new Splitter();
                splitter.setStartPage(fromPage);
                splitter.setEndPage(toPage);
                splitter.setSplitAtPage(toPage);
                List<PDDocument> splittedChapters = splitter.split(document);
                String fileName = orderFolderPath+File.separator + count + ".pdf";
                for (PDDocument doc : splittedChapters) {
                    doc.save( fileName);
                    doc.close();
                }
                System.out.println("Save successful file : " + fileName);
                return fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<String> extractChapters(String orderFolderPath, List<ItemVO> chapterDetails) throws IOException {
        List<String> extractedChapterLocation = new ArrayList<>();
        int count=0;
        for(ItemVO item: chapterDetails) {
            String bookLocation = item.getBookLocation();
            count++;
            extractedChapterLocation.add(splitPdf(bookLocation, item.getStartPage(), item.getEndPage(), orderFolderPath, count));
        }
        return extractedChapterLocation;
    }

    public static String generate_page_numbers(String file_name) {
        File load_file = new File(file_name);
        PDDocument doc=null;
        try {

            doc = PDDocument.load(load_file);
            int page_number = 1;
            for (PDPage page : doc.getPages()) {
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, false);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ITALIC, 10);
                contentStream.setStrokingColor(Color.BLACK);
                PDRectangle pageSize = page.getCropBox();
                float x = pageSize.getLowerLeftX();
                float y = pageSize.getLowerLeftY();
                contentStream.newLineAtOffset(x + pageSize.getWidth() - 60, y + 20);
                contentStream.showText(Integer.toString(page_number));
                contentStream.endText();
                contentStream.close();
                ++page_number;
            }

            doc.save(load_file);
            doc.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_name;
    }

    public static String addCoverPage(){
        return null;
    }
}
