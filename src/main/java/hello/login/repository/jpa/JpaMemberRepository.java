package hello.login.repository.jpa;

        import hello.login.domain.Member;
        import org.springframework.data.jpa.repository.JpaRepository;

        import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByName(String name);

}