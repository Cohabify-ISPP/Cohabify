package org.ispp4.cohabify.tag;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    private Tag testTag1;
    private Tag testTagNull;
    private ObjectId testObjectId;

    @BeforeEach
    void setUp() {
        testTag1 = new Tag("Test tag 1", TagType.USER_TAG);
        testTagNull = null;
        testObjectId = new ObjectId();
    }

    @Test
    void shouldFindAllTags() {
        List<Tag> tags = tagService.findAll();
        assertNotNull(tags);
    }

    @Test
    void shouldFindTagById() {
        Tag tag = tagService.save(testTag1);
        ObjectId id = tag.getId();

        Optional<Tag> tagOptional = tagService.findById(id);
        assertTrue(tagOptional.isPresent());
    }

    @Test
    void shouldNotFindTagByIdWhenIdNotFound() {
    
        Optional<Tag> tagOptional = tagService.findById(testObjectId);
    
        assertTrue(tagOptional.isEmpty());
    }

    @Test
    void shouldSaveTag() {

        Tag savedTag = tagService.save(testTag1);

        assertNotNull(savedTag);
        assertNotNull(savedTag.getId());

        tagService.deleteById(savedTag.getId());
    }

    @Test
    void shouldNotSaveTagWhenItIsNull() {
    
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.save(testTagNull);
        });
    }

    @Test
    void shouldDeleteTagById() {
        Tag tag = tagService.save(testTag1);

        ObjectId id = tag.getId();

        tagService.deleteById(id);

        Optional<Tag> deletedTag = tagService.findById(id);

        assertTrue(deletedTag.isEmpty());
    }

    @Test
    void shouldNotDeleteTagByIdIfIdDoesNotExist() {
        
        Tag savedTag = tagRepository.save(testTag1);

        tagService.deleteById(testObjectId);

        Optional<Tag> deletedTag = tagService.findById(savedTag.getId());

        assertFalse(deletedTag.isEmpty());
    }
}