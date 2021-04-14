package com.iiitb.customebook.repository;

import com.iiitb.customebook.bean.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {


    @Query("SELECT b FROM Book b WHERE b.book_name like '%book_name%'")
    public Book findByBookName(@Param("book_name") String book_name);

    @Query("SELECT b FROM Book b WHERE b.author like '%author%'")
    public List<Book> findByAuthor(@Param("author") String author);

    @Query("SELECT b FROM Book b WHERE b.publisher like '%publisher%'")
    public List<Book> findByPublisher(@Param("publisher") String publisher);

    @Query("Select b FROM Book b where b.isbnNumber = 'isbnNumber'")
    public List<Book> findByIsbnNumber(@Param("isbnNumber") String isbnNumber);




}
