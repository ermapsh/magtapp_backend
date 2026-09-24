# MagTapp Pro — Backend README

## Overview

This backend implements the core backend flow for the MagTapp Pro subscription prototype.

The current implementation focuses on:

* User authentication with JWT
* Order creation
* Payment initiation
* Mock payment processing
* Asynchronous bank/payment callback simulation
* Payment authorization and capture
* Order status updates

The payment system is intentionally mocked. No real banking or payment-provider integration is used.

---

## Tech Stack

* Java
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA
* PostgreSQL
* Maven
* Lombok

---

## Backend Architecture

```text
Flutter App
    │
    ▼
REST API
    │
    ├── Authentication
    │       ├── Signup
    │       └── Login
    │
    ├── Order
    │       └── Create Order
    │
    └── Payment
            │
            ├── Initiate Payment
            │
            ├── Mock Payment Processor
            │
            ├── Bank Callback Simulator
            │
            ├── Authorization
            │
            └── Capture
```

---

# Authentication

Authentication uses JWT-based stateless authentication.

### Signup

```http
POST /api/v1/auth/signup
```

Creates a user and returns an access token.

### Login

```http
POST /api/v1/auth/login
```

Authenticates the user and returns an access token.

Protected APIs use:

```http
Authorization: Bearer <access-token>
```

The JWT contains the user's ID and is validated by `JwtAuthenticationFilter`.

---

# Order Flow

Authenticated users can create an order.

```http
POST /api/v1/order
```

The order is associated with the currently authenticated user.

An order starts in:

```text
CREATED
```

The order is later moved to:

```text
ATTEMPTED
```

when payment initiation begins.

After successful payment capture:

```text
PAID
```

---

# Payment Flow

The payment flow is designed to simulate an asynchronous payment provider.

```text
Create Order
     ↓
Initiate Payment
     ↓
Payment = AUTHORIZING
     ↓
Bank Callback Simulator
     ↓
Authorization Result
     ↓
Capture
     ↓
Payment = CAPTURED
     ↓
Order = PAID
```

## Payment Initiation

The payment service:

1. Finds and locks the order.
2. Validates the order status.
3. Changes the order to `ATTEMPTED`.
4. Creates a payment.
5. Sets the payment status to `AUTHORIZING`.
6. Sends the payment request to the mock payment processor.

The order is locked using a pessimistic database lock to prevent concurrent payment attempts from modifying the same order simultaneously.

---

# Mock Payment Processor

The project does not connect to a real payment processor.

Instead, a `MockPaymentProcessor` is used.

It currently returns hardcoded successful responses:

```java
new PaymentResult.Success("SIM_BANK_REF_123456");
```

The processor exposes two operations:

```text
authorize()
capture()
```

This keeps the payment service independent from the actual payment-processing implementation.

---

# Bank Callback Simulator

The `BankCallbackSimulator` represents an external bank/payment processor callback.

It periodically checks for payments with:

```text
PaymentStatus.AUTHORIZING
```

that were created more than the configured delay ago.

Conceptually:

```sql
SELECT *
FROM payment
WHERE payment_status = 'AUTHORIZING'
AND created_at < <cutoff>;
```

The simulator then resolves the authorization.

For the current implementation, the simulated bank approves the payment.

A simulated bank reference is generated and passed to:

```text
resolveAuthorization()
```

---

# Authorization & Capture

Once the simulated bank approves the payment:

```text
AUTHORIZING
     ↓
AUTHORIZE_SUCCESS
     ↓
CAPTURE_REQUEST
     ↓
MockPaymentProcessor.capture()
     ↓
CAPTURE_SUCCESS
     ↓
CAPTURED
```

After successful capture:

```text
Payment → CAPTURED
Order   → PAID
```

The payment state transitions are handled through the payment transition service rather than directly changing every state throughout the business logic.

---

# Transaction & Concurrency Handling

Payment initiation uses a transaction with:

```java
@Transactional(isolation = Isolation.REPEATABLE_READ)
```

The order is retrieved using a pessimistic write lock:

```java
findByIdAndMerchantIdForUpdate(...)
```

This prevents two concurrent payment requests from processing the same order at the same time.

---

# Current Payment Result

The payment result is currently intentionally simple:

```java
public sealed interface PaymentResult
        permits PaymentResult.Success {

    record Success(String bankReference)
            implements PaymentResult {
    }
}
```

Failure and pending responses can be added later if needed.

---

# Current Scope

Implemented:

* JWT authentication
* User identification
* Protected APIs
* Order creation
* Order/payment relationship
* Payment initiation
* Payment authorization state
* Mock payment processor
* Bank callback simulation
* Payment capture
* Payment state transitions
* Order marked as paid after successful capture
* Transaction and concurrency handling

Not implemented yet:

* Real Apple/Google subscription billing
* Real payment gateway
* Real banking integration
* Production webhook verification
* Subscription entitlement activation
* Payment refunds
* Payment reconciliation
* Refresh-token flow

---

# Next Step

The next backend layer is the **Pro subscription entitlement flow**:

```text
Payment CAPTURED
       ↓
Subscription
       ↓
ACTIVE
       ↓
User has Pro entitlement
       ↓
Flutter displays Pro
```

This will complete the backend side of the core **User → Order → Payment → Subscription → Pro Entitlement** flow required for the assignment.
