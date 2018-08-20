Feature: Test online Shop services

Scenario: user wants to test services of online shop

When user give call to '/sayHello' service
Then user should receive service status as 'OK' and response message 'Hello World from RESTFullWebServices'

When user submit valid product detail
Then Product details successfully should add and same product details should return by services