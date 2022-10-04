package eu.finbite.converter;

import eu.finbite.enums.ItemType;
import eu.finbite.enums.Package;
import eu.finbite.model.InvoiceItem;
import eu.finbite.model.PackageItem;
import java.math.BigDecimal;

public class InvoiceItemConverter {

  public static InvoiceItem toInvoiceItem(Package userPackage) {
    return InvoiceItem.builder()
        .description(String.format("Mobile package %s", userPackage.getName()))
        .pricePerUnit(userPackage.getPrice())
        .quantity(1)
        .build();
  }

  public static InvoiceItem toInvoiceItem(
      PackageItem packageItem, Integer usageQuantity, BigDecimal pricePerUnit) {
    ItemType itemType = packageItem.getItemType();
    Integer maxQuantity = packageItem.getQuantity();
    Integer overuseQuantity = usageQuantity > maxQuantity ? usageQuantity - maxQuantity : 0;

    return InvoiceItem.builder()
        .pricePerUnit(pricePerUnit)
        .quantity(overuseQuantity)
        .description(String.format("Extra usage of %s", itemType.toInvoiceName()))
        .build();
  }
}
