package com.interntest.cosmotechintl.controller;

import com.interntest.cosmotechintl.dto.requestDto.BookRegisterDto;
import com.interntest.cosmotechintl.entity.BookInfo;
import com.interntest.cosmotechintl.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerBook(@RequestBody @Validated BookRegisterDto bookRegisterDto){

        try {
            bookService.registerBook(bookRegisterDto);
            return ResponseEntity.ok("Registered Book");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping("/registerMultiple")
    @ResponseBody
    public ResponseEntity<String> registerMultipleBooks(@RequestBody List<BookRegisterDto> bookRegisterDtos) {
        try {
            bookService.registerMultipleBooks(bookRegisterDtos);
            return ResponseEntity.ok("Books registered successfully.");
        } catch (IllegalStateException e) {
            // Return the error message from the exception (which contains book details)
            return ResponseEntity.status(400).body("Error registering books: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/getall")
    @ResponseBody
    public ResponseEntity<List<BookInfo>> getAllBooks() {
        List<BookInfo> booksinfo = bookService.getAllBooks();
        if (booksinfo.isEmpty()) {
            return ResponseEntity.status(404).body(Collections.emptyList());
        }
        return ResponseEntity.ok(booksinfo);
    }

    @GetMapping("/searchByName")
    @ResponseBody
    public ResponseEntity<List<BookInfo>> searchByBookName(@RequestParam String name) {
        List<BookInfo> books = bookService.searchByBookName(name);
        if (books.isEmpty()) {
            return ResponseEntity.status(404).body(Collections.emptyList());
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/searchByAuthor")
    @ResponseBody
    public ResponseEntity<List<BookInfo>> searchByAuthor(@RequestParam String author) {
        List<BookInfo> books = bookService.searchByAuthor(author);
        if (books.isEmpty()) {
            return ResponseEntity.status(404).body(Collections.emptyList());
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/searchByPublisher")
    @ResponseBody
    public ResponseEntity<List<BookInfo>> searchByPublisher(@RequestParam String publisher) {
        List<BookInfo> books = bookService.searchByPublisher(publisher);
        if (books.isEmpty()) {
            return ResponseEntity.status(404).body(Collections.emptyList());
        }
        return ResponseEntity.ok(books);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.ok("Book deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PutMapping("/modify/{id}")
    @ResponseBody
    public ResponseEntity<String> modifyBook(@PathVariable Long id, @RequestBody @Validated BookRegisterDto bookRegisterDto) {
        try {
            if (bookService.modifyBook(id, bookRegisterDto)) {
                return ResponseEntity.ok("Book modified successfully.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
