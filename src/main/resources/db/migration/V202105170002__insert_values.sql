/*
    Script to insert values into BRAND and PRICE tables
*/


INSERT INTO BRAND (BRAND_ID, BRAND_NAME) VALUES (1, 'ZARA');

INSERT INTO PRICES (ID, BRAND_ID, PRODUCT_ID, PRIORITY, PRICE, PRICE_LIST, START_DATE, END_DATE, CURR)
VALUES (1, 1, 35455, 0, 35.50, 1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 'EUR'),
       (2, 1, 35455, 1, 25.45, 2, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 'EUR'),
       (3, 1, 35455, 1, 30.50, 3, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 'EUR'),
       (4, 1, 35455, 1, 38.95, 4, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 'EUR');

