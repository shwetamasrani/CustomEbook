package com.iiitb.customebook;

import com.iiitb.customebook.pojo.BookChapterVO;
import com.iiitb.customebook.pojo.BookVO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CustomebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomebookApplication.class, args);
        readXML();
        writeXML();
        addToXML();
    }
    public static void readXML() {
        System.out.println("Hello");

        try{
            File file = new File("src/main/resources/Books.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(BookVO.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            BookVO book= (BookVO) jaxbUnmarshaller.unmarshal(file);
            System.out.println("Chapters:");
            List<BookChapterVO> list=book.getBookChapters();
            for(BookChapterVO chapters:list)
                System.out.println(chapters.getChapterNumber()+" "+chapters.getChapterName()+"  "+chapters.getPrice() + " " + chapters.getContentLocation());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void writeXML() {
        System.out.println("Hello from the other side");

        try {
            JAXBContext contextObj = JAXBContext.newInstance(BookVO.class);

            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            BookChapterVO chapter1 = new BookChapterVO(1, "Linkin Park", 5100.30, "I've become so numb\n" +
                    "I can't feel you there\n" +
                    "Become so tired\n" +
                    "So much more aware");
            BookChapterVO chapter2 = new BookChapterVO(2, "Green Day", 5200.20, "Summer has come and passed\n" +
                    "The innocent can never last\n" +
                    "Wake me up when September ends");

            ArrayList<BookChapterVO> list = new ArrayList<>();
            list.add(chapter1);
            list.add(chapter2);

            BookVO book = new BookVO();
            book.setBookchapters(list);
            marshallerObj.marshal(book, new FileOutputStream("src/main/resources/newsample.xml"));
        } catch(JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void addToXML() {
        System.out.println("Hello! Again....");

        try{
            File file = new File("src/main/resources/Books.xml");
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
    }

}
