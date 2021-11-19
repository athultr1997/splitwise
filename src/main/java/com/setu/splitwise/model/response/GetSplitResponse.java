package com.setu.splitwise.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
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
public class GetSplitResponse implements Response {

  private static final long serialVersionUID = -1350286740L;

  private Long id;

  private Long createdBy;

  private String title;

  private String note;

  private BigDecimal amount;

  private Currency currency;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private List<SplitDistribution> splitDistributions;

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonNaming(SnakeCaseStrategy.class)
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SplitDistribution implements Response {

    private static final long serialVersionUID = -13506740L;

    private Long userId;

    private BigDecimal paid;

    private BigDecimal owed;

  }

}
