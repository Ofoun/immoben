package com.immoben.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cities")
public class City extends IdBasedEntity{
	
	@Column(nullable = false, length = 45, unique = true)
	private String name;
	
	@ManyToMany
	@JoinTable(
			name = "cities_categories",
			joinColumns = @JoinColumn(name = "city_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id")
			)
	private Set<Category> categories = new HashSet<>();

	public City() {
		
	}
	
	public City(String name) {
		this.name = name;
	}

	public City(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return this.name;
	}
	


	public static City copyIdAndName(City city) {
		City copyCity = new City();
		copyCity.setId(city.getId());
		copyCity.setName(city.getName());
		
		return copyCity;
	}

	public static City copyIdAndName(Integer id, String name) {
		City copyCity = new City();
		copyCity.setId(id);
		copyCity.setName(name);
		
		return copyCity;
	}
	
	public static City copyFull(City city) {
		City copyCity = new City();
		copyCity.setId(city.getId());
		copyCity.setName(city.getName());
		
		return copyCity;		
	}
	
	public static City copyFull(City city, String name) {
		City copyCity = City.copyFull(city);
		copyCity.setName(name);
		
		return copyCity;
	}

	

	/*
	 * @Override public String toString() { return "City [id=" + id + ", name=" +
	 * name + ", categories=" + categories + "]"; }
	 */


}
