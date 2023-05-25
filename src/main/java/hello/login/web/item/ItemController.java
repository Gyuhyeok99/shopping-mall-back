package hello.login.web.item;

import hello.login.domain.item.Item;
import hello.login.domain.item.ItemRepository;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.SessionConst;
import hello.login.web.item.form.ItemSaveForm;
import hello.login.web.item.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;


    @GetMapping
    public String items(Model model, HttpServletRequest request) {
        List<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("member", loginMember);
        return "items/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model, HttpServletRequest request) {
        Item item = itemRepository.findById(itemId);
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("member", loginMember);
        model.addAttribute("item", item);

        //등록한 사람과 로그인한 회원 이름이 같으면 나의 아이템 아니면 남의 아이템 페이지로
        if(loginMember.getName().equals(item.getRaisedMember())) {
            return "/items/myItem";
        }
        return "/items/otherItem";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "items/addForm";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult
            , RedirectAttributes redirectAttributes, HttpServletRequest request) {

        //특정 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "items/addForm";
        }
        //성공 로직

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setPrice(form.getPrice());
        item.setQuantity(form.getQuantity());
        item.setRaisedMember(member.getName());

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "items/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm form, BindingResult bindingResult) {

        //특정 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "items/editForm";
        }

        Item itemParam = new Item();
        itemParam.setItemName(form.getItemName());
        itemParam.setPrice(form.getPrice());
        itemParam.setQuantity(form.getQuantity());

        itemRepository.update(itemId, itemParam);
        return "redirect:/items/{itemId}";
    }
    @PostMapping("/{itemId}/purchase")
    public String purchaseItem(@PathVariable Long itemId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Item item = itemRepository.findById(itemId);

        // 아이템 가격과 회원의 돈을 비교하여 구매 가능 여부 확인 + 아이템 수량이 0개 이상인지 확인
        if (loginMember.getMoney() >= item.getPrice() && item.getQuantity() > 0) {
            // 아이템 구매
            itemRepository.purchaseItem(itemId);


            // 구매한 아이템 정보 업데이트

            Map<String, Integer> purchaseItems = loginMember.getPurchaseItems();
            if (purchaseItems.containsKey(item.getItemName())) {
                // 이미 구매한 아이템인 경우 수량 증가
                int currentQuantity = purchaseItems.get(item.getItemName());
                purchaseItems.put(item.getItemName(), currentQuantity + 1);
            } else {
                // 처음 구매하는 아이템인 경우 추가
                purchaseItems.put(item.getItemName(), 1);
            }

            // 돈 차감 및 회원 정보 업데이트
            int newMoney = loginMember.getMoney() - item.getPrice();
            loginMember.setMoney(newMoney);
            memberRepository.update(loginMember.getId(), loginMember);

            // 등록한 회원에게 돈 지급
            Optional<Member> raisedMember = memberRepository.findByName(item.getRaisedMember());
            int raisedMemberMoney = raisedMember.map(Member::getMoney).orElse(0) + item.getPrice();
            raisedMember.ifPresent(member -> member.setMoney(raisedMemberMoney));
            raisedMember.ifPresent(member -> {
                Long memberId = member.getId();
                member.setMoney(raisedMemberMoney);
                memberRepository.update(memberId, member);
            });



            // 구매 완료 메시지 등을 처리한 후 이동할 페이지로 리다이렉트
            return "redirect:/items/" + itemId + "?status=success";
        } else {
            // 돈이 부족하여 구매 불가능한 경우 처리
            return "redirect:/items/" + itemId + "?status=failed";
        }
    }

}
