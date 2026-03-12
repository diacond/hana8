package com.hana8.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberImageDTO {

  private Long id;
  private String orgname;
  private String savename;
  private String savedir;

  public String getImageUrl() {
    return savedir + "/" + savename;
  }

  public String getThumbnailUrl() {
    return savedir + "/thumb_" + savename;
  }
}
