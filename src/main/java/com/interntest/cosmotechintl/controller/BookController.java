package com.interntest.cosmotechintl.controller;

import com.interntest.cosmotechintl.dto.requestDto.BookRegisterDto;
import com.interntest.cosmotechintl.entity.BookInfo;
import com.interntest.cosmotechintl.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerBook(@RequestBody BookRegisterDto bookRegisterDto){

        // Check if the book with the same name, author, and publisher already exists
        if (bookService.existsByBookNameAndAuthorAndPublisher(
                bookRegisterDto.getBookName(),
                bookRegisterDto.getAuthor(),
                bookRegisterDto.getPublisher())) {
            return ResponseEntity.badRequest().body("A book with the same name, author, and publisher already exists.");
        }

        // Check if the book with the same name and publisher exists, but the author is different
        if (bookService.existsByBookNameAndPublisherAndDifferentAuthor(
                bookRegisterDto.getBookName(),
                bookRegisterDto.getPublisher(),
                bookRegisterDto.getAuthor())) {
            return ResponseEntity.badRequest().body("A book with the same name and publisher but a different author already exists.");
        }

        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookName(bookRegisterDto.getBookName());
        bookInfo.setAuthor(bookRegisterDto.getAuthor());
        bookInfo.setPublisher(bookRegisterDto.getPublisher());
        bookInfo.setPrice(bookRegisterDto.getPrice());
        bookInfo.setPageCount(bookRegisterDto.getPageCount());
        bookService.registerBook(bookInfo);
    return ResponseEntity.ok("Registered Book");
    }

    @GetMapping("/all")
    @ResponseBody
    public List<BookInfo> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/searchByName")
    public List<BookInfo> searchByBookName(@RequestParam String name) {
        return bookService.searchByBookName(name);
    }

    @GetMapping("/searchByAuthor")
    public List<BookInfo> searchByAuthor(@RequestParam String author) {
        return bookService.searchByAuthor(author);
    }

    @GetMapping("/searchByPublisher")
    public List<BookInfo> searchByPublisher(@RequestParam String publisher) {
        return bookService.searchByPublisher(publisher);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        if (bookService.deleteBook(id)) {
            return "Book deleted successfully.";
        }
        return "Book not found.";
    }

    @PutMapping("/modify/{id}")
    public String modifyBook(@PathVariable Long id, @RequestBody BookRegisterDto bookRegisterDto) {
        if (bookService.modifyBook(id, bookRegisterDto)) {
            return "Book modified successfully.";
        }
        return "Book not found.";
    }
}
