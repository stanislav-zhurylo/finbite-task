package eu.finbite.enums;

import eu.finbite.model.PackageItem;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Package {
  PACKAGE_S(
      List.of(
          PackageItem.builder().itemType(ItemType.CONVERSATION_MINUTE).quantity(10).build(),
          PackageItem.builder().itemType(ItemType.SMS).quantity(50).build()),
      new BigDecimal("5.00"),
      "S"),
  PACKAGE_M(
      List.of(
          PackageItem.builder().itemType(ItemType.CONVERSATION_MINUTE).quantity(50).build(),
          PackageItem.builder().itemType(ItemType.SMS).quantity(100).build()),
      new BigDecimal("10.00"),
      "M"),
  PACKAGE_L(
      List.of(
          PackageItem.builder().itemType(ItemType.CONVERSATION_MINUTE).quantity(500).build(),
          PackageItem.builder().itemType(ItemType.SMS).quantity(500).build()),
      new BigDecimal("20.00"),
      "L");

  private final List<PackageItem> packageItems;

  private final BigDecimal price;

  private final String name;

  Package(List<PackageItem> packageItems, BigDecimal price, String name) {
    this.packageItems = packageItems;
    this.price = price;
    this.name = name;
  }
}
