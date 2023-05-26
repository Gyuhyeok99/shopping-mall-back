package hello.login.repository;

import hello.login.domain.Board;

import java.util.List;


public interface BoardRepository {

    public Board save(Board board);

    public Board findById(Long id);

    public List<Board> findAll();

    public void update(Long boardId, Board updateParam);

    public void clearStore();
}
