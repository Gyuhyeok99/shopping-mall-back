package hello.login.repository.mysql;

import hello.login.domain.Member;
import hello.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class MysqlMemberRepository implements MemberRepository {

    private final EntityManager em;

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        List<Member> members = em.createQuery("SELECT m FROM Member m WHERE m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList();
        return members.stream().findFirst();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> members = em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return members.stream().findFirst();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }

    @Override
    public void memberDelete(Member member) {
        em.remove(member);
    }

    @Override
    public void clearStore() {
        em.createQuery("DELETE FROM Member").executeUpdate();
    }

    @Override
    public void update(Long id, Member updatedMember) {
        Member member = findById(id);
        member.setMoney(updatedMember.getMoney());
        member.setPurchaseItems(updatedMember.getPurchaseItems());
    }
}
