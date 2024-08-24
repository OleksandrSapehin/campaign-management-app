# Campaign Management App

## Overview

The Campaign Management App is a web-based application that allows users to create, edit, delete, and view advertising campaigns for different products. Sellers can manage their campaigns by specifying keywords, bid amounts, campaign funds, status, target towns, and campaign radius.

## Features

- **Create Campaigns**: Sellers can create new campaigns for products by providing necessary details such as campaign name, keywords, bid amount, etc.
- **Edit Campaigns**: Existing campaigns can be edited with updated information.
- **Delete Campaigns**: Sellers can delete campaigns that are no longer needed.
- **View Campaigns**: View a list of campaigns for a specific product.

## Technologies Used

### Backend

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **H2 Database (In-Memory)**
- **Maven**

### Frontend

- **React**
- **Axios**
- **Bootstrap** (for styling)
- **React Autosuggest** (for keyword suggestions)

## Installation and Setup

### Prerequisites

- **Java 21** installed on your machine
- **Node.js** and **npm** installed on your machine
- **Maven** installed on your machine

### Backend Setup

1. Navigate to the backend directory:

    ```bash
    cd managing_campaigns_app_backend
    ```

2. Install dependencies and build the project:

    ```bash
    mvn clean install
    ```

3. Run the Spring Boot application:

    ```bash
    mvn spring-boot:run
    ```

   The backend server should now be running on `http://localhost:8080`.

. Navigate to the frontend directory:

    ```bash
    cd ../frontend/campaign-front
    ```

2. Install the necessary npm packages:

    ```bash
    npm install
    ```

   Make sure the following npm packages are installed:
   
   - **axios**: A promise-based HTTP client for making requests to the backend.
   - **bootstrap**: For styling the application.
   - **autosuggest**: For implementing the typeahead functionality.
   
   These should be installed automatically via `npm install` if listed in the `package.json`. If not, you can install them manually:

    ```bash
    npm install react react-dom react-router-dom axios bootstrap react-autosuggest
    ```

4. Start the React development server:

    ```bash
    npm start
    ```

   The frontend server should now be running on `http://localhost:3000`.

## API Endpoints

### Campaigns

- **GET /api/campaigns/products/{productId}**: Retrieve all campaigns for a specific product.
- **POST /api/campaigns/products/{productId}?sellerId={sellerId}**: Create a new campaign for a product by a seller.
- **PUT /api/campaigns/{campaignId}**: Update an existing campaign.
- **DELETE /api/campaigns/{campaignId}**: Delete a campaign.

### Products

- **GET /api/products**: Retrieve a list of all products.
- **POST /api/products**: Create a new product.
- **PUT /api/products/{productId}**: Update an existing product.
- **DELETE /api/products/{productId}**: Delete a product.

### Sellers

- **GET /api/sellers**: Retrieve a list of all sellers.
- **POST /api/sellers**: Create a new seller.
- **PUT /api/sellers/{sellerId}**: Update an existing seller.
- **DELETE /api/sellers/{sellerId}**: Delete a seller.

## Testing

### Backend

1. Navigate to the backend directory:

    ```bash
    cd managing_campaigns_app_backend
    ```

2. Run the tests using Maven:

    ```bash
    mvn test
    ```



