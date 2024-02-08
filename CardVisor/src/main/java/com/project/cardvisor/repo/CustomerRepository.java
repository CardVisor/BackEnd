package com.project.cardvisor.repo;

import org.springframework.data.repository.CrudRepository;

import com.project.cardvisor.vo.CustomerVO;

public interface CustomerRepository extends CrudRepository<CustomerVO, String> {

	
}
