package hello.login.controller;

import hello.login.domain.Member;
import hello.login.repository.MemberRepository;
import hello.login.repository.jpa.JpaMemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.seesion.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final JpaMemberRepository memberRepository;
    private final SessionManager sessionManager;


    @GetMapping("/")
    public String homeLogin(@Login Member loginMember, Model model) {

        //세션에 회원 데이터가 없으면 home
        if(loginMember == null) {
            return "home";
        }
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}