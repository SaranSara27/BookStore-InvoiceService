# BookStore-InvoiceService

1.Place Order
Request URL:
http://localhost:9011/invoice/placeOrder
Request Body:
{
        "id": 40,
        "orderId": "13482",
        "paymentType": "COD",
        "deliveryAddress": "silver Apt, 3rd block 500",
        "userId": 123,
        "productId": "13,42,132",
       "productName": "Harry Potter,Perfect Us,Bed Time Stories",
        "qty": "1,1,1",
        "price": "20,132,40",
        "orderDate": "Wed Nov 5 08:22:25 IST 2021",
        "orderStatus": "Order Placed",
        "total": 192
}
Output:
 ![image](https://user-images.githubusercontent.com/65871256/140710237-1cb27f7c-2d18-4b5d-bd3b-492d588765ba.png)
![image](https://user-images.githubusercontent.com/65871256/140710259-0fd90a22-9050-4716-9118-b2dc182a1982.png)


PDF file:          
2.Cancel Order
Request URL:
http://localhost:9011/invoice/cancelOrder 
Request Body:
{
        "id": 40,
        "orderId": "13482",
        "paymentType": "COD",
        "deliveryAddress": "silver Apt, 3rd block 500",
        "userId": 123,
        "productId": "13,42,132",
       "productName": "Harry Potter,Perfect Us,Bed Time Stories",
        "qty": "1,1,1",
        "price": "20,132,40",
        "orderDate": "Wed Nov 5 08:22:25 IST 2021",
        "orderStatus": "Order Cancelled",
        "total": 152
}
Output:
![image](https://user-images.githubusercontent.com/65871256/140710200-4c11ac4d-c0a3-4e93-9af7-acfd16a66588.png)
![image](https://user-images.githubusercontent.com/65871256/140710219-cfb67c79-78bc-4e7d-801e-3d6b2796f1f2.png)


PDF file:           
Swagger:
http://localhost:9011/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/  

Actuator:
localhost:9011/actuator
 
