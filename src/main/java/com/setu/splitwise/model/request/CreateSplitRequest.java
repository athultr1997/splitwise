package com.setu.splitwise.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.setu.splitwise.enums.SplitType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(SnakeCaseStrategy.class)
public class CreateSplitRequest implements Request {

  private static final long serialVersionUID = -1350286753434022565L;

  private Long createdBy;

  private BigDecimal amount;

  private Currency currency;

  private String title;

  private String note;

  private SplitType splitType;

  private List<SplitDistribution> splitDistributions;

  public List<Long> getAllUserIds() {
    Set<Long> userIds = new HashSet<>(Optional.ofNullable(this.splitDistributions)
        .filter(CollectionUtils::isNotEmpty)
        .map(splitDistributionsCopy -> splitDistributionsCopy
            .stream()
            .map(SplitDistribution::getUserId)
            .collect(Collectors.toSet())
        )
        .orElseGet(Collections::emptySet));
    userIds.add(this.createdBy);
    return new ArrayList<>(userIds);
  }

  public int getSplitSize() {
    return Optional.ofNullable(this.splitDistributions).map(List::size).orElse(0);
  }

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonNaming(SnakeCaseStrategy.class)
  public static class SplitDistribution implements Request {

    private static final long serialVersionUID = -1350286753434022565L;

    private Long userId;

    private BigDecimal splitValue;

    private BigDecimal amountPaid;

  }

}
