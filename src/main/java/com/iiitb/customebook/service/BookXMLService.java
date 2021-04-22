package com.iiitb.customebook.service;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.pojo.BookVO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class BookXMLService {

    public void createXMlFile(BookVO book, String filePath) {
        try {
            JAXBContext contextObj = JAXBContext.newInstance(BookVO.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(book, new FileOutputStream(filePath));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
