package com.immoben.city;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.immoben.paging.SearchRepository;
import com.immoben.common.entity.City;

public interface CityRepository extends SearchRepository<City, Integer> {
	
	public Long countById(Integer id);
	
	public City findByName(String name);
	
	@Query("SELECT c FROM City c WHERE c.name LIKE %?1%")
	public Page<City> findAll(String keyword, Pageable pageable);
	
	@Query("SELECT NEW City(c.id, c.name) FROM City c ORDER BY c.name ASC")
	public List<City> findAll();
	
	
	/*------------------------------------------------------------*/
	
	/*
	 * @Query("SELECT c FROM City c WHERE c.enabled = true ORDER BY c.name ASC")
	 * public List<City> findAllEnabled();
	 */
	
	/*
	 * @Query("SELECT c FROM City c WHERE c.enabled = true AND c.alias = ?1") public
	 * City findByAliasEnabled(String alias);
	 */
	
	 @Query("SELECT c FROM City c WHERE c.id = ?1") 
	public List<City> findRootCities(Sort sort);

	 @Query("SELECT c FROM City c WHERE c.id = ?1") 
	public Page<City> findRootCities(Pageable pageable);
	
	@Query("SELECT c FROM City c WHERE c.name LIKE %?1%")
	public Page<City> search(String keyword, Pageable pageable);

	/* public City findByAlias(String alias); */
	
	/*
	 * @Query("UPDATE City c SET c.enabled = ?2 WHERE c.id = ?1")
	 * 
	 * @Modifying public void updateEnabledStatus(Integer id, boolean enabled);
	 */
}
