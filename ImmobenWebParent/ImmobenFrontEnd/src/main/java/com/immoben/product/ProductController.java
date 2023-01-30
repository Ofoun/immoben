package com.immoben.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.immoben.paging.PagingAndSortingHelper;
import com.immoben.paging.PagingAndSortingParam;
import com.immoben.category.CategoryService;
import com.immoben.city.CityService;
import com.immoben.common.entity.Category;
import com.immoben.common.entity.City;
import com.immoben.common.entity.product.Product;
import com.immoben.common.exception.CategoryNotFoundException;
import com.immoben.common.exception.ProductNotFoundException;

@Controller
public class ProductController {
	@Autowired private ProductService productService;
	@Autowired private CategoryService categoryService;
	@Autowired private CityService cityService;
	private String defaultRedirectURL = "redirect:/product/page/1?sortField=id&sortDir=desc&categoryId=0&cityId=0&keyword=null ";


	@GetMapping("/c/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias,
			Model model) {
		return viewCategoryByPage(alias, 1, model);
	}
	
	@GetMapping("/c/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias,
			@PathVariable("pageNum") int pageNum,
			Model model) {
		try {
			Category category = categoryService.getCategory(alias);		
			List<Category> listCategoryParents = categoryService.getCategoryParents(category);
			
			Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
			List<Product> listProducts = pageProducts.getContent();
			
			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
			if (endCount > pageProducts.getTotalElements()) {
				endCount = pageProducts.getTotalElements();
			}
			
			
			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", pageProducts.getTotalElements());
			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("category", category);
			
			return "product/products_by_category";
		} catch (CategoryNotFoundException ex) {
			return "error/404";
		}
	}/*
		 * 
		 * 
		 * @GetMapping("/p/{product_alias}") public String
		 * viewProductDetail(@PathVariable("product_alias") String alias, Model model,
		 * HttpServletRequest request) {
		 * 
		 * try { Product product = productService.getProduct(alias); List<Category>
		 * listCategoryParents =
		 * categoryService.getCategoryParents(product.getCategory());
		 * 
		 * model.addAttribute("listCategoryParents", listCategoryParents);
		 * model.addAttribute("product", product); model.addAttribute("pageTitle",
		 * (product.getShortName()));
		 * 
		 * return "product/product_detail"; } catch (ProductNotFoundException e) {
		 * return "error/404"; } }
		 */

	


	
	@GetMapping("/p/{product_id}")
	public String viewProductDetail(@PathVariable("product_id") Integer id, Model model,
			HttpServletRequest request) {
		
		try {
			Product product = productService.getProduct(id);
			List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());
			
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("product", product);
			model.addAttribute("pageTitle", product.getShortName());
			
			return "product/product_detail";
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
	}

	 
	
	@GetMapping("/search")
	public String searchFirstPage(String keyword, Model model) {
		return searchByPage(keyword, 1, model);
	}
	
	@GetMapping("/search/page/{pageNum}")
	public String searchByPage(String keyword,
			@PathVariable("pageNum") int pageNum,
			Model model) {


		
		Page<Product> pageProducts = productService.search(keyword, pageNum);
		List<Product> listResult = pageProducts.getContent();
		
		long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1;
		if (endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("pageTitle", keyword + " - Search Result");
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("listResult", listResult);
		
		return "product/search_result";
	}
	
	/*------------------------------------------------------------*/



	 
	
//	@GetMapping("/search_two")
//	public String searchFirstHomePage(String keyword, Model model) {
//		return searchByHomePage(keyword, 1, model);
//	}
//	
//	@GetMapping("/search_two/page/{pageNum}")
//	public String searchByHomePage(String keyword,
//			@PathVariable("pageNum") int pageNum,
//			Model model) {
//		Page<Product> pageProducts = productService.search(keyword, pageNum);
//		List<Product> listResult = pageProducts.getContent();
//		
//		long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
//		long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1;
//		if (endCount > pageProducts.getTotalElements()) {
//			endCount = pageProducts.getTotalElements();
//		}
//		
//		model.addAttribute("currentPage", pageNum);
//		model.addAttribute("totalPages", pageProducts.getTotalPages());
//		model.addAttribute("startCount", startCount);
//		model.addAttribute("endCount", endCount);
//		model.addAttribute("totalItems", pageProducts.getTotalElements());
//		model.addAttribute("pageTitle", keyword + " - Search Result");
//		
//		model.addAttribute("keyword", keyword);
//		model.addAttribute("listResult", listResult);
//		
//		return "product/search_two_result";
//	}
	
	
	/*
	 * @GetMapping("/index/page/{pageNum}") public String listByPage(
	 * 
	 * @PagingAndSortingParam(listName = "listProducts", moduleURL = "/index")
	 * PagingAndSortingHelper helper,
	 * 
	 * @PathVariable(name = "pageNum") int pageNum, Model model, Integer categoryId,
	 * Integer cityId ) { productService.listByPage(pageNum, helper, categoryId,
	 * cityId);
	 * 
	 * Page<Product> pageProducts = productService..search(keyword, pageNum);
	 * List<Product> listProducts = pageProducts.getContent();
	 * 
	 * long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
	 * long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1; if
	 * (endCount > pageProducts.getTotalElements()) { endCount =
	 * pageProducts.getTotalElements(); }
	 * 
	 * 
	 * 
	 * model.addAttribute("currentPage", pageNum); model.addAttribute("totalPages",
	 * pageProducts.getTotalPages()); model.addAttribute("startCount", startCount);
	 * model.addAttribute("endCount", endCount); model.addAttribute("totalItems",
	 * pageProducts.getTotalElements());
	 * 
	 * model.addAttribute("keyword", keyword); model.addAttribute("listProducts",
	 * listProducts);
	 * 
	 * 
	 * List<Category> listCategories = categoryService.listCategoriesUsedInForm();
	 * List<City> listCities = cityService.listCitiesUsedInForm();
	 * 
	 * if (categoryId != null) model.addAttribute("categoryId", categoryId); if
	 * (cityId != null) model.addAttribute("cityId", cityId);
	 * model.addAttribute("listCategories", listCategories);
	 * model.addAttribute("listCities", listCities);
	 * 
	 * return "index"; }
	 */
	
	
	
	/*
	 * @GetMapping("/index/page/{pageNum}") public String listByPage(
	 * 
	 * @PagingAndSortingParam(listName = "listProducts", moduleURL = "/products")
	 * PagingAndSortingHelper helper,
	 * 
	 * @PathVariable(name = "pageNum") int pageNum, Model model, Integer categoryId,
	 * Integer cityId ) {
	 * 
	 * productService.listByPage(pageNum, helper, categoryId, cityId);
	 * 
	 * List<Category> listCategories = categoryService.listCategoriesUsedInForm();
	 * List<City> listCities = cityService.listCitiesUsedInForm();
	 * 
	 * if (categoryId != null) model.addAttribute("categoryId", categoryId); if
	 * (cityId != null) model.addAttribute("citId", categoryId);
	 * 
	 * model.addAttribute("listCategories", listCategories);
	 * model.addAttribute(listCities);
	 * 
	 * return "index";
	 * 
	 * }
	 */

	
	
	@GetMapping("/search_category_city_district")
	public String searchFirstHomePage(String categoryIdMatch, String cityIdMatch, String keyword, Model model) {
		return searchByHomePage(categoryIdMatch, cityIdMatch, keyword, 1, model);
	}
	
	@GetMapping("/search_category_city_district/page/{pageNum}")
	public String searchByHomePage(String categoryIdMatch, String cityIdMatch, String keyword,
			@PathVariable("pageNum") int pageNum,
			Model model) {
		Page<Product> pageProducts = productService.search_category_city_district(categoryIdMatch, cityIdMatch, keyword, pageNum);
		List<Product> listResult = pageProducts.getContent();
		
		long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1;
		if (endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("pageTitle", keyword + " - Search Result");
		
		model.addAttribute("categoryIdMatch", categoryIdMatch);
		model.addAttribute("cityIdMatch", cityIdMatch);
		model.addAttribute("keyword", keyword);
		model.addAttribute("listResult", listResult);
		
		return "product/search_home_page_result";
	}
	
//	@GetMapping("/product/detail/{alias}")
//	public String viewProductDetails( @PathVariable("alias") String alias, Model model,
//			RedirectAttributes ra) {
//		try {
//			Product product = productService.getProduct(alias);			
//			model.addAttribute("product", product);		
//			
//			return "product/product_detail_modal";
//			
//		} catch (ProductNotFoundException e) {
//			ra.addFlashAttribute("message", e.getMessage());
//			
//			return defaultRedirectURL;
//		}
//	  }	

	
	@GetMapping("/product/detail/{id}")
	public String viewProductDetails( @PathVariable("id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Product product = productService.get(id);			
			model.addAttribute("product", product);		
			
			return "product/product_detail_modal";
			
		} catch (ProductNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			
			return defaultRedirectURL;
		}
	  }	
	}
