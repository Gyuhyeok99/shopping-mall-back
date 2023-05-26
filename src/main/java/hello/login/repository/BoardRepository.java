package hello.login.repository;

import hello.login.domain.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public interface BoardRepository {

    public Board save(Board board);

    public Board findById(Long id);

    public List<Board> findAll();

    public void update(Long boardId, Board updateParam);

    public void clearStore();
}
