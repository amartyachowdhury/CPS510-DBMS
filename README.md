# CPS510 Database Management System - Web Application

A Spring Boot web application for managing a Clothing Retail Store database system connected to Oracle 11g.

## Features

- **Customer Management**: Create, read, update, and delete customer records
- **Product Management**: Manage product catalog with categories, pricing, and inventory
- **Order Management**: Process orders with order items
- **Payment Tracking**: Record and track payments for orders
- **Dashboard**: Overview of system statistics

## Technology Stack

- **Backend**: Java with Spring Boot 2.7.14
- **Database**: Oracle 11g (Ryerson: oracle.scs.ryerson.ca:1521:orcl)
- **Frontend**: Thymeleaf templates with Bootstrap 5.3.0
- **Build Tool**: Maven
- **Connection**: JDBC with Spring JDBC

## Database Schema

The database schema is defined in the `if_needed/` directory and has already been executed on Oracle 11g:

- **Tables**: Category_, Customer, Employee, Product, Order_, Payment, OrderItem
- **Views**: V_ORDERS_SUMMARY, V_ORDER_LINE_ITEMS, V_PAYMENTS
- **Auto-increment**: Sequences and triggers for ID generation

See `if_needed/a5.sql` for the complete schema definition.

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Oracle 11g database access (Ryerson Oracle server)
- Oracle JDBC driver (included in pom.xml)

## Configuration

1. Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.scs.ryerson.ca:1521:orcl
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Or set environment variables:
```bash
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

## Building and Running

### Build the project:
```bash
mvn clean package
```

### Run the application:
```bash
mvn spring-boot:run
```

Or run the JAR:
```bash
java -jar target/cps510-dbms-1.0.0.jar
```

The application will start on `http://localhost:8080`

## Project Structure

```
cps510-dbms/
├── if_needed/              # SQL schema files (already executed)
├── src/
│   └── main/
│       ├── java/
│       │   └── com/cps510/
│       │       ├── Cps510Application.java
│       │       ├── config/          # Database configuration
│       │       ├── controller/      # MVC controllers
│       │       ├── dao/            # Data Access Objects
│       │       └── model/          # Entity models
│       └── resources/
│           ├── application.properties
│           ├── static/css/         # CSS styles
│           └── templates/          # Thymeleaf templates
├── pom.xml
└── README.md
```

## API Endpoints

### Customers
- `GET /customers` - List all customers
- `GET /customers/{id}` - View customer details
- `GET /customers/new` - Show create form
- `POST /customers/new` - Create customer
- `GET /customers/{id}/edit` - Show edit form
- `POST /customers/{id}/edit` - Update customer
- `POST /customers/{id}/delete` - Delete customer

### Products
- `GET /products` - List all products
- `GET /products/{id}` - View product details
- `GET /products/new` - Show create form
- `POST /products/new` - Create product
- `GET /products/{id}/edit` - Show edit form
- `POST /products/{id}/edit` - Update product
- `POST /products/{id}/delete` - Delete product

### Orders
- `GET /orders` - List all orders
- `GET /orders/{id}` - View order details
- `GET /orders/new` - Show create form
- `POST /orders/new` - Create order
- `GET /orders/{id}/items` - Manage order items
- `POST /orders/{id}/items/add` - Add item to order
- `POST /orders/{id}/items/delete` - Remove item from order
- `POST /orders/{id}/delete` - Delete order

### Payments
- `GET /payments` - List all payments
- `GET /payments/{id}` - View payment details
- `GET /payments/new` - Show create form
- `POST /payments/new` - Create payment
- `GET /payments/{id}/edit` - Show edit form
- `POST /payments/{id}/edit` - Update payment
- `POST /payments/{id}/delete` - Delete payment

## Database Connection

The application uses Spring JDBC with connection pooling. The JDBC template is configured in `DatabaseConfig.java` and uses the connection settings from `application.properties`.

## Notes

- The database schema must already exist before running the application
- Oracle sequences are used for auto-generating IDs via triggers
- The application uses existing views for complex queries (V_ORDERS_SUMMARY, V_ORDER_LINE_ITEMS, V_PAYMENTS)
- Order totals are automatically recalculated when items are added/removed

## License

MIT License

