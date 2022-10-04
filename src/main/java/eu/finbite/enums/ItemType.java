package eu.finbite.enums;

public enum ItemType {
  CONVERSATION_MINUTE,
  SMS;

  public String toInvoiceName() {
    switch (this) {
      case CONVERSATION_MINUTE:
        return "conversation minute(s)";
      case SMS:
        return "sms";
      default:
        return "";
    }
  }
}
