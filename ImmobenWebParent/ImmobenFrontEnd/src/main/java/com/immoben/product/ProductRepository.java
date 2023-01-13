package com.immoben.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.immoben.common.entity.product.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

	@Query("SELECT p FROM Product p WHERE p.enabled = true "
			+ "AND (p.category.id = ?1 OR p.category.allParentIDs LIKE %?2%)"
			+ " ORDER BY p.name ASC")
	public Page<Product> listByCategory(Integer categoryId, String categoryIDMatch, Pageable pageable);
	
	@Query(value = "SELECT p FROM Product p WHERE p.id = ?1")
	Product getById(Integer id);
	
	/*
	 * @Query(value = "SELECT p FROM Product p WHERE p.alias =:alias AND p.id =:id")
	 */
	
	
	/*
	 * @Query(value =
	 * "SELECT p FROM Product p WHERE p.alias LIKE %?1% AND p.id = ?1")
	 */
	 
	  public Product findByAliasAndId(String alias, Integer id);
	 
	
		/* @Query(value = "SELECT p FROM Product p WHERE p.alias =:alias") */ 
		/* @Query(value = "SELECT p FROM Product p WHERE p.alias LIKE %?1%") */
	 public Product findByAlias(String alias);
	/*	 public List<Product> findByAlias(String alias);  */
	
	/*
	 * @Query(value = "SELECT * FROM Product WHERE enabled = true AND " +
	 * "MATCH(name, short_description, full_description) AGAINST (?1)", nativeQuery
	 * = true) public Page<Product> search(String keyword, Pageable pageable);
	 */
	 
	/*
	 * @Query(value =
	 * "SELECT p FROM Product p WHERE CONCAT(p.name, p.city.name, p.category.name, p.district) LIKE %?1%"
	 * , nativeQuery = true) public Page<Product> search(String keyword, Pageable
	 * pageable);
	 */
	 
	/*
	 * @Query(value = "SELECT p FROM Product p WHERE p.name LIKE %?1% " +
	 * "OR p.city.name LIKE %?2% " + "OR p.category.name LIKE %?3% " +
	 * "OR p.district LIKE %?4%", nativeQuery = true)
	 */
//	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
//	public Page<Product> search(String keyword, Pageable pageable);
	
	@Query(value = "SELECT * FROM Products WHERE enabled = true AND "
			+ "MATCH(alias, short_description, full_description, district) AGAINST (?1)", 
			nativeQuery = true)
	public Page<Product> search(String keyword, Pageable pageable);

	
	/*----------------------------------------------------------*/
	
	@Query("SELECT p FROM Product p WHERE ((p.category.id = ?1 " 
			+ "OR p.category.allParentIDs LIKE %?2%) AND "
			+ "(p.name LIKE %?3% " 
			+ "OR p.shortDescription LIKE %?3% " 
			+ "OR p.fullDescription LIKE %?3% "
			+ "OR p.city.name LIKE %?3% " 
			+ "OR p.category.name LIKE %?3%)) AND "
			+ "(p.city.id = ?4 AND "
			+ "(p.name LIKE %?5% " 
			+ "OR p.shortDescription LIKE %?5% " 
			+ "OR p.fullDescription LIKE %?5% "
			+ "OR p.city.name LIKE %?5% " 
			+ "OR p.category.name LIKE %?5%))")	
	public Page<Product> searchInCategoryAndCity(String keyword, Integer categoryId, 
			String categoryIdMatch, Integer cityId, String cityIdMatch, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE (p.category.id = ?1 " 
			+ "OR p.category.allParentIDs LIKE %?2%) AND "
			+ "(p.name LIKE %?3% " 
			+ "OR p.shortDescription LIKE %?3% " 
			+ "OR p.fullDescription LIKE %?3% "
			+ "OR p.city.name LIKE %?3% " 
			+ "OR p.category.name LIKE %?3%)")
	public Page<Product> searchInCategory(String keyword, Integer categoryId, String categoryIdMatch,
			Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.city.id = ?1 AND "
			+ "(p.name LIKE %?2% " 
			+ "OR p.shortDescription LIKE %?2% " 
			+ "OR p.fullDescription LIKE %?2% "
			+ "OR p.city.name LIKE %?2% " 
			+ "OR p.category.name LIKE %?2%)")
	public Page<Product> searchInCity(String keyword, Integer cityId, String cityIdMatch, Pageable pageable);
	
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1% " 
			+ "OR p.district LIKE %?1% "
			+ "OR p.shortDescription LIKE %?1% "
			+ "OR p.fullDescription LIKE %?1% "
			+ "OR p.city.name LIKE %?1% "
			+ "OR p.category.name LIKE %?1%")
	public Page<Product> findAll(String keyword, Pageable pageable);
	
	
	@Query("SELECT p FROM Product p WHERE (p.category.id = ?1 "
			+ "OR p.category.allParentIDs LIKE %?2%) AND " 
			+ "p.city.id = ?3 ")	
	public Page<Product> findAllInCategoryAndCity(Integer categoryId, String categoryIdMatch, Integer cityId, 
			String cityIdMatch, Pageable pageable);
	
	
	@Query("SELECT p FROM Product p WHERE p.category.id = ?1 "
			+ "OR p.category.allParentIDs LIKE %?2%")	
	public Page<Product> findAllInCategory(Integer categoryId, String categoryIdMatch, 
			Pageable pageable);
	
	
	@Query("SELECT p FROM Product p WHERE p.city.id = ?1 ")	
	public Page<Product> findAllInCity(Integer cityId, String cityIdMatch, 
			Pageable pageable);
	
	
	public Page<Product> findAll(Pageable pageable);
	
	
	
	
	
	/*-----------------------------------------------------------------------------*/	
	
	
		 
	/*
	 * @Query(value =
	 * "SELECT p FROM Product p WHERE CONCAT(p.name, p.city, p.category, p.district) LIKE %?1%"
	 * , nativeQuery = true) public Page<Product> search_two(String keyword,
	 * Pageable pageable);
	 */
		
		
	public List<Product> findByName(String name);
	/* public Product findByName(String name); */
	
	/*
	 * @Query("UPDATE Product p SET p.enabled = ?2 WHERE p.id = ?1")
	 * 
	 * @Modifying public void updateEnabledStatus(Integer id, boolean enabled);
	 */
	public Long countById(Integer id);




	@Query("SELECT p FROM Product p WHERE p.category.id = ?1 "
			+ "OR p.category.allParentIDs LIKE %?2% "
			+ "OR p.city.id = ?3 ")	
	public Page<Product> findAllInCategoryORCity(Integer categoryId, String categoryIdMatch, Integer cityId, String cityIdMatch, 
			Pageable pageable);
	
//	@Query("SELECT p FROM Product p WHERE (p.category.id = ?1 AND p.city.id = ?1 AND p.district.id = ?1"
//			+ "OR p.category.allParentIDs LIKE %?2%) AND "
//			+ "(p.name LIKE %?3% " 
//			+ "OR p.shortDescription LIKE %?3% "
//			+ "OR p.fullDescription LIKE %?3% "
//			+ "OR p.city.name LIKE %?3% "
//			+ "OR p.district.name LIKE %?3% "
//			+ "OR p.category.name LIKE %?3%)")		
	@Query("SELECT p FROM Product p WHERE p.city.name LIKE %?1% "
			+ "OR p.district.name LIKE %?1% "
			+ "OR p.category.name LIKE %?1%")	
	public Page<Product> search_category_city_district(String categoryIdMatch, String cityIdMatch, String keyword, Pageable pageable);
//	public Page<Product> search_category_city_district(String categoryIdMatch,
//			String cityIdMatch, String keyword, Pageable pageable);

	 	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
	public Page<Product> searchProductsByName(String keyword, Pageable pageable);
	

}
