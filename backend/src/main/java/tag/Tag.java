package tag;

import org.ispp4.cohabify.model.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "tags")
public class Tag extends BaseEntity{

  private String tag;

  private TagType TagType;
 
}
