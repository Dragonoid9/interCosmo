package com.interntest.cosmotechintl.service;

import com.interntest.cosmotechintl.dto.requestDto.BookRegisterDto;
import com.interntest.cosmotechintl.entity.BookInfo;

import java.util.List;
import java.util.Optional;

public interface BookService {

    void registerBook(BookRegisterDto bookRegisterDto);

    void registerMultipleBooks(List<BookRegisterDto> bookRegisterDtos);

    boolean existsByBookNameAndAuthorAndPublisher(String bookName, String author, String publisher);

    boolean existsByBookNameAndPublisherAndDifferentAuthor(String bookName, String publisher, String author);


    List<BookInfo> getAllBooks();

    List<BookInfo> searchByBookName(String name);

    List<BookInfo> searchByAuthor(String author);

    List<BookInfo> searchByPublisher(String publisher);

    boolean deleteBook(Long id);

    boolean modifyBook(Long id, BookRegisterDto bookRegisterDto);

    Optional<BookInfo> findById(Long id);
}
