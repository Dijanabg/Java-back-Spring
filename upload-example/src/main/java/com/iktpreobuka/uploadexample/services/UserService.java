package com.iktpreobuka.uploadexample.services;

import java.util.List;

import com.iktpreobuka.uploadexample.entity.UserEntity;

public interface UserService {
	UserEntity updateUser(Integer userId, UserEntity userDetails) throws Exception;
	String generateCsv(List<String> fields);
}
