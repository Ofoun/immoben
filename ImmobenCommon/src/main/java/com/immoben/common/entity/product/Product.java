package com.immoben.common.entity.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.immoben.common.Constants;
import com.immoben.common.entity.Category;
import com.immoben.common.entity.City;
import com.immoben.common.entity.Customer;
import com.immoben.common.entity.IdBasedEntity;

@Entity
@Table(name = "products")
public class Product extends IdBasedEntity {
	
	@Column(name = "main_image", nullable = false)
	private String mainImage;
	
	@Column(length = 255, nullable = false)
	private String name;
	
	@Column(length = 255)
	private String alias;
	
	@Column(nullable = false, updatable = true, length = 127, unique = false)
	private String district;

	@ManyToOne
	@JoinColumn(name = "city_id")	
	private City city;
		
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(nullable = true, updatable = true)
	private Long lounge;

	@Column(nullable = true, updatable = true)
	private Long room;

	@Column(nullable = true, updatable = true)
	private Long kitchen;

	@Column(nullable = true, updatable = true)
	private Long shower;

	@Column(nullable = true, updatable = true)
	private Long toilet;

	@Column(nullable = true, updatable = true)
	private float area;
	
	@Column(nullable = true, updatable = true)
	private float cost;

	@Column(nullable = true, updatable = true)
	private float price;
	
	private float averageRating;	
	private float length;
	private float width;
	
	@Column(length = 512, name = "short_description")
	private String shortDescription;
	
	@Column(length = 4096, name = "full_description")
	private String fullDescription;
	
	@Column(name = "created_time")
	private Date createdTime;
	
	@Column(name = "updated_time")
	private Date updatedTime;
	
	private boolean enabled;
	
	@Column(name = "in_stock")
	private boolean inStock;
	
	@Column(name = "discount_percent")
	private float discountPercent;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	

	@Column(name = "owner_phone_nummer", length = 255, nullable = false)
	private String phoneNumber;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ProductImage> images = new HashSet<>();
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductDetail> details = new ArrayList<>();
	
	
	public Product(Integer id) {
		this.id = id;
	}

	public Product() {
	}
	
	public Product(String name) {
		this.name = name;
	}
				
	public Product(String name, String alias, String mainImage, String district, City city, Category category,
			Long lounge, Long room, Long kitchen, Long shower, Long toilet, float area, float cost, float price,
			String shortDescription, String fullDescription, Date createdTime, Date updatedTime, boolean enabled,
			boolean inStock, float discountPercent, float length, float width, Customer customer, String phoneNumber,
			Set<ProductImage> images, List<ProductDetail> details, float averageRating) {
		super();
		this.name = name;
		this.alias = alias;
		this.mainImage = mainImage;
		this.district = district;
		this.city = city;
		this.category = category;
		this.lounge = lounge;
		this.room = room;
		this.kitchen = kitchen;
		this.shower = shower;
		this.toilet = toilet;
		this.area = area;
		this.cost = cost;
		this.price = price;
		this.shortDescription = shortDescription;
		this.fullDescription = fullDescription;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.enabled = enabled;
		this.inStock = inStock;
		this.discountPercent = discountPercent;
		this.length = length;
		this.width = width;
		this.customer = customer;
		this.phoneNumber = phoneNumber;
		this.images = images;
		this.details = details;
		this.averageRating = averageRating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Long getLounge() {
		return lounge;
	}

	public void setLounge(Long lounge) {
		this.lounge = lounge;
	}

	public Long getRoom() {
		return room;
	}

	public void setRoom(Long room) {
		this.room = room;
	}

	public Long getKitchen() {
		return kitchen;
	}

	public void setKitchen(Long kitchen) {
		this.kitchen = kitchen;
	}

	public Long getShower() {
		return shower;
	}

	public void setShower(Long shower) {
		this.shower = shower;
	}

	public Long getToilet() {
		return toilet;
	}

	public void setToilet(Long toilet) {
		this.toilet = toilet;
	}

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public float getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}	

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}
	
	@Transient
	public String getURI() {
		return "/p/" + this.alias + "/";
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}
	
	public void addExtraImage(String imageName) {
		this.images.add(new ProductImage(imageName, this));
	}
	
	@Transient
	public String getMainImagePath() {
		if (id == null || mainImage == null) return "/images/image-thumbnail.png";
		
		return Constants.S3_BASE_URI +"/product-images/" + this.id + "/" + this.mainImage;
	}

	public List<ProductDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ProductDetail> details) {
		this.details = details;
	}
	
	public void addDetail(String name, String value) {
		this.details.add(new ProductDetail(name, value, this));
	}

	public void addDetail(Integer id, String name, String value) {
		this.details.add(new ProductDetail(id, name, value, this));
	}
	
	public boolean containsImageName(String imageName) {
		Iterator<ProductImage> iterator = images.iterator();
		
		while (iterator.hasNext()) {
			ProductImage image = iterator.next();
			if (image.getName().equals(imageName)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Transient
	public String getShortName() {
		if (name.length() > 70) {
			return name.substring(0, 70).concat("...");
		}
		return name;
	}
	
	@Transient
	public float getDiscountPrice() {
		if (discountPercent > 0) {
			return price * ((100 - discountPercent) / 100);
		}
		return this.price;
	}
}
