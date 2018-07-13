package com.bootshop.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootshop.model.Role;
import com.bootshop.repository.RoleRepository;


@Service
@Transactional
public class RoleServiceImpl {
	@Autowired
	private RoleRepository roleRepository;
	
	public void save(Role role){
		roleRepository.save(role);
	}
}
