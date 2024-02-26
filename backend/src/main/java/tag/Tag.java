package tag;

import org.ispp4.cohabify.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "tags")
public class Tag extends BaseEntity{

  private String tag;

  private TagType tagType;

  public Tag() {

  }

  public Tag(String tag, tag.TagType tagType) {
    this.tag = tag;
    this.tagType = tagType;
  }

  public static Tag fromString(String tag) {
    return new Tag(tag, TagType.USER_TAG);
  }

}
