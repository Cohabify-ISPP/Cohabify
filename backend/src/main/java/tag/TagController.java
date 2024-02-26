package tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TagController {

    @Autowired
    TagRepository tagRepository;

    @PostMapping("/tags")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        try {
            Tag _tag = tagRepository
                    .save(new Tag(tag.getTag(), tag.getTagType()));
            return new ResponseEntity<>(_tag, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/alltags")
    public ResponseEntity<List<Tag>> getAllTags(@RequestParam(required = false) String tag) {
        try {
            List<Tag> tags = new ArrayList<Tag>();

            if (tag == null)
                tagRepository.findAll().forEach(tags::add);
            else
                tagRepository.findByTag(tag).forEach(tags::add);

            if (tags.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tags, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") ObjectId id) {
        Optional<Tag> tagData = tagRepository.findById(id);

        if (tagData.isPresent()) {
            return new ResponseEntity<>(tagData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tags/{tagtype}")
    public ResponseEntity<List<Tag>> findByTagType(@PathVariable("tagtype")TagType tagtype) {
        try {
            List<Tag> tags = tagRepository.findByTagType(tagtype);

            if (tags.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tags, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tags/update/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable("id") ObjectId id, @RequestBody Tag tag) {
        Optional<Tag> tagData = tagRepository.findById(id);

        if (tagData.isPresent()) {
            Tag _tag = tagData.get();
            _tag.setTag(tag.getTag());
            _tag.setTagType(tag.getTagType());
            return new ResponseEntity<>(tagRepository.save(_tag), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tag/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") ObjectId id) {
        try {
            tagRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<HttpStatus> deleteAllTags() {
        try {
            tagRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
