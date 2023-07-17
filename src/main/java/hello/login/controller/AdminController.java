package hello.login.controller;

import hello.login.domain.Member;
import hello.login.repository.jpa.JpaMemberRepository;
import hello.login.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final JpaMemberRepository memberRepository;


    @GetMapping
    public String admin(@ModelAttribute("member") Member member, HttpServletRequest request, Model model) {
        //로그인 멤버 정보
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(!(loginMember.getId().equals(1) && loginMember.getLoginId().equals("test12"))) {
            return "redirect:/items";
        }

        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "admin/admin";
    }

    //회원 삭제하는 기능
    @PostMapping("/{memberId}/delete")
    public String delete(@PathVariable("memberId") long memberId) {
        log.info("memberId={}", memberId);
        memberRepository.deleteById(memberId);
        return "redirect:/admin";
    }
}
