package eu.finbite.model;

import eu.finbite.enums.ItemType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PackageItem {

  private final ItemType itemType;

  private final Integer quantity;

  PackageItem(ItemType itemType, Integer quantity) {
    this.itemType = itemType;
    this.quantity = quantity;
  }
}
