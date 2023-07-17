package hello.login.controller;

import hello.login.repository.ItemRepository;
import hello.login.domain.member.GenderType;
import hello.login.domain.Member;
import hello.login.domain.member.MobileCarrierType;
import hello.login.repository.jpa.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final JpaMemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @ModelAttribute("hobbies")
    public Map<String, String>hobbies() {
        Map<String, String> hobbies = new LinkedHashMap<>();
        hobbies.put("운동", "운동");
        hobbies.put("독서", "독서");
        hobbies.put("여행", "여행");
        hobbies.put("음악감상", "음악감상");
        hobbies.put("기타", "기타");
        return hobbies;
    }

    @ModelAttribute("genderTypes")
    public GenderType[] genderTypes() {
        return GenderType.values();
    }

    @ModelAttribute("mobileCarrierType")
    public List<MobileCarrierType> mobileCarrierTypes() {
        List<MobileCarrierType> mobileCarrierTypes = new ArrayList<>();
        mobileCarrierTypes.add(new MobileCarrierType("SKT", "SKT"));
        mobileCarrierTypes.add(new MobileCarrierType("LG", "LG"));
        mobileCarrierTypes.add(new MobileCarrierType("KT", "KT"));
        return mobileCarrierTypes;

    }
    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult){


        if(member.getLoginId() != null) {
            if (!(memberRepository.findByLoginId(member.getLoginId()).isEmpty())) {
                bindingResult.reject("existingMemberLoginId", null);
            }
        }

        if(member.getName() != null) {
            if(!(memberRepository.findByName(member.getName()).isEmpty())) {
                bindingResult.reject("existingMemberName", null);
            }
        }
        if(bindingResult.hasErrors()){
            return "members/addMemberForm";
        }

        memberRepository.save(member);
        return "redirect:/";
    }

    @GetMapping("/userInfo/{memberId}")
    public String userInfo(@PathVariable long memberId, Model model) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member member = optionalMember.orElseThrow(() -> new IllegalArgumentException("Invalid member Id: " + memberId));
        model.addAttribute("member", member);
        Set<String> itemNames = member.getPurchaseItems().keySet();

        return "members/info";
    }
}
