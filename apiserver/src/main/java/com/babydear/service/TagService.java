package com.babydear.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babydear.controller.UserController;
import com.babydear.model.Baby;
import com.babydear.repository.BabyRepository;

@Service
public class TagService {
	private static final Logger logger = LoggerFactory.getLogger(TagService.class);
	@Autowired BabyRepository babyRepo;

	public List<Baby> processTags(List<Long> bIds, List<Baby> babies) {
		if(bIds == null || bIds.isEmpty()) {
			if(babies == null) return null;
			logger.info(babies.toString());
			return babies;
		}
		List<Baby> babyList = new ArrayList<Baby>();
		for (Long bId : bIds) {
			Baby baby = babyRepo.findOne(bId);
			if(baby == null)continue;
			babyList.add(baby);
		}
		return babyList;
	}

}
