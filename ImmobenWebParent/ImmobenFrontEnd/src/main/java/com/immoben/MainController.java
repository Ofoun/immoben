package com.immoben;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.immoben.category.CategoryService;
import com.immoben.city.CityService;
import com.immoben.common.entity.Category;
import com.immoben.common.entity.City;
import com.immoben.common.entity.product.Product;
import com.immoben.product.ProductService;

@Controller
public class MainController {

	@Autowired private CategoryService categoryService;	
	@Autowired private ProductService productService;
	@Autowired private CityService cityService;

	
	@GetMapping("")
	public String viewHomePage(Model model, @Param("keyword") String keyword) {
	
	    List<Category> listCategories = categoryService.listNoChildrenCategories();
	    model.addAttribute("listCategories", listCategories);
		 
		
		List<City> listCities = cityService.listAll();
		model.addAttribute("listCities", listCities);
		
		List<Product> listProducts = productService.listAll();
	    model.addAttribute("listProducts", listProducts);
		  

	    model.addAttribute("keyword", keyword);
		 
		return "index";
	}
	
	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		
		return "redirect:/";
	}	
}
