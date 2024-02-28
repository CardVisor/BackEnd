package com.project.cardvisor.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.project.cardvisor.composite.CardBenefitComposite;
import com.project.cardvisor.vo.CardBenefitVO;
import com.project.cardvisor.vo.CardListVO;

public interface CardBenefitRepository extends CrudRepository<CardBenefitVO, CardBenefitComposite>{
	
	@Query(value="SELECT c FROM CardBenefitVO c WHERE c.id.cardType = :card_type")
	List<CardBenefitVO> findbyCard_type(@Param("card_type") int card_type);
}
