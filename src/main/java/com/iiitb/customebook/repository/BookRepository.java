package com.iiitb.customebook.repository;

import com.iiitb.customebook.bean.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {


    @Query(value = "SELECT * FROM books b WHERE b.book_name like %:bookName%", nativeQuery = true)
    public List<Book> findByBookName(@Param("bookName") String bookName);

    @Query(value = "SELECT * FROM books b WHERE b.author like %:author%", nativeQuery = true)
    public List<Book> findByAuthor(@Param("author") String author);

    @Query(value = "SELECT * FROM books b WHERE b.publisher like %:publisher%", nativeQuery = true)
    public List<Book> findByPublisher(@Param("publisher") String publisher);

    @Query(value = "SELECT * FROM books b WHERE b.isbn_number =:isbnNumber", nativeQuery = true)
    public Book findByIsbnNumber(@Param("isbnNumber") String isbnNumber);

}
