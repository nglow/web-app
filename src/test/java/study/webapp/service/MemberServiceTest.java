package study.webapp.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.webapp.domain.Member;
import study.webapp.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    @DisplayName("회원가입")
    public void testJoin() {
        var member = new Member();
        member.setName("kim");

        memberService.join(member);
        var memberRetrieved = memberService.findOne(member.getId());

        assertThat(member).isEqualTo(memberRetrieved);
    }

    @Test
    @DisplayName("중복 회원 예외")
    public void testDuplicationException() {
        var member = new Member();
        member.setName("kim");
        memberService.join(member);

        var member2 = new Member();
        member2.setName("kim");

        assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
    }

}