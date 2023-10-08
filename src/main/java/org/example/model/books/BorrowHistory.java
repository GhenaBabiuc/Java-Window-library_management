package org.example.model.books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.users.User;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "borrow_history", schema = "books")
public class BorrowHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "borrow_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date borrowDate;

    @Column(name = "return_date")
    @Temporal(TemporalType.DATE)
    private Date returnDate;
}