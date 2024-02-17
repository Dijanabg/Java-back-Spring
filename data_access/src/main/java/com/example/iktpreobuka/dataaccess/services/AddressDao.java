package com.example.iktpreobuka.dataaccess.services;

import java.util.List;
import java.util.Optional;

import com.example.iktpreobuka.dataaccess.entities.AddressEntity;

public interface AddressDao {
	
	public List<AddressEntity> findAddressesByUserName(String name);

	AddressEntity addNewAddress(String street, String city, String country);

	public Iterable<AddressEntity> findAll();

	public Optional<AddressEntity> findById(Integer id);
	
	AddressEntity updateAddress(Integer id, AddressEntity addressDetails);

	public void deleteAddress(Integer id);

	public List<AddressEntity> findByCity(String city);

	public List<AddressEntity> findByCountrySorted(String country);

	

	

	
}
