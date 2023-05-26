package hello.login.domain;

import hello.login.domain.member.GenderType;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", nullable = false, length = 12)
    private String loginId; //로그인 ID


    @Column(nullable = false, length = 8)
    @Length(min = 2, max = 8)
    private String name; //사용자 이름

    @Column(nullable = false, length = 20)
    @Length(min = 8, max = 20)
    private String password;

    @Column(name = "mobileCarrier", nullable = false, length = 255)
    @NotEmpty
    private String mobileCarrier; //통신사

    @Column(nullable = false)
    @NotNull
    private Integer phone; //전화번호

    @Column(nullable = false, length = 255)
    @NotNull
    private GenderType gender; //성별

    @ElementCollection
    @CollectionTable(name = "member_hobbies", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "hobby")
    private List<String> hobbies; //취미

    @Column(length = 255)
    private String introduce; //자기소개

    @Column(nullable = false)
    private Integer money = 100000; //보유금액 회원 가입 시 100000원

    @ElementCollection
    @CollectionTable(name = "purchase_items", joinColumns = @JoinColumn(name = "member_id"))
    @MapKeyColumn(name = "item_name")
    @Column(name = "item_count")
    private Map<String, Integer> purchaseItems = new ConcurrentHashMap<>(); //구매한 아이템의 이름과 개수

    public Member() {}
}