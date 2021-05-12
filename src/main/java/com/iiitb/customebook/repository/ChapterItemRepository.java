package com.iiitb.customebook.repository;

import com.iiitb.customebook.bean.ChapterItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChapterItemRepository extends JpaRepository<ChapterItem,Integer> {

    @Query(value = "SELECT * FROM chapter_items ci WHERE ci.book_id = :bookId and ci.chapter_number = :chapterNumber", nativeQuery = true)
    public ChapterItem findChapterItemByBookIdAndChapterNumber(@Param("bookId") Integer bookId, @Param("chapterNumber") Integer chapterNumber);
}
