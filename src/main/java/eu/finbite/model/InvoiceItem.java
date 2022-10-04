package eu.finbite.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class InvoiceItem {

  private final String description;

  private final Integer quantity;

  private final BigDecimal pricePerUnit;

  public BigDecimal getTotalPrice() {
    return quantity == null || pricePerUnit == null
        ? BigDecimal.ZERO
        : pricePerUnit.multiply(BigDecimal.valueOf(quantity));
  }
}
