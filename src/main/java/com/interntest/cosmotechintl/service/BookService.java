package com.interntest.cosmotechintl.service;

import com.interntest.cosmotechintl.dto.requestDto.BookRegisterDto;
import com.interntest.cosmotechintl.entity.BookInfo;

import java.util.List;

public interface BookService {

    public void registerBook(BookInfo bookInfo);

    boolean existsByBookNameAndAuthorAndPublisher(String bookName, String author, String publisher);

    boolean existsByBookNameAndPublisherAndDifferentAuthor(String bookName, String publisher, String author);


    List<BookInfo> getAllBooks();

    List<BookInfo> searchByBookName(String name);

    List<BookInfo> searchByAuthor(String author);

    List<BookInfo> searchByPublisher(String publisher);

    boolean deleteBook(Long id);

    boolean modifyBook(Long id, BookRegisterDto bookRegisterDto);
}
