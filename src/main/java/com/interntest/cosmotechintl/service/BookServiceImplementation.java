package com.interntest.cosmotechintl.service;

import com.interntest.cosmotechintl.dto.requestDto.BookRegisterDto;
import com.interntest.cosmotechintl.entity.BookInfo;
import com.interntest.cosmotechintl.repository.BookInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImplementation implements BookService {

    private final BookInfoRepository bookInfoRepository;

    @Autowired
    public BookServiceImplementation(BookInfoRepository bookInfoRepository) {
        this.bookInfoRepository = bookInfoRepository;
    }

    @Override
    public void registerBook(BookRegisterDto bookRegisterDto) {
        // Use the helper method to check for duplicates
        checkForDuplicateBook(bookRegisterDto);

        // Create and save the new book
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookName(bookRegisterDto.getBookName());
        bookInfo.setAuthor(bookRegisterDto.getAuthor());
        bookInfo.setPublisher(bookRegisterDto.getPublisher());
        bookInfo.setPrice(bookRegisterDto.getPrice());
        bookInfo.setPageCount(bookRegisterDto.getPageCount());
        bookInfoRepository.save(bookInfo);
    }

    @Override
    @Transactional
    public void registerMultipleBooks(List<BookRegisterDto> bookRegisterDtos) {
        for (BookRegisterDto bookRegisterDto : bookRegisterDtos) {
            // Use the helper method to check for duplicates
            checkForDuplicateBook(bookRegisterDto);

            // Create and save the new book
            BookInfo bookInfo = new BookInfo();
            bookInfo.setBookName(bookRegisterDto.getBookName());
            bookInfo.setAuthor(bookRegisterDto.getAuthor());
            bookInfo.setPublisher(bookRegisterDto.getPublisher());
            bookInfo.setPrice(bookRegisterDto.getPrice());
            bookInfo.setPageCount(bookRegisterDto.getPageCount());
            bookInfoRepository.save(bookInfo);
        }
    }

    @Override
    public boolean existsByBookNameAndAuthorAndPublisher(String bookName, String author, String publisher) {
        return bookInfoRepository.existsByBookNameAndAuthorAndPublisher(bookName, author, publisher);
    }

    @Override
    public boolean existsByBookNameAndPublisherAndDifferentAuthor(String bookName, String publisher, String author) {
        return bookInfoRepository.existsByBookNameAndPublisherAndAuthorNot(bookName, publisher, author);
    }


    @Override
    public List<BookInfo> getAllBooks() {
        return bookInfoRepository.findAll();
    }

    @Override
    public List<BookInfo> searchByBookName(String name) {
        return bookInfoRepository.findByBookNameContainingIgnoreCase(name);
    }

    @Override
    public List<BookInfo> searchByAuthor(String author) {
        return bookInfoRepository.findByAuthorContainingIgnoreCase(author);
    }

    @Override
    public List<BookInfo> searchByPublisher(String publisher) {
        return bookInfoRepository.findByPublisherContainingIgnoreCase(publisher);
    }

    @Override
    public boolean deleteBook(Long id) {
        if (bookInfoRepository.existsById(id)) {
            bookInfoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyBook(Long id, BookRegisterDto bookRegisterDto) {
        Optional<BookInfo> bookOptional = bookInfoRepository.findById(id);
        if (bookOptional.isPresent()) {
            // Check for duplicates
            checkForDuplicateBook(bookRegisterDto);

            BookInfo bookInfo = bookOptional.get();
            bookInfo.setBookName(bookRegisterDto.getBookName());
            bookInfo.setAuthor(bookRegisterDto.getAuthor());
            bookInfo.setPublisher(bookRegisterDto.getPublisher());
            bookInfo.setPrice(bookRegisterDto.getPrice());
            bookInfo.setPageCount(bookRegisterDto.getPageCount());
            bookInfoRepository.save(bookInfo);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BookInfo> findById(Long id) {
        return bookInfoRepository.findById(id);
    }

    private void checkForDuplicateBook(BookRegisterDto bookRegisterDto) {
        // Check if the book with the same name, author, and publisher already exists
        if (existsByBookNameAndAuthorAndPublisher(
                bookRegisterDto.getBookName(),
                bookRegisterDto.getAuthor(),
                bookRegisterDto.getPublisher())) {
            String errorMessage = String.format(
                    "A book with the same name, author, and publisher already exists: %s, %s, %s",
                    bookRegisterDto.getBookName(),
                    bookRegisterDto.getAuthor(),
                    bookRegisterDto.getPublisher());
            throw new IllegalStateException(errorMessage);
        }

        // Check if the book with the same name and publisher exists, but the author is different
        if (existsByBookNameAndPublisherAndDifferentAuthor(
                bookRegisterDto.getBookName(),
                bookRegisterDto.getPublisher(),
                bookRegisterDto.getAuthor())) {
            String errorMessage = String.format(
                    "A book with the same name and publisher but a different author already exists: %s, %s, %s",
                    bookRegisterDto.getBookName(),
                    bookRegisterDto.getAuthor(),
                    bookRegisterDto.getPublisher());
            throw new IllegalStateException(errorMessage);
        }
    }
}