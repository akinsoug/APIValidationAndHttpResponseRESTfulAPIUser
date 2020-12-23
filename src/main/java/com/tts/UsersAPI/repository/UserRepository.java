package com.tts.UsersAPI.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.UsersAPI.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByFirstName(String firstName);
	List<User> findAllById(Iterable<Long> ids);
	List<User> findByState(String state);

//	User findById2(Long id);
}