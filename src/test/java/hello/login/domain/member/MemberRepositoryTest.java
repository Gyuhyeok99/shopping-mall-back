package hello.login.domain.member;

import hello.login.domain.Member;
import hello.login.repository.memory.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class MemberRepositoryTest {


    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        Member member = new Member();
        member.setName("테스터");
        member.setLoginId("test");
        member.setPassword("test!");

        Member saveMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    void findAll() {
        Member member1 = new Member();
        member1.setName("테스터1");
        member1.setLoginId("test");
        member1.setPassword("test!");

        Member member2 = new Member();
        member2.setName("테스터2");
        member2.setLoginId("test");
        member2.setPassword("test!");

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAll();

        assertThat(members.size()).isEqualTo(2);
        assertThat(members).contains(member1, member2);
    }

    @Test
    void memberDelete() {
        Member member1 = new Member();
        member1.setName("테스터1");
        member1.setLoginId("test");
        member1.setPassword("test!");

        Member member2 = new Member();
        member2.setName("테스터2");
        member2.setLoginId("test");
        member2.setPassword("test!");

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);
        assertThat(members).contains(member1, member2);


        memberRepository.memberDelete(member1);
        members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(1);
        assertThat(members).doesNotContain(member1);


    }


}