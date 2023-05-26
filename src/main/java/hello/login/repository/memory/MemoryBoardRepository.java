package hello.login.repository.memory;

import hello.login.domain.Board;
import hello.login.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Slf4j
public class MemoryBoardRepository implements BoardRepository {

    private static final Map<Long, Board> store = new ConcurrentHashMap<>();

    private static long sequence = 0L;

    public Board save(Board board) {
        board.setId(++sequence);
        board.setView(0);
        log.info("save: board={}", board);
        store.put(board.getId(), board);
        return board;
    }

    public Board findById(Long id) {
        return store.get(id);
    }

    public List<Board> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long boardId, Board updateParam) {
        Board byId = findById(boardId);
        byId.setTitle(updateParam.getTitle());
        byId.setContent(updateParam.getContent());
        byId.setCreateDateTime(updateParam.getCreateDateTime());
    }

    public void clearStore() {
        store.clear();
    }
}
