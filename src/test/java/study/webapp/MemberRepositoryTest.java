package study.webapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.webapp.domain.Member;
import study.webapp.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@Rollback(false) // 테스트코드가 돌아간 후 Rollback하지 않고 DB에 적용됨
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원 테스트")
    public void testMember() {
        var member = new Member();
        member.setName("memberA");

        var id = memberRepository.save(member);
        var memberRetrieved = memberRepository.findOne(id);

        assertThat(memberRetrieved.getId()).isEqualTo(member.getId());
        assertThat(memberRetrieved.getName()).isEqualTo(member.getName());
        assertThat(memberRetrieved).isEqualTo(member);

    }
}