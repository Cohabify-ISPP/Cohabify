package org.ispp4.cohabify.chat;

import java.util.List;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<Chat, ObjectId>{

    List<Chat> findByUsers(User user);

    List<Chat> findByOpenedBy(User user);

    Chat findByUsers(List<User> users);

}
