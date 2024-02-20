package com.iktpreobuka.project.services;

import java.util.List;

import com.iktpreobuka.project.entities.EUserRole;
import com.iktpreobuka.project.entities.UserEntity;


public interface UserService {
	UserEntity addNewUser(String name, String lastName, String email, String username, String password, EUserRole userRole);
	UserEntity getUserById(Integer id);
	List<UserEntity> getAllUsers();
    UserEntity updateUser(Integer id, UserEntity userUpdates);
    boolean deleteUser(Integer id);
    UserEntity changeUserRole(Integer id, EUserRole newRole);
    UserEntity changeUserPassword(Integer id, String oldPassword, String newPassword);
    UserEntity getUserByUsername(String username);
}
