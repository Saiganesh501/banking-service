# 🏦 Banking Customer Information Platform
## Your Project Structure — Explained Simply

---

## 📁 FOLDER STRUCTURE

```
banking-platform/
│
├── pom.xml                          ← Maven: all dependencies listed here
│
└── src/
    ├── main/
    │   ├── java/com/tcs/banking/
    │   │   │
    │   │   ├── BankingApplication.java        ← ENTRY POINT — starts the app
    │   │   │
    │   │   ├── controller/
    │   │   │   └── CustomerController.java    ← LAYER 1: Receives HTTP requests
    │   │   │
    │   │   ├── service/
    │   │   │   └── CustomerService.java       ← LAYER 2: Business logic lives here
    │   │   │
    │   │   ├── repository/
    │   │   │   └── CustomerRepository.java    ← LAYER 3: Talks to PostgreSQL
    │   │   │
    │   │   ├── entity/
    │   │   │   └── Customer.java              ← Maps to DB table (Hibernate)
    │   │   │
    │   │   ├── dto/
    │   │   │   └── CustomerDTO.java           ← What we send/receive in API
    │   │   │
    │   │   └── exception/
    │   │       ├── CustomerNotFoundException.java
    │   │       ├── DuplicateCustomerException.java
    │   │       └── GlobalExceptionHandler.java ← Handles all errors centrally
    │   │
    │   └── resources/
    │       └── application.properties         ← DB config, server port etc.
    │
    └── test/
        └── java/com/tcs/banking/
            └── service/
                └── CustomerServiceTest.java   ← JUnit 5 + Mockito tests
```

---

## 🔄 HOW A REQUEST FLOWS (Most Important!)

```
1. Client sends:  GET /api/v1/customers/CUS001

2. CustomerController receives it
   → @GetMapping("/{customerId}")
   → Calls customerService.getCustomerById("CUS001")

3. CustomerService processes it
   → Calls customerRepository.findByCustomerId("CUS001")
   → If not found → throws CustomerNotFoundException
   → If found → converts Entity to DTO

4. CustomerRepository hits PostgreSQL
   → SELECT * FROM customers WHERE customer_id = 'CUS001'

5. Response goes back:
   → DTO → JSON → HTTP 200 Response
```

---

## 🔑 KEY ANNOTATIONS — Remember These!

| Annotation          | Layer       | What it does                        |
|---------------------|-------------|-------------------------------------|
| @SpringBootApplication | Main    | Starts the entire application       |
| @RestController     | Controller  | Handles HTTP requests, returns JSON |
| @RequestMapping     | Controller  | Sets base URL path                  |
| @GetMapping         | Controller  | Handles GET requests                |
| @PostMapping        | Controller  | Handles POST requests               |
| @Service            | Service     | Business logic layer                |
| @Transactional      | Service     | DB operations roll back on failure  |
| @Repository         | Repository  | Data access layer                   |
| @Entity             | Entity      | Maps Java class to DB table         |
| @Autowired          | All layers  | Spring injects the dependency       |

---

## 💡 INTERVIEW ANSWERS FROM THIS CODE

**Q: How does Spring Data JPA work?**
A: "We extend JpaRepository in our Repository interface. Spring auto-generates
   SQL from method names. For example, findByCustomerId() generates:
   SELECT * FROM customers WHERE customer_id = ?"

**Q: What is @Transactional?**
A: "It ensures all DB operations in a method succeed together or roll back
   together. For example, in onboardCustomer(), if saving fails midway,
   the entire operation is rolled back — no partial data in DB."

**Q: How did you use Java 8 Streams?**
A: "In CustomerService, I used streams to convert List<Customer> to
   List<CustomerDTO>, filter active customers, and sort by balance.
   For example: customers.stream().filter(...).map(...).collect(...)"

**Q: What is DTO and why do you use it?**
A: "DTO is Data Transfer Object. We never expose the Entity directly
   to the API because the Entity is tied to the DB table. DTO controls
   exactly what data goes in and out of the API — clean architecture."

**Q: How did you handle exceptions?**
A: "We used @RestControllerAdvice for centralized exception handling.
   Instead of try-catch in every method, all exceptions are caught in
   GlobalExceptionHandler and return proper HTTP status codes with
   meaningful JSON error messages."
