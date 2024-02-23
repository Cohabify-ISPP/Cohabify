package tag;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;



@Document(collection = "tags")
public class Tag {

  @Id
  @NotEmpty
  private String id;

  private String tag;

  private TagType TagType;
  

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTag() {
    return this.tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public TagType getTagType() {
    return this.TagType;
  }

  public void setTagType(TagType TagType) {
    this.TagType = TagType;
  }


}
