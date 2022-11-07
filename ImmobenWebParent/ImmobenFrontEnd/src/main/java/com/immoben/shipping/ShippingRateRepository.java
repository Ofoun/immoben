package com.immoben.shipping;

import org.springframework.data.repository.CrudRepository;

import com.immoben.common.entity.Country;
import com.immoben.common.entity.ShippingRate;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {
	
	public ShippingRate findByCountryAndState(Country country, String state);
}
