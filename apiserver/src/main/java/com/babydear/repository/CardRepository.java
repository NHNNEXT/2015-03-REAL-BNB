package com.babydear.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.babydear.model.Baby;
import com.babydear.model.Card;
import com.babydear.model.Card.State;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
	public List<Card> findByStateOrderByCIdAsc(State state);
	public List<Card> findByStateAndFIdOrderByModifiedDateDesc(State state, Long fId);
//	public List<Card> findByStateAndFIdAndBIdOrderByCIdAsc(State normal, Long fId, Long bId);
	

	// @Query("SELECT c FROM card c where c_id in (SELECT card FROM card_babies
	// WHERE babies=:bId)")
	// @Query("aaaSELECT c FROM card c where c_id in (SELECT card FROM
	// card_babies WHERE babies=1)")
	// public List<Card> findAllByBaby(@Param("bId") Long bId);

	public List<Card> findAllByBabies(List<Baby> babies);
	public Card findOneByLinkUrl(String linkUrl);
}
