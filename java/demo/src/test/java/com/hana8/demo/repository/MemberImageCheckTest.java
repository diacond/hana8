package com.hana8.demo.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hana8.demo.entity.Member;
import com.hana8.demo.entity.MemberImage;

@SpringBootTest
@Transactional
class MemberImageCheckTest {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void checkMember1Image() {
		Member member = memberRepository.findById(1L).orElseThrow();
		System.out.println("Member nickname: " + member.getNickname());
		System.out.println("Member images count: " + member.getImages().size());
		
		boolean found = false;
		for (MemberImage image : member.getImages()) {
			System.out.println("Image: " + image.getSavedir() + "/" + image.getSavename());
			if ("2026/03/11".equals(image.getSavedir()) && "6ce3149b-9b48-4c4e-9467-86f74a1a605c.png".equals(image.getSavename())) {
				found = true;
			}
		}
		
		assertThat(found).isTrue();
	}
}
