package com.iiitb.customebook;

import com.iiitb.customebook.util.CustomEBookConstants;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class CustomebookApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CustomebookApplication.class, args);

        makeDirectory(CustomEBookConstants.PATH_BOOKS);
        makeDirectory(CustomEBookConstants.PATH_BOOKS_XML);
        //addToXML();
        splitPdf("sample2",21,30);
        mergePDF();
    }

    private static void mergePDF() throws IOException {

        //Loading an existing PDF document
        File file1 = new File(CustomEBookConstants.PATH_BOOKS+"/sample.pdf");
        PDDocument document1 = PDDocument.load(file1);
        File file2 = new File(CustomEBookConstants.PATH_BOOKS+"/sample2.pdf");
        PDDocument document2 = PDDocument.load(file2);

        //Create PDFMergerUtility class object
        PDFMergerUtility PDFmerger = new PDFMergerUtility();

        //Setting the destination file path
        PDFmerger.setDestinationFileName(CustomEBookConstants.PATH_BOOKS+"/merged.pdf");

        //adding the source files
        PDFmerger.addSource(file1);
        PDFmerger.addSource(file2);
        //Merging the documents
        PDFmerger.mergeDocuments(null);

        System.out.println("PDF Documents merged to a single file successfully");

//Close documents
        document1.close();
        document2.close();

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

    private static void splitPdf(String fileName, int fromPage, int toPage) throws IOException {

        // Loading PDF
        File pdfFile
                = new File(CustomEBookConstants.PATH_BOOKS+"/DevOps For Dummies" + CustomEBookConstants.PDF_FILE_EXTENSION);
        PDDocument document = PDDocument.load(pdfFile);
        if (document.getNumberOfPages() > 20) {
            System.out.println(document.getDocumentInformation().getTitle());
            try {
                Splitter splitter = new Splitter();
                splitter.setStartPage(fromPage);
                splitter.setEndPage(toPage);
                splitter.setSplitAtPage(toPage);
                List<PDDocument> splittedList = splitter.split(document);
                for (PDDocument doc : splittedList) {
                    doc.save( CustomEBookConstants.PATH_BOOKS+"/"+fileName + ".pdf");
                    doc.close();
                }
                System.out.println("Save successful file : " + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void mergePDFs(List<File> chaptersPDFList, Integer orderId) throws IOException {

        //Create PDFMergerUtility class object
        PDFMergerUtility PDFmerger = new PDFMergerUtility();

        //Setting the destination file path
        PDFmerger.setDestinationFileName(CustomEBookConstants.PATH_BOOKS+"/merged_"+orderId+CustomEBookConstants.PDF_FILE_EXTENSION);

        for(File chapterPDF: chaptersPDFList) {
            PDDocument document = PDDocument.load(chapterPDF);
            PDFmerger.addSource(chapterPDF);
            document.close();
        }
        //Merging the documents
        PDFmerger.mergeDocuments(null);

        System.out.println("PDF Documents merged to a single file successfully");
    }

   /*
    public static void addToXML() {
        System.out.println("Hello! Again....");

        try{
            File file = new File("src/main/resources/Books/pythonBook.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(BookVO.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            BookVO book= (BookVO) jaxbUnmarshaller.unmarshal(file);
            System.out.println("Existing Chapters:");
            List<BookChapterVO> chapterList=book.getBookChapters();
            for(BookChapterVO chapters:chapterList)
                System.out.println(chapters.getChapterNumber()+" "+chapters.getChapterName()+"  "+chapters.getPrice() + " " + chapters.getContentLocation());
            BookChapterVO newChapter = new BookChapterVO(3, "Twinkle", 500.30, "Twinkle Twinkle Little Star, How I wonder what you are?");
            chapterList.add(newChapter);
            book.setBookchapters(chapterList);

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(book, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }*/

}
