package com.iiitb.customebook.service;

import com.iiitb.customebook.bean.ChapterItem;
import com.iiitb.customebook.exception.ResourceNotFoundException;
import com.iiitb.customebook.repository.ChapterItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChapterItemService {

    private final ChapterItemRepository chapterItemRepository;

    @Autowired
    public ChapterItemService(ChapterItemRepository chapterItemRepository) {
        this.chapterItemRepository = chapterItemRepository;
    }

    public ChapterItem getChapterItemById(Integer chapterItemId) {
        ChapterItem chapterItem= chapterItemRepository.findById(chapterItemId).orElseThrow(()
                -> new ResourceNotFoundException("Employee not exists with id:"+chapterItemId));

        return chapterItem;
    }

    public ChapterItem getChapterItemByBookIdAndChapterNumber(Integer bookId, Integer chapterNumber) {
        ChapterItem chapterItem= chapterItemRepository.findChapterItemByBookIdAndChapterNumber(bookId, chapterNumber);
        return chapterItem;
    }

    public ChapterItem addChapterItem(ChapterItem chapterItem)
    {
        return chapterItemRepository.save(chapterItem);
    }
}
