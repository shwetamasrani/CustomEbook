package com.iiitb.customebook.service;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

import com.iiitb.customebook.bean.Order;
import com.iiitb.customebook.pojo.ItemVO;
import com.iiitb.customebook.util.CustomEBookConstants;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
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

public class PDFMerge {

    static final float INCH = 72;
    public static List<PDAnnotation> annotations;
    public static HashMap<String, Integer> chapPageNumber = new HashMap<String, Integer>();


    public static String merge(Order order, Integer orderId, List<ItemVO> orderItems) throws Exception {

        String orderFolderPath = CustomEBookConstants.PATH_BOOKS_ORDERS+File.separator+orderId;
        makeDirectory(orderFolderPath);
        try {
            List<String> extractedChapterLocations = extractChapters(orderFolderPath, orderItems);
            String coverPage = createCoverPage(order.getCustomEBookName(), order.getUser_id().getFirstName()+" "+ order.getUser_id().getLastName(), orderFolderPath);

            String fileLocation = mergePDFs(extractedChapterLocations, orderId);
            //tryingIndex(fileLocation);
            addCoverPage(fileLocation, coverPage);
            return generate_page_numbers(fileLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private static void addCoverPage(String fileLocation, String coverPageLocation) throws IOException {

        //Create PDFMergerUtility class object
        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        //Setting the destination file path
        PDFmerger.setDestinationFileName(fileLocation);

        File coverPage = new File(coverPageLocation);
        PDDocument coverPageDocument = PDDocument.load(coverPage);
        PDFmerger.addSource(coverPage);

        File mergedFile = new File(fileLocation);
        PDDocument mergedDocument = PDDocument.load(mergedFile);
        PDFmerger.addSource(mergedFile);
        PDFmerger.mergeDocuments(null);
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
        int pgNo=1;
        int count=0;
        for(ItemVO item: chapterDetails) {
            String bookLocation = item.getBookLocation();
            chapPageNumber.put(item.getBookName()+" - "+item.getChapterName(), pgNo);
            pgNo += item.getEndPage() - item.getStartPage() + 1;
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
            int i=0;
            for (PDPage page : doc.getPages()) {
                i++;
                if(i==1) continue;
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

    public static String createCoverPage(String customEBookName, String fullName, String orderFolderPath) throws IOException {
        // Create a Document object.
        PDDocument pdDocument = new PDDocument();

        // Create a Page object
        PDPage pdPage = new PDPage();
        // Add the page to the document and save the document to a desired file.
        pdDocument.addPage(pdPage);
        String destination = orderFolderPath+File.separator+"cover.pdf";

        try {
            // Create a Content Stream
            PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, pdPage);

            float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(customEBookName) / 1000 * 64;
            float titleHeight = PDType1Font.HELVETICA_BOLD.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * 64;

            // Lets try a different font and size
            pdPageContentStream.beginText();
            pdPageContentStream.newLineAtOffset((pdPage.getMediaBox().getWidth() - titleWidth) / 2,
                    pdPage.getMediaBox().getHeight() - 30 - titleHeight);
            pdPageContentStream.setFont(PDType1Font.HELVETICA_BOLD, 64);
            pdPageContentStream.showText(customEBookName);
            pdPageContentStream.endText();

            titleWidth = PDType1Font.COURIER.getStringWidth(fullName) / 1000 * 18;
            pdPageContentStream.beginText();
            pdPageContentStream.newLineAtOffset((pdPage.getMediaBox().getWidth() - titleWidth) / 2, 50);
            pdPageContentStream.setFont(PDType1Font.COURIER, 18);
            pdPageContentStream.showText(fullName);
            pdPageContentStream.endText();

            // Once all the content is written, close the stream
            pdPageContentStream.close();


            pdDocument.save(destination);
            pdDocument.close();
            System.out.println("PDF saved to the location !!!");
            return destination;

        } catch (IOException ioe) {
            System.out.println("Error while saving pdf" + ioe.getMessage());
        }
        return null;
    }

    @SuppressWarnings("null")
    public static String tryingIndex(String fileLocation) throws Exception {

        String indexDestination = CustomEBookConstants.PATH_BOOKS+"/index.pdf";
        // Create a Document object.
        PDDocument newDocument = new PDDocument();

        // Create a Page object
        PDPage newPage = new PDPage();
        // Add the page to the document and save the document to a desired file.
        newDocument.addPage(newPage);
        newDocument.save(indexDestination);
        newDocument.close();
        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        PDFmerger.setDestinationFileName(fileLocation);
        File indexPage = new File(indexDestination);
        PDDocument indexPageDocument = PDDocument.load(indexPage);
        PDFmerger.addSource(indexPage);

        File mergedFile = new File(fileLocation);
        PDDocument mergedDocument = PDDocument.load(mergedFile);
        PDFmerger.addSource(mergedFile);
        PDFmerger.mergeDocuments(null);


        PDPage pdPage = mergedDocument.getPage(0);
        PDPageContentStream contents = new PDPageContentStream(mergedDocument,  pdPage);


        PDRectangle position = null;
        try {

            PDFont font = PDType1Font.HELVETICA_BOLD;

            float titleWidth = font.getStringWidth("Table of Contents") / 1000 * 18;
            float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * 18;
            // Lets try a different font and size
            contents.beginText();
            contents.setFont(font, 18);
            contents.newLineAtOffset((pdPage.getMediaBox().getWidth() - titleWidth) / 2,
                    pdPage.getMediaBox().getHeight() - 30 - titleHeight);
            contents.setLeading(20f);
            contents.showText("Table of Contents");

            titleWidth = PDType1Font.COURIER.getStringWidth("Table of Contents") / 1000 * 12;
            titleHeight = PDType1Font.COURIER.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * 12;

            contents.newLineAtOffset(27, 200);
            contents.setFont(PDType1Font.COURIER, 12);
                position = new PDRectangle();
                for(String chapter : chapPageNumber.keySet()){
                    contents.newLine();
                    contents.showText(chapter);
                    contents.newLine();
                }

                contents.endText();
                contents.close();

                PDRectangle[] pos1 = new PDRectangle[chapPageNumber.size()];

                int i=0;

                // Using for-each loop
                for (Map.Entry mapElement : chapPageNumber.entrySet()) {
                    String key = (String)mapElement.getKey();
                    int value = ((int)mapElement.getValue());
                    pos1[i]=new PDRectangle();
                    pos1[i].setLowerLeftX((pdPage.getMediaBox().getWidth() - titleWidth) / 2);
                    pos1[i].setLowerLeftY(575-(i*40));
                    pos1[i].setUpperRightX(INCH + 100);
                    pos1[i].setUpperRightY(585-(i*40));

                    getPage(mergedDocument, pdPage, key,  value, pos1[i]);
                    i++;
                    System.out.println(key + " : " + value);
                }


            mergedDocument.save(fileLocation);
                System.out.println("Completed");
            } finally {
            mergedDocument.close();
            }

        return fileLocation;
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
        float textWidth = PDType1Font.TIMES_BOLD.getStringWidth("Table of Contents") / 1000 * 18;

        PDAnnotationLink pageLink = new PDAnnotationLink();
        pageLink.setBorderStyle(borderULine);
        textWidth = PDType1Font.TIMES_BOLD.getStringWidth(txt) / 1000 * 18;

        pageLink.setRectangle(position);
        PDActionGoTo actionGoto = new PDActionGoTo();
        PDPageDestination dest = new PDPageFitWidthDestination();
        dest.setPage(document.getPage(pageno-1));
        actionGoto.setDestination(dest);
        pageLink.setAction(actionGoto);
        annotations.add(pageLink);
    }
}
