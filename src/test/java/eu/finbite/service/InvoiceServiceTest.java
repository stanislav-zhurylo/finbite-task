package eu.finbite.service;

import static org.assertj.core.api.Assertions.assertThat;

import eu.finbite.enums.ItemType;
import eu.finbite.enums.Package;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InvoiceServiceTest {

  private static Object[][] testData() {
    return new Object[][] {
      {usage(10, 50), prices("0.20", "0.30"), Package.PACKAGE_S, new BigDecimal("6.00")},
      {usage(50, 100), prices("0.20", "0.30"), Package.PACKAGE_M, new BigDecimal("12.00")},
      {usage(500, 500), prices("0.20", "0.30"), Package.PACKAGE_L, new BigDecimal("24.00")},
      {usage(240, 360), prices("0.20", "0.30"), Package.PACKAGE_S, new BigDecimal("172.80")}
    };
  }

  private static final InvoiceService service = new InvoiceService();

  @ParameterizedTest
  @MethodSource("testData")
  void calculateInvoiceTotal(
      Map<ItemType, Integer> usageMap,
      Map<ItemType, BigDecimal> priceMap,
      Package userPackage,
      BigDecimal expected) {

    BigDecimal actual = service.calculateInvoiceTotal(usageMap, priceMap, userPackage, false);

    assertThat(actual).isEqualTo(expected);
  }

  private static Map<ItemType, Integer> usage(int minutes, int sms) {
    return Map.of(ItemType.CONVERSATION_MINUTE, minutes, ItemType.SMS, sms);
  }

  private static Map<ItemType, BigDecimal> prices(String conversationMinutePrice, String smsPrice) {
    return Map.of(
        ItemType.CONVERSATION_MINUTE,
        new BigDecimal(conversationMinutePrice),
        ItemType.SMS,
        new BigDecimal(smsPrice));
  }
}
