package com.setu.splitwise.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(SnakeCaseStrategy.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseOverview implements Response {

  private static final long serialVersionUID = -13502867534340L;

  private List<Expense> expenses;

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonNaming(SnakeCaseStrategy.class)
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Expense implements Response {

    private static final long serialVersionUID = -1367534340L;

    private LocalDateTime createdAt;

    private String title;

    private String description;

    private String additionalInformation;

  }

}
