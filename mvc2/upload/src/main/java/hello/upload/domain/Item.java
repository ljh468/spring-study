package hello.upload.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Item {

  private Long id;
  private String itemName;
  private UploadFile attachFile;
  private List<UploadFile> imageFiles;;

}
