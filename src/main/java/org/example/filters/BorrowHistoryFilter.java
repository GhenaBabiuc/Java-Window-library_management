package org.example.filters;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowHistoryFilter {
    private String userIdn;
    private String username;
    private String bookIsbn;
    private String bookTitle;
    private Date afterDate;
    private Date beforeDate;
}
