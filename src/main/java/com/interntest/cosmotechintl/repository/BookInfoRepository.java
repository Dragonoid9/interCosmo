package com.interntest.cosmotechintl.repository;

import com.interntest.cosmotechintl.entity.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookInfoRepository extends JpaRepository<BookInfo, Long> {

    boolean existsByBookNameAndAuthorAndPublisher(String bookName, String author, String publisher);

    boolean existsByBookNameAndPublisherAndAuthorNot(String bookName, String publisher, String author);

    List<BookInfo> findByBookNameContainingIgnoreCase(String name);

    List<BookInfo> findByAuthorContainingIgnoreCase(String author);

    List<BookInfo> findByPublisherContainingIgnoreCase(String publisher);
}
