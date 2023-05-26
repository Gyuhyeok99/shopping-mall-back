package hello.login.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {

    private Long id;
    private String title; // 제목
    private String content; // 내용
    private String author = " "; //작성자
    private LocalDateTime createDateTime; // 작성 시간 (년월일시분초)
    private Integer view; //조회수
    public Board() {}

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
