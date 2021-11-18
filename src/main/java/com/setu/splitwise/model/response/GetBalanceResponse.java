package com.setu.splitwise.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.setu.splitwise.enums.BalanceType;
import java.math.BigDecimal;
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
public class GetBalanceResponse implements Response {

  private static final long serialVersionUID = -13502867534340L;

  private BigDecimal amount;

  private BalanceType balanceType;

  private List<BalanceDistribution> balanceDistributions;

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonNaming(SnakeCaseStrategy.class)
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class BalanceDistribution implements Response {

    private static final long serialVersionUID = -13502867534L;

    private Long withUserId;

    private BigDecimal amount;

    private BalanceType balanceType;

  }

}
