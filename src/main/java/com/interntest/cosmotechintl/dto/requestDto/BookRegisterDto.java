package com.interntest.cosmotechintl.dto.requestDto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookRegisterDto {

    @NotBlank(message = "Book name cannot be empty")
    private String bookName;

    @NotBlank(message = "Author name cannot be empty")
    private String author;

    @NotBlank(message = "Publisher name cannot be empty")
    private String publisher;

    @Min(value = 100, message = "Price must be greater than 0")
    private int price;

    @Min(value = 50, message = "Page count must be greater than 0")
    private int pageCount;
}
