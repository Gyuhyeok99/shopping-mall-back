package hello.login.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String author = " ";
    private LocalDateTime createDateTime;
    private Integer view;

    public Board() {
    }

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
        this.createDateTime = LocalDateTime.now();
    }

    public Board(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createDateTime = LocalDateTime.now();
    }

}