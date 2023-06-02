package hello.login.repository.memory;

import hello.login.domain.Member;
import hello.login.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new ConcurrentHashMap<>(); //static 사용
    private static long sequence = 0L; //static 사용

    public Member save(Member member) {
        member.setId(++sequence);
        member.setMoney(100000);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    //아이디로 멤버 찾기
    public Member findById(Long id){
        return store.get(id);
    }

    //로그인 아이디로 멤버 찾기
    public Optional<Member> findByLoginId(String loginId){
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    //이름으로 멤버 찾기
    public Optional<Member> findByName(String name) {
        return findAll().stream()
                .filter(m -> m.getName().equals(name))
                .findFirst();
    }

    //모든 멤버 찾기
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    //멤버 삭제하기
    public void memberDelete(Member member) {
        store.remove(member.getId());
    }
    //테스트를 위한 저장소 비우기
    public void clearStore() {
        store.clear();
    }

    //아이템 구매할 때 정보 업데이트하기
    public void update(Long id, Member updatedMember) {
        Member member = findById(id);
        member.setMoney(updatedMember.getMoney());
        member.setPurchaseItems(updatedMember.getPurchaseItems());
    }


}
