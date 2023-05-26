package hello.login.domain;

import hello.login.domain.member.GenderType;
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

    @Column
    @Length(min = 6, max = 12)
    private String loginId; //로그인 ID

    @Column
    @Length(min = 2, max = 8)
    private String name; //사용자 이름

    @Column
    @Length(min = 8, max = 20)
    private String password;

    @Column
    @NotEmpty
    private String mobileCarrier; //통신사

    @Column(nullable = false)
    @NotNull
    private Integer phone; //전화번호

    @Column(nullable = false, length = 255)
    @NotNull
    private GenderType gender; //성별

    @ElementCollection
    @CollectionTable
    @Column
    private List<String> hobbies; //취미

    @Column(length = 255)
    private String introduce; //자기소개

    @Column
    private Integer money = 100000; //보유금액 회원 가입 시 100000원

    @ElementCollection
    @CollectionTable
    @MapKeyColumn
    @Column
    private Map<String, Integer> purchaseItems = new ConcurrentHashMap<>(); //구매한 아이템의 이름과 개수

    public Member() {}
}