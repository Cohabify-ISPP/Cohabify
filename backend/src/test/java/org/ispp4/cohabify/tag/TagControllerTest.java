package org.ispp4.cohabify.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class TagControllerTest {

    @Autowired
    private TagController tagController;

    @Autowired
    private TagRepository tagRepository;

    private Tag testTag1;
    private Tag testTag2;
    private ObjectId testObjectId;

    @BeforeEach
    void setUp() {
        testTag1 = new Tag("Test tag 1", TagType.USER_TAG);
        testTag2 = new Tag("Test tag 2", TagType.FLAT_TAG);
        testObjectId = new ObjectId();
    }

    @Test
    public void testCreateTag() {
        ResponseEntity<Tag> response = tagController.createTag(testTag1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        tagRepository.delete(response.getBody());
    }

    @Test
    public void testGetAllTags() {
        
        tagRepository.save(testTag1);
        tagRepository.save(testTag2);

        ResponseEntity<List<Tag>> response = tagController.getAllTags(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testGetAllTags_Empty() {
        
        tagRepository.deleteAll();
    
        ResponseEntity<List<Tag>> response = tagController.getAllTags(null);
    
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        
        
        if (response.getBody() != null) {
            assertTrue(response.getBody().isEmpty());
        }
    }    

    @Test
    public void testGetTagById() {
        
        Tag savedTag = tagRepository.save(testTag1);

        ResponseEntity<Tag> response = tagController.getTagById(savedTag.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        
        tagRepository.delete(savedTag);
    }

    @Test
    public void testGetTagById_NotFound() {
        
        ResponseEntity<Tag> response = tagController.getTagById(testObjectId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }    

    @Test
    public void testFindByTagType() {
        
        tagRepository.save(testTag1);
        tagRepository.save(testTag2);

        ResponseEntity<List<Tag>> response = tagController.findByTagType(TagType.FLAT_TAG);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testFindByTagType_NotFound() {

        tagRepository.deleteAll();
        
        ResponseEntity<List<Tag>> response = tagController.findByTagType(TagType.FLAT_TAG);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        if (response.getBody() != null) {
            assertTrue(response.getBody().isEmpty());
        }
    }

    @Test
    public void testUpdateTag() {
        
        Tag savedTag = tagRepository.save(testTag1);

        
        savedTag.setTag("Updated Tag");

        ResponseEntity<Tag> response = tagController.updateTag(savedTag.getId(), savedTag);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        
        Tag updatedTag = tagRepository.findById(savedTag.getId()).orElse(null);
        assertNotNull(updatedTag);
        assertEquals(savedTag.getTag(), updatedTag.getTag());

        
        tagRepository.delete(updatedTag);
    }

    @Test
    public void testUpdateTag_NotFound() {

        ResponseEntity<Tag> response = tagController.updateTag(testObjectId, testTag1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteTag() {
        
        Tag savedTag = tagRepository.save(testTag1);

        ResponseEntity<HttpStatus> response = tagController.deleteTag(savedTag.getId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        
        assertFalse(tagRepository.existsById(savedTag.getId()));
    }

    @Test
    public void testDeleteTag_NotFound() {
        
        ResponseEntity<HttpStatus> response = tagController.deleteTag(testObjectId);
    
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
