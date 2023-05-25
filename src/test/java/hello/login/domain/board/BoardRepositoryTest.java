package hello.login.domain.board;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BoardRepositoryTest {

    BoardRepository boardRepository = new BoardRepository();

    @AfterEach
    void afterEach() {
        boardRepository.clearStore();
    }

    @Test
    void save() {

        Board board = new Board("제목", "내용", "테스터");

        Board saveBoard = boardRepository.save(board);

        Board findBoard = boardRepository.findById(board.getId());
        System.out.println(saveBoard.getCreateDateTime());
        assertThat(findBoard).isEqualTo(saveBoard);
    }

    @Test
    void finaAll() {
        Board board1 = new Board("제목1", "내용2", "테스터1");
        Board board2 = new Board("제목2", "내용2", "테스터2");

        boardRepository.save(board1);
        boardRepository.save(board2);

        List<Board> result = boardRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(board1, board2);
    }

    @Test
    void updateBoard() {
        Board board = new Board("제목1", "내용2", "테스터");

        Board saveBoard = boardRepository.save(board);
        Long boardId = saveBoard.getId();

        Board updateBoard = new Board("제목2", "내용2");
        boardRepository.update(boardId, updateBoard);

        Board findBoard = boardRepository.findById(boardId);

        assertThat(findBoard.getTitle()).isEqualTo(updateBoard.getTitle());
        assertThat(findBoard.getContent()).isEqualTo(updateBoard.getContent());
        assertThat(findBoard.getCreateDateTime()).isEqualTo(updateBoard.getCreateDateTime());
    }

}