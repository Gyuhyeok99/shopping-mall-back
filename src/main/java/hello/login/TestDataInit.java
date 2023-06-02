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

        Member member = new Member();
        member.setLoginId("test12");
        member.setPassword("test!@#$");
        member.setName("관리자");
        member.setGender(GenderType.MAN);
        List<String> test = new ArrayList<>();
        test.add("운동");
        test.add("독서");
        member.setHobbies(test);
        member.setMobileCarrier("SKT");
        member.setPhone(1012345678);
        member.setIntroduce("안녕하세요~");

        Member member1 = new Member();
        member1.setLoginId("test23");
        member1.setPassword("test@#$%");
        member1.setName("테스터1");
        member1.setGender(GenderType.MAN);
        member1.setMobileCarrier("LG");
        member1.setPhone(1011112222);

        Member member2 = new Member();
        member2.setLoginId("test34");
        member2.setPassword("test#$%^");
        member2.setName("테스터2");
        member2.setGender(GenderType.WOMAN);
        member2.setMobileCarrier("KT");
        member2.setPhone(1033334444);

        memberRepository.save(member);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Board board1 = new Board("제목1", "내용1", "관리자");
        Board board2 = new Board("제목2", "내용2", "관리자");

        boardRepository.save(board1);
        boardRepository.save(board2);
    }

}