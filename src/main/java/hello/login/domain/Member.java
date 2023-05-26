package hello.login.domain;

import hello.login.domain.member.GenderType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Member {

    private Long id;


    @Length(min = 6, max = 12)
    private String loginId; //로그인 ID

    @Length(min = 2, max = 8)
    private String name; //사용자 이름


    @Length(min = 8, max = 20)
    private String password;

    @NotEmpty
    private String MobileCarrier; //통신사
    @NotNull
    private Integer phone; //전화번호
    @NotNull
    private GenderType gender; //성별

    private List<String> hobbies; //취미
    private String introduce; //자기소개
    private Integer money; //보유금액 회원 가입 시 100000원

    private Map<String, Integer> purchaseItems = new ConcurrentHashMap<>(); //구매한 아이템의 이름과 개수


    public Member() {}

}
