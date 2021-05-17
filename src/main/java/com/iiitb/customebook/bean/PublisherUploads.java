package com.iiitb.customebook.bean;
import javax.persistence.*;

@Entity
@Table
public class PublisherUploads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int upload_id;

   /* @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;
*/
    @Column(nullable = false)
    private Integer book_id;

    public int getUpload_id() {
        return upload_id;
    }

    public void setUpload_id(int upload_id) {
        this.upload_id = upload_id;
    }

   /* public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }*/

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public PublisherUploads() {
    }

  /*  public PublisherUploads(User user_id, Integer book_id) {
        this.user_id = user_id;
        this.book_id = book_id;
    }*/
}
