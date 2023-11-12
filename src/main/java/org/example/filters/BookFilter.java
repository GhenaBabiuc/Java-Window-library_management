package org.example.filters;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookFilter {
    private String isbn;
    private String title;
    private String year;
    private String author;
    private String category;
}
