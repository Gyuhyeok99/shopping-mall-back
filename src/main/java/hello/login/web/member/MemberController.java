package hello.login.web.member;

import hello.login.domain.item.ItemRepository;
import hello.login.domain.member.GenderType;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.domain.member.MobileCarrierType;
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
    private final MemberRepository memberRepository;
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
        Member member = memberRepository.findById(memberId);
        model.addAttribute("member", member);
        Set<String> itemNames = member.getPurchaseItems().keySet();

        return "members/info";
    }
}
