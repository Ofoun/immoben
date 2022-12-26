package com.immoben;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.immoben.category.CategoryService;
import com.immoben.city.CityService;
import com.immoben.common.entity.Category;
import com.immoben.common.entity.City;
import com.immoben.common.entity.product.Product;
import com.immoben.paging.PagingAndSortingHelper;
import com.immoben.paging.PagingAndSortingParam;
import com.immoben.product.ProductRepository;
import com.immoben.product.ProductService;

@Controller
public class MainController {
	public static final int PRODUCTS_PER_PAGE = 20;
	public static final int SEARCH_RESULTS_PER_PAGE = 20;
	private String defaultRedirectURL = "redirect:/product/page/1?sortField=id&sortDir=desc&categoryId=0&cityId=0&keyword= ";


	
	@Autowired private CategoryService categoryService;	
	@Autowired private ProductService productService;
	@Autowired private CityService cityService;
	
	@Autowired private ProductRepository repo;

	
	
	
	/*
	 * @GetMapping("/") public String viewHomePage(Model model) {
	 * 
	 * List<Category> listCategories = categoryService.listNoChildrenCategories();
	 * model.addAttribute("listCategories", listCategories);
	 * 
	 * 
	 * List<City> listCities = cityService.listAll();
	 * model.addAttribute("listCities", listCities);
	 * 
	 * List<Product> listProducts = productService.listAll();
	 * model.addAttribute("listProducts", listProducts);
	 * 
	 * return "index"; }
	 */
	

	
	public List<Product> listAll() {
		return (List<Product>) repo.findAll();
	}
	
	@GetMapping("/")
	public String listFirstPage(Model model) {
		return defaultRedirectURL;
	}
//	
//	@GetMapping("/product/page/{pageNum}") 
//	  public String viewHomePage(String keyword,
//	  	  @PagingAndSortingParam(listName = "listProducts", moduleURL = "/product")
//	  PagingAndSortingHelper helper,
//	  
//	  @PathVariable(name = "pageNum") int pageNum, Model model, Integer categoryId,
//	  Integer cityId ) {
//	  
//	  List<Category> listCategories = categoryService.listNoChildrenCategories();
//	  
//	  List<City> listCities = cityService.listAll();
//	  
//	  List<Product> listProducts = productService.listAll();
//	  
//	  Page<Product> pageProducts = productService.search(keyword, pageNum);
//	  
//	  List<Product> listResult = pageProducts.getContent();
//	  
////	  long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
////	  long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1; 
////	  if (endCount > pageProducts.getTotalElements()) { 
////		  endCount = pageProducts.getTotalElements(); 
////		  }
//	  
//	  long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
//	  long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1; 
//	  if (endCount > pageProducts.getTotalElements()) { 
//		  endCount = pageProducts.getTotalElements(); 
//		  }
//	  
//	  model.addAttribute("currentPage", pageNum); 
//	  model.addAttribute("totalPages", pageProducts.getTotalPages()); 
//	  model.addAttribute("startCount", startCount);
//	  model.addAttribute("endCount", endCount); 
//	  model.addAttribute("totalItems", pageProducts.getTotalElements()); 
//	  model.addAttribute("pageTitle", keyword + " - Search Result");
//	  
//	  model.addAttribute("listCategories", listCategories);
//	  model.addAttribute("listCities", listCities);
//	  model.addAttribute("listProducts", listProducts);
//	  model.addAttribute("keyword", keyword); 
//	  model.addAttribute("listResult", listResult); 
//	  model.addAttribute("helper", helper); 
//	  if (categoryId != null)
//	  model.addAttribute("categoryId", categoryId); 
//	  if (cityId != null)
//	  model.addAttribute("cityId", cityId);
//	  
//	  return "index";
//	  
//	  }
	  
	  
	  
	  
//		@GetMapping("/")
//		public String listFirstPage(Model model) {
//			return listByPage(1, model, "id", "desc", null, 0, 0);
//		}
		
		@GetMapping("/product/page/{pageNum}")
		public String listByPage(
		  	    @PagingAndSortingParam(listName = "listProducts", moduleURL = "/product") PagingAndSortingHelper helper,
				@PathVariable(name = "pageNum") int pageNum, Model model,
				@Param("sortField") String sortField, 
				@Param("sortDir") String sortDir,
				@Param("keyword") String keyword,
				@Param("categoryId") Integer categoryId,
				@Param("cityId") Integer cityId
				) {
			Page<Product> page = productService.listByPage(pageNum, sortField, sortDir, keyword, categoryId, cityId);
			List<Product> listProducts = page.getContent();
			
//			List<Category> listCategories = categoryService.listCategoriesUsedInForm();
			List<Category> listCategories = categoryService.listNoChildrenCategories();
			
			 List<City> listCities = cityService.listAll();
			
			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
			if (endCount > page.getTotalElements()) {
				endCount = page.getTotalElements();
			}
			
			String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
			
			if (categoryId != null) model.addAttribute("categoryId", categoryId); 
			if (cityId != null) model.addAttribute("cityId", cityId); 
			
			model.addAttribute("helper", helper);
			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", reverseSortDir);
			model.addAttribute("keyword", keyword);		
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("listCities", listCities);	
			model.addAttribute("moduleURL", "/product");	
			
			return "index";		
		}
	  
	 

	
	/*
	 * @GetMapping("/product/page/{pageNum}") public String viewHomePage(
	 * 
	 * @PagingAndSortingParam(listName = "listProducts", moduleURL = "/product")
	 * PagingAndSortingHelper helper,
	 * 
	 * @PathVariable(name = "pageNum") int pageNum, Model model, Integer categoryId,
	 * Integer cityId ) {
	 * 
	 * productService.listByPage(pageNum, helper, categoryId, cityId);
	 * 
	 * List<Category> listCategories = categoryService.listCategoriesUsedInForm();
	 * 
	 * List<City> listCities = cityService.listAll();
	 * 
	 * List<Product> listProducts = productService.listAll();
	 * 
	 * if (categoryId != null) model.addAttribute("categoryId", categoryId); if
	 * (cityId != null) model.addAttribute("cityId", cityId);
	 * 
	 * model.addAttribute("listCategories", listCategories);
	 * model.addAttribute("listCities", listCities);
	 * model.addAttribute("listProducts", listProducts);
	 * model.addAttribute("helper", helper);
	 * 
	 * return "index"; }
	 */
	
	
	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		
		return "redirect:/";
	}	
}
