package hello.login;

import hello.login.domain.Board;
import hello.login.repository.BoardRepository;
import hello.login.domain.Item;
import hello.login.repository.ItemRepository;
import hello.login.domain.member.GenderType;
import hello.login.domain.Member;
import hello.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        Item itemA = new Item("itemA", 10000, 10);
        itemA.setRaisedMember("테스터1");
        Item itemB = new Item("itemB", 20000, 20);
        itemB.setRaisedMember("테스터2");
        itemRepository.save(itemA);
        itemRepository.save(itemB);



        Board board1 = new Board("제목1", "내용1", "테스터1");
        Board board2 = new Board("제목2", "내용2", "테스터2");

        boardRepository.save(board1);
        boardRepository.save(board2);
    }

}