package org.ispp4.cohabify.tag;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class TagService {


    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Optional<Tag> findById(ObjectId id) {
        return tagRepository.findById(id);
    }

    public Tag save(Tag tag) {
        return (Tag) tagRepository.save(tag);
    }

    public void deleteById(ObjectId id) {
        tagRepository.deleteById(id);
    }

}
