package com.immoben.city;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.immoben.paging.SearchRepository;
import com.immoben.common.entity.City;

public interface CityRepository extends SearchRepository<City, Integer> {
	
	public Long countById(Integer id);
	
	public City findByName(String name);
	
	@Query("SELECT b FROM City b WHERE b.name LIKE %?1%")
	public Page<City> findAll(String keyword, Pageable pageable);
	
	@Query("SELECT NEW City(b.id, b.name) FROM City b ORDER BY b.name ASC")
	public List<City> findAll();
}
