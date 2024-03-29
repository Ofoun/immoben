package com.immoben.product;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.immoben.common.entity.product.Product;
import com.immoben.common.exception.ProductNotFoundException;
import com.immoben.paging.PagingAndSortingHelper;

@Service
public class ProductService {
	public static final int PRODUCTS_PER_PAGE = 10;
	public static final int SEARCH_RESULTS_PER_PAGE = 10;
	
	@Autowired private ProductRepository repo;
	
	public Page<Product> listByCategory(int pageNum, Integer categoryId) {
		String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
		
		return repo.listByCategory(categoryId, categoryIdMatch, pageable);
	}
	
	/*
	 * public Product getProduct(String alias) throws ProductNotFoundException {
	 * Product product = repo.findByAlias(alias); if (product == null) { throw new
	 * ProductNotFoundException("Aucun produit avec l'alias: " + alias +
	 * " n'a été trouvé !"); }
	 * 
	 * return product; }
	 */
	
	
	public Product getProduct(Integer id) throws ProductNotFoundException {
		Product product = repo.getById(id);
		
		if (product == null) {
			throw new ProductNotFoundException("Aucun produit avec l'id: "  + id + " n'a été trouvé !");
		}
		
		return product;
	}


	public Page<Product> search(String keyword,  int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);


			
		String cityIdMatch = null;
		return repo.search(keyword, pageable);
		
	}
	
	
	
	/* ----------------------------------------------------------------- */
	

	

	
	public List<Product> listAll() {
		return (List<Product>) repo.findAll();
	}
	

	
	
//	public void listByPage(int pageNum, PagingAndSortingHelper helper, Integer categoryId) {
//		Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
//		String keyword = helper.getKeyword();
//		Page<Product> page = null;
//
//		if (keyword != null && !keyword.isEmpty()) {
//			if (categoryId != null && categoryId > 0) {
//				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
//				page = repo.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
//			} else {
//				page = repo.findAll(keyword, pageable);
//			}
//		} else {
//			if (categoryId != null && categoryId > 0) {
//				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
//				page = repo.findAllInCategory(categoryId, categoryIdMatch, pageable);
//			} else {
//				page = repo.findAll(pageable);
//			}
//		}
//
//		helper.updateModelAttributes(pageNum, page);
//	}
	 
	
	

	
	public Page<Product> listByPage(int pageNum, String sortField, String sortDir, 
			String keyword, Integer categoryId, Integer cityId) {
		Sort sort = Sort.by(sortField);
		Page<Product> page = null;
		
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
				
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);


		if (keyword != null && !keyword.isEmpty()) {
			if (categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";				
				
				if (cityId != null && cityId > 0) {
					String cityIdMatch = "-" + String.valueOf(cityId) + "-";
					
					page = repo.searchInCategoryAndCity( keyword, categoryId, categoryIdMatch, cityId, cityIdMatch,pageable);
					
					} else {
						page = repo.searchInCategory( keyword, categoryId, categoryIdMatch, pageable);
					}
				} else {
										
					if (cityId != null && cityId > 0) {
						String cityIdMatch = "-" + String.valueOf(cityId) + "-";
						
						page = repo.searchInCity(keyword, cityId, cityIdMatch, pageable);
						
					} else {
						page = repo.findAll(keyword, pageable);
					}					
				}								
			} else {
				if (categoryId != null && categoryId > 0) {
					String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
					
					if (cityId != null && cityId > 0) {
						String cityIdMatch = "-" + String.valueOf(cityId) + "-";
						
						page = repo.findAllInCategoryAndCity(categoryId, categoryIdMatch, cityId, cityIdMatch, pageable);
						} else {
							page = repo.findAllInCategory(categoryId, categoryIdMatch,pageable);
							}
					} else {
						if (cityId != null && cityId > 0) {
							String cityIdMatch = "-" + String.valueOf(cityId) + "-";
							
							page = repo.findAllInCity(cityId, cityIdMatch, pageable);
						} else {
							page = repo.findAll(pageable);						
						}
					}
				}
		
		return repo.findAll(pageable);		
	}	
	
	
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper, Integer categoryId, Integer cityId) {
		Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
		String keyword = helper.getKeyword();
		Page<Product> page = null;

		if (keyword != null && !keyword.isEmpty()) {
			if (categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";				
				
				if (cityId != null && cityId > 0) {
					String cityIdMatch = "-" + String.valueOf(cityId) + "-";
					
					page = repo.searchInCategoryAndCity( keyword, categoryId, categoryIdMatch, cityId, cityIdMatch,pageable);
					
					} else {
						page = repo.searchInCategory( keyword, categoryId, categoryIdMatch, pageable);
					}
				} else {
										
					if (cityId != null && cityId > 0) {
						String cityIdMatch = "-" + String.valueOf(cityId) + "-";
						
						page = repo.searchInCity(keyword, cityId, cityIdMatch, pageable);
						
					} else {
						page = repo.findAll(keyword, pageable);
					}					
				}								
			} else {
				if (categoryId != null && categoryId > 0) {
					String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
					
					if (cityId != null && cityId > 0) {
						String cityIdMatch = "-" + String.valueOf(cityId) + "-";
						
						page = repo.findAllInCategoryAndCity(categoryId, categoryIdMatch, cityId, cityIdMatch, pageable);
						} else {
							page = repo.findAllInCategory(categoryId, categoryIdMatch,pageable);
							}
					} else {
						if (cityId != null && cityId > 0) {
							String cityIdMatch = "-" + String.valueOf(cityId) + "-";
							
							page = repo.findAllInCity(cityId, cityIdMatch, pageable);
						} else {
							page = repo.findAll(pageable);						
						}
					}
				}
					
			helper.updateModelAttributes(pageNum, page);
		}
	
	/*
	 * public void listByPage(int pageNum, PagingAndSortingHelper helper, Integer
	 * categoryId) { Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE,
	 * pageNum); String keyword = helper.getKeyword(); Page<Product> page = null;
	 * 
	 * if (keyword != null && !keyword.isEmpty()) { if (categoryId != null &&
	 * categoryId > 0) { String categoryIdMatch = "-" + String.valueOf(categoryId) +
	 * "-"; page = repo.searchInCategory(categoryId, categoryIdMatch, keyword,
	 * pageable); } else { page = repo.findAll(keyword, pageable); } } else { if
	 * (categoryId != null && categoryId > 0) { String categoryIdMatch = "-" +
	 * String.valueOf(categoryId) + "-"; page = repo.findAllInCategory(categoryId,
	 * categoryIdMatch, pageable); } else { page = repo.findAll(pageable); } }
	 * 
	 * helper.updateModelAttributes(pageNum, page); }
	 */
	 	
	
	public void searchProducts(int pageNum, PagingAndSortingHelper helper) {
		Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
		String keyword = helper.getKeyword();		
		Page<Product> page = repo.searchProductsByName(keyword, pageable);		
		helper.updateModelAttributes(pageNum, page);
	}
	
	public Product save(Product product) {
		if (product.getId() == null) {
			product.setCreatedTime(new Date());
		}
		
		if (product.getAlias() == null || product.getAlias().isEmpty()) {
			String defaultAlias = product.getName().replaceAll(" ", "-");
			product.setAlias(defaultAlias);
		} else {
			product.setAlias(product.getAlias().replaceAll(" ", "-"));
		}
		
		product.setUpdatedTime(new Date());
		
		return repo.save(product);
	}
	
	public void saveProductPrice(Product productInForm) {
		Product productInDB = repo.findById(productInForm.getId()).get();
		productInDB.setCost(productInForm.getCost());
		productInDB.setPrice(productInForm.getPrice());
		productInDB.setDiscountPercent(productInForm.getDiscountPercent());
		
		repo.save(productInDB);
	}
	
	
	
	/*
	 * public String checkUnique(Integer id, String name) { boolean isCreatingNew =
	 * (id == null || id == 0); List<Product> productByName = repo.findByName(name);
	 * 
	 * if (isCreatingNew) { if (productByName != null) return "Duplicate"; } else {
	 * if (productByName != null && ((IdBasedEntity) productByName).getId() != id) {
	 * return "Duplicate"; } }
	 * 
	 * return "OK"; }
	 */
	 
	
	/*
	 * public void updateProductEnabledStatus(Integer id, boolean enabled) {
	 * repo.updateEnabledStatus(id, enabled); }
	 */
	
	public void delete(Integer id) throws ProductNotFoundException {
		Long countById = repo.countById(id);
		
		if (countById == null || countById == 0) {
			throw new ProductNotFoundException("Aucun produit avec ID: " + id + " n'est trouvable.");			
		}
		
		repo.deleteById(id);
	}	
	
	public Product get(Integer id) throws ProductNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new ProductNotFoundException("Aucun produit avec ID: " + id + " n'est trouvable.");
		}
	}
	



	public Page<Product> search_category_city_district (String categoryIdMatch, String cityIdMatch, String keyword, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);
		return repo.search_category_city_district(categoryIdMatch, cityIdMatch, keyword, pageable);
		
	}



}

