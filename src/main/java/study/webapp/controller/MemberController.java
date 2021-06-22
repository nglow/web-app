package study.webapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.webapp.domain.Address;
import study.webapp.domain.Member;
import study.webapp.service.MemberService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        var address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        var member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    // 화면을 뿌릴때도 dto로 변환하여 넘기는 것이 좋음. but, mvc패턴일경우 같은 서버안에서 돌아가는것이기 때문에 괜찮을 수 있음.
    @GetMapping("/members")
    public String retrieveMembers(Model model) {
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }
}
