package tag;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TagRepository extends MongoRepository<Tag, ObjectId> {

    List<Tag> findByTag(String tag);

    List<Tag> findByTagType(TagType tagtype);

    List<Tag> findAll();

    void deleteById(ObjectId id);

}
