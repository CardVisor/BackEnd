package com.project.cardvisor.repo;

import org.springframework.data.repository.CrudRepository;

import com.project.cardvisor.vo.MccVO;
import java.util.List;


public interface MccCodeRepository extends CrudRepository<MccVO, String> {
	
	MccVO findByCtgName(String ctgName);
}
