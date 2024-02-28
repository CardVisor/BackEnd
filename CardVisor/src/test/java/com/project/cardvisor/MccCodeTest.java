package com.project.cardvisor;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.cardvisor.repo.MccCodeRepository;
import com.project.cardvisor.vo.MccVO;

@SpringBootTest
public class MccCodeTest {

	@Autowired
	MccCodeRepository mccrepo;

	@Test
	void f1() {
		String[] mcclist = { "식비", "생활", "교통/자동차", "쇼핑", "미용", "의료/건강/피트니스", "여행/숙박", "오락", "교육", "카페/간식", "주거/통신",
				"편의점/마트/잡화", "취미/여가", "술/유흥", "보험/세금/기타금융", "기타" };
		IntStream.range(0, mcclist.length).forEach(i -> {
			MccVO mcc = MccVO.builder()
					.mccCode(String.format("%04d", i+1))
					.ctgName(mcclist[i])
					.build();
			mccrepo.save(mcc);
		});
	}
}
