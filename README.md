This ecommerce spring app has three microservices. The user service, product service and cart service. All of these microservices has the functonalities of CRUD. In product service, I also added the functionalities that the user can delete all the products under the seller role, and an update endpoint that can update the quantities of a specific product after a customer added it to cart. Lastly, the cart service has also added functionalities that can delete all the items in cart.
Different functionalities may have different scaling requirements. By isolating them into separate services, it becomes easier to scale each service independently as needed, without affecting other services. 
