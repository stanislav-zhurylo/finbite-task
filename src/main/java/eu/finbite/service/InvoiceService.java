package eu.finbite.service;

import static eu.finbite.converter.InvoiceItemConverter.toInvoiceItem;
import static java.util.stream.Collectors.toList;

import eu.finbite.converter.InvoiceItemConverter;
import eu.finbite.enums.ItemType;
import eu.finbite.enums.Package;
import eu.finbite.model.InvoiceItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class InvoiceService {
  private static final BigDecimal VAT_FACTOR = new BigDecimal("0.20");
  private static final Set<ItemType> REQUIRED_USAGE_ITEMS =
      Set.of(ItemType.CONVERSATION_MINUTE, ItemType.SMS);

  public BigDecimal calculateInvoiceTotal(
      Map<ItemType, Integer> usageMap,
      Map<ItemType, BigDecimal> priceMap,
      Package userPackage,
      boolean isPrintRequired) {

    validateItemTypeMap(usageMap);
    validateItemTypeMap(priceMap);

    List<InvoiceItem> invoiceItems = new ArrayList<>();
    invoiceItems.add(toInvoiceItem(userPackage));
    invoiceItems.addAll(
        userPackage.getPackageItems().stream()
            .map(
                packageItem ->
                    InvoiceItemConverter.toInvoiceItem(
                        packageItem,
                        usageMap.get(packageItem.getItemType()),
                        priceMap.get(packageItem.getItemType())))
            .filter(this::hasExceededUsageLimit)
            .collect(toList()));

    BigDecimal invoiceTotal =
        invoiceItems.stream()
            .map(InvoiceItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal vatPayment = invoiceTotal.multiply(VAT_FACTOR).setScale(2, RoundingMode.HALF_UP);
    BigDecimal paymentTotal = invoiceTotal.add(vatPayment);

    if (isPrintRequired) {
      printInvoiceTableHeader();
      invoiceItems.forEach(this::printInvoiceTableItem);
      printInvoiceTableFooter(invoiceTotal, vatPayment, paymentTotal);
    }

    return paymentTotal;
  }

  private void printInvoiceTableHeader() {
    printDivider();
    System.out.printf(
        "\n| %40s | %10s | %12s | %16s |", "Item name", "Qty.", "PPU (EUR)", "Total (EUR)");
    printDivider();
  }

  private void printDivider() {
    System.out.print("\n" + "-".repeat(91));
  }

  private void printInvoiceTableItem(InvoiceItem item) {
    System.out.printf(
        "\n| %40s | %10s | %12s | %16s |",
        item.getDescription(), item.getQuantity(), item.getPricePerUnit(), item.getTotalPrice());
    printDivider();
  }

  private void printInvoiceTableFooter(
      BigDecimal invoiceTotal, BigDecimal vatPayment, BigDecimal paymentTotal) {
    System.out.printf("\n| %68s : %16s |", "Invoice total", String.format("%s EUR", invoiceTotal));
    System.out.printf("\n| %68s : %16s |", "VAT (20%)", String.format("%s EUR", vatPayment));
    System.out.printf("\n| %68s : %16s |", "Amount to pay", String.format("%s EUR", paymentTotal));
    printDivider();
  }

  private void validateItemTypeMap(Map<ItemType, ?> itemTypeMap) {
    if (itemTypeMap == null
        || !itemTypeMap.keySet().containsAll(REQUIRED_USAGE_ITEMS)
        || !itemTypeMap.values().stream().allMatch(Objects::nonNull)) {
      throw new IllegalStateException("Item type map is not correctly defined");
    }
  }

  private boolean hasExceededUsageLimit(InvoiceItem invoiceItem) {
    return invoiceItem.getQuantity() > 0;
  }
}
