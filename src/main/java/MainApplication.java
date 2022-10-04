import eu.finbite.enums.ItemType;
import eu.finbite.enums.Package;
import java.math.BigDecimal;
import java.util.Map;
import eu.finbite.service.InvoiceService;

public class MainApplication {

  public static void main(String... args) {
    // This is just a demo example
    Map<ItemType, Integer> usageMap = Map.of(ItemType.CONVERSATION_MINUTE, 240, ItemType.SMS, 360);
    Map<ItemType, BigDecimal> priceMap =
        Map.of(
            ItemType.CONVERSATION_MINUTE,
            new BigDecimal("0.20"),
            ItemType.SMS,
            new BigDecimal("0.30"));

    InvoiceService service = new InvoiceService();
    service.calculateInvoiceTotal(usageMap, priceMap, Package.PACKAGE_S, true);
  }
}
