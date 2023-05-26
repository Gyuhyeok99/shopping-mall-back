package hello.login.repository;
import hello.login.domain.Member;

import java.util.*;


public interface MemberRepository {
    Member save(Member member);

    Member findById(Long id);

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByName(String name);

    List<Member> findAll();

    void memberDelete(Member member);

    void clearStore();

    void update(Long id, Member updatedMember);

}
