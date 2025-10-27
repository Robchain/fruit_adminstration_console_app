# Fruit Vendor Management System

A comprehensive Java application for managing a fruit drink vendor's inventory and sales operations.

## Features

### Core Requirements
- [x] Hard-coded starting inventory of ingredients
- [x] List current inventory of ingredients
- [x] Sell drinks and reduce inventory accordingly
- [x] Deny sales when ingredients are insufficient

### Optional Features (Implemented)
- [x] Warning when ingredients fall below threshold for 4 drinks
- [x] Mixed fruit drink sales with combined flavors
- [x] Multiple drink sizes (Small, Medium, Large)
- [x] Ingredient costs and profit-based pricing
- [x] Sales tracking over time with daily reports

## Technical Architecture

### Design Patterns Used
- **Repository Pattern**: For data access abstraction
- **Service Layer Pattern**: For business logic separation
- **Strategy Pattern**: For pricing calculations
- **Builder Pattern**: For complex object construction

### Key Components
- **Model**: Immutable domain objects (Recipe, Sale, etc.)
- **Service**: Business logic layer (VendorService, PricingService)
- **Repository**: Data access layer (InventoryRepository, SalesRepository)
- **Exception Handling**: Custom exceptions for business rules
- **Utilities**: Console formatting and display utilities

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Building and Running

### Using Maven

1. **Compile the project:**
   ```bash
   mvn compile
   ```

2. **Run tests:**
   ```bash
   mvn test
   ```

3. **Build JAR file:**
   ```bash
   mvn package
   ```

4. **Run the application:**
   ```bash
   java -jar target/formos-fruit-vendor.jar
   ```

### Using IntelliJ IDEA

1. **Import the project:**
   - File → Open → Select the project directory
   - Choose "Import Maven project" when prompted

2. **Run the application:**
   - Navigate to `Main.java`
   - Right-click and select "Run 'Main.main()'"

3. **Run tests:**
   - Right-click on `src/test/java` directory
   - Select "Run 'All Tests'"

## Usage

The application provides a console-based menu interface with the following options:

1. **View Current Inventory** - Display all ingredient quantities
2. **Sell Single Flavor Drink** - Create and sell a drink with one flavor
3. **Sell Mixed Flavor Drink** - Create and sell a drink with multiple flavors
4. **Check Low Stock Ingredients** - View ingredients below specified threshold
5. **View Daily Sales Report** - See sales summary for a specific date
6. **View All Sales History** - Display complete sales history

## Recipe Specifications

### Base Recipe (per 100ml)
- Blended fruit: 50ml
- Ice: 30ml
- Condensed milk: 20ml
- Sugar: 8g

### Fruit Requirements (per 100ml blended fruit)
- **Strawberry**: 100g of strawberries
- **Banana**: 120g of bananas
- **Mango**: 140g of mango

### Available Sizes
- **Small**: 200ml
- **Medium**: 300ml (standard requirement)
- **Large**: 500ml

## Pricing Strategy

The application uses a cost-plus pricing model:
- Ingredient costs are calculated per unit
- 60% profit margin is applied
- Mixed drinks include a 10% complexity premium
- Prices are rounded to the nearest nickel

## Initial Inventory

The system starts with the following inventory:
- Strawberries: 5,000g (5kg)
- Bananas: 6,000g (6kg)
- Mango: 4,000g (4kg)
- Ice: 10,000ml (10L)
- Condensed milk: 3,000ml (3L)
- Sugar: 2,000g (2kg)

## Testing

The project includes comprehensive unit tests using:
- **JUnit 5**: Modern testing framework
- **AssertJ**: Fluent assertions
- **Mockito**: Mocking framework

Run tests with:
```bash
mvn test
```

## Code Quality Features

- **Immutable objects** where appropriate
- **Comprehensive error handling** with custom exceptions
- **Clean architecture** with separation of concerns
- **Type safety** using enums and generics
- **Null safety** with proper validation
- **Thread safety** considerations in repositories
- **Documentation** with Javadoc comments

## Author

Developed for Robert Roman Java Developer Skills Test
