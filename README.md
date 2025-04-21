# Common Platform Notification Services

A microservice component of the Firefly platform that provides unified notification capabilities through multiple channels: Email, SMS, and Push Notifications.

## Overview

The Common Platform Notification Services is a reactive Spring Boot application that provides a unified API for sending notifications through various channels. It abstracts away the complexity of integrating with different notification providers, offering a consistent interface for all notification needs.

## Features

- **Multi-channel Notifications**: Send notifications via:
  - Email (using SendGrid)
  - SMS (using Twilio)
  - Push Notifications (using Firebase)

- **Reactive Architecture**: Built with Spring WebFlux for non-blocking, reactive processing

- **Provider Abstraction**: Easily switch between different notification providers without changing client code

- **Comprehensive API Documentation**: Fully documented API with Swagger/OpenAPI

## Architecture

The service follows a modular architecture with clear separation of concerns:

```
common-platform-notification-services/
├── common-platform-notification-services-core/         # Core business logic
├── common-platform-notification-services-interfaces/   # API interfaces and DTOs
├── common-platform-notification-services-web/          # Web controllers and application entry point
├── common-platform-notification-services-providers-twilio/    # Twilio SMS provider
├── common-platform-notification-services-providers-sendgrid/  # SendGrid email provider
├── common-platform-notification-services-providers-firebase/  # Firebase push notification provider
```

## API Endpoints

### Email

```
POST /api/v1/email/send
```

Sends an email using the configured provider (SendGrid).

**Request Body:**
```json
{
  "to": "recipient@example.com",
  "subject": "Email Subject",
  "body": "Email content in HTML or plain text",
  "attachments": [
    {
      "filename": "document.pdf",
      "content": "base64-encoded-content",
      "contentType": "application/pdf"
    }
  ]
}
```

### SMS

```
POST /api/v1/sms/send
```

Sends an SMS message using the configured provider (Twilio).

**Request Body:**
```json
{
  "phoneNumber": "+1234567890",
  "message": "Hello, World!"
}
```

### Push Notifications

```
POST /api/v1/push
```

Sends a push notification using the configured provider (Firebase).

**Request Body:**
```json
{
  "token": "device-token",
  "title": "Notification Title",
  "body": "Notification Body",
  "data": {
    "key1": "value1",
    "key2": "value2"
  }
}
```

## Setup and Configuration

### Prerequisites

- Java 21 or higher
- Maven 3.8 or higher
- PostgreSQL database
- Provider accounts:
  - SendGrid account and API key
  - Twilio account, auth token, and phone number
  - Firebase project and credentials

### Environment Variables

The following environment variables need to be set:

```
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=notifications
DB_USERNAME=postgres
DB_PASSWORD=password
DB_SSL_MODE=disable

# Twilio Configuration
TWILIO_ACCOUNT_SID=your-account-sid
TWILIO_AUTH_TOKEN=your-auth-token
TWILIO_PHONE_NUMBER=your-twilio-phone-number

# SendGrid Configuration
SENDGRID_API_KEY=your-sendgrid-api-key

# Firebase Configuration
FIREBASE_CREDENTIALS=path-to-firebase-credentials-json
```

### Building the Application

```bash
mvn clean package
```

### Running the Application

```bash
java -jar common-platform-notification-services-web/target/common-platform-notification-services-web-1.0.0-SNAPSHOT.jar
```

Or using Docker:

```bash
docker build -t common-platform-notification-services .
docker run -p 8080:8080 --env-file .env common-platform-notification-services
```

## Deployment

The service can be deployed using the provided Dockerfile. CI/CD pipelines are configured in `.github/workflows/publish.yml` to:

1. Build and test the application
2. Publish the Maven artifacts to GitHub Packages
3. Build and publish the Docker image to Azure Container Registry

## Documentation

API documentation is available at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Health Checks

Health endpoints are available at:
- http://localhost:8080/actuator/health
- http://localhost:8080/actuator/health/liveness
- http://localhost:8080/actuator/health/readiness

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request