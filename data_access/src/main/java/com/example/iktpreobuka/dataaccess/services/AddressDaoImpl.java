package com.example.iktpreobuka.dataaccess.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.iktpreobuka.dataaccess.entities.AddressEntity;
import com.example.iktpreobuka.dataaccess.repositories.AddressRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class AddressDaoImpl implements AddressDao{
	
	@Autowired
	private AddressRepository addressRepository;
	
	@PersistenceContext
	private EntityManager em;
	@SuppressWarnings("unchecked")
	@Override
	public List<AddressEntity> findAddressesByUserName(String name) {
		String sql = "select a " +
		"from AddressEntity a " +
		"left join fetch a.users u " +
		"where u.name = :name ";
		Query query = em.createQuery(sql);
		query.setParameter("name", name);
		List<AddressEntity> result = new ArrayList<>();
		result = query.getResultList();
		return result;
	}
	@Override
	public AddressEntity addNewAddress(String street, String city, String country) {
        AddressEntity address = new AddressEntity();
        address.setStreet(street);
        address.setCity(city);
        address.setCountry(country);
        return addressRepository.save(address);
    }
	@Override
	public Iterable<AddressEntity> findAll() {
		return addressRepository.findAll();
	}
	@Override
	public Optional<AddressEntity> findById(Integer id) {
		return addressRepository.findById(id);
	}
	@Override
	public AddressEntity updateAddress(Integer id, AddressEntity addressDetails) {
        return addressRepository.findById(id).map(address -> {
            address.setStreet(addressDetails.getStreet());
            address.setCity(addressDetails.getCity());
            address.setCountry(addressDetails.getCountry());
            return addressRepository.save(address);
        }).orElseThrow(() -> new RuntimeException("Address not found with id " + id));
    }
	@Override
	public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }
	@Override
	public List<AddressEntity> findByCity(String city) {
		 return addressRepository.findByCity(city);
	}
	@Override
	public List<AddressEntity> findByCountrySorted(String country) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
