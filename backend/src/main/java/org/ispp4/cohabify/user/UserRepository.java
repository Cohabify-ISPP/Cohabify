package org.ispp4.cohabify.user;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    List<User> findByUsername(String username);

    List<User> findByIsOwner(Boolean isOwner); 

    List<User> findAll();

    void deleteById(ObjectId id);
}
