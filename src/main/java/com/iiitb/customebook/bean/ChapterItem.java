package com.iiitb.customebook.bean;
import javax.persistence.*;

@Entity
@Table(name = "ChapterItems")
public class ChapterItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chapterId;

    @Column(nullable = false)
    private Integer bookId;

    @Column(nullable = false)
    private Integer chapterNumber;

    public ChapterItem() {
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(Integer chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

}


