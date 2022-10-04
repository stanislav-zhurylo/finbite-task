## FinBite test task

### Description
This is as test task, implemented according to the following requirements:
```
Create a method for calculating invoice total, which returns invoice total and prints out invoice rows.
calculateInvoiceTotal(usage, priceList, package)

Package S
10 minutes
50 sms
5 eur

Package M
50 minutes
100 sms
10 eur

Package L
500 minutes
500 sms
20 eur

Price list
1 minute - 0.2 eur
1 sms - 0.3 eur
```
### Used tools
- Oracle JDK 11.0.13
- Gradle 7.1

### Entry point

Application entry point: InvoiceService -> calculateInvoiceTotal

### Tests

In order to start unit tests run the command

```
./gradlew test
```
