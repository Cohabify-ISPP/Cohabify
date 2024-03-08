package org.ispp4.cohabify.user;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    List<User> findByIsOwner(Boolean isOwner); 

    List<User> findAll();

    void deleteById(ObjectId id);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPhone(String username, String phone);
}
