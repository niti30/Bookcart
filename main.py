from flask import Flask, render_template_string

app = Flask(__name__)

# Simple HTML template with instructions
DOCS_TEMPLATE = '''
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookstore API Documentation</title>
    <link href="https://cdn.replit.com/agent/bootstrap-agent-dark-theme.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Bookstore RESTful API</h1>
        
        <div class="card mb-4">
            <div class="card-header">
                <h2 class="card-title h5 mb-0">How to Run Locally</h2>
            </div>
            <div class="card-body">
                <p>This is a RESTful API for a bookstore with complete CRUD operations. To run it locally:</p>
                <pre class="bg-dark text-light p-3 rounded">
# Clone the repository
git clone https://github.com/yourusername/bookstore-api.git
cd bookstore-api

# Run the application with Maven Wrapper
./mvnw spring-boot:run
                </pre>
                <p>The API will be available at <code>http://localhost:8000</code></p>
                <p>Swagger UI: <code>http://localhost:8000/swagger-ui</code></p>
                <p>H2 Console: <code>http://localhost:8000/h2-console</code> (JDBC URL: <code>jdbc:h2:mem:bookstoredb</code>, Username: <code>sa</code>, Password: <code>password</code>)</p>
            </div>
        </div>
        
        <div class="card mb-4">
            <div class="card-header">
                <h2 class="card-title h5 mb-0">Available Endpoints</h2>
            </div>
            <div class="card-body">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Endpoint</th>
                            <th>Method</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr><td>/api/books</td><td>GET</td><td>Get all books</td></tr>
                        <tr><td>/api/books/{id}</td><td>GET</td><td>Get book by ID</td></tr>
                        <tr><td>/api/books/isbn/{isbn}</td><td>GET</td><td>Get book by ISBN</td></tr>
                        <tr><td>/api/books</td><td>POST</td><td>Create a new book</td></tr>
                        <tr><td>/api/books/{id}</td><td>PUT</td><td>Update a book</td></tr>
                        <tr><td>/api/books/{id}</td><td>DELETE</td><td>Delete a book</td></tr>
                        <tr><td>/api/books/search/author?author={author}</td><td>GET</td><td>Find books by author</td></tr>
                        <tr><td>/api/books/search/title?title={title}</td><td>GET</td><td>Find books by title</td></tr>
                        <tr><td>/api/books/search/genre?genre={genre}</td><td>GET</td><td>Find books by genre</td></tr>
                    </tbody>
                </table>
            </div>
        </div>
        
        <div class="card">
            <div class="card-header">
                <h2 class="card-title h5 mb-0">Project Structure</h2>
            </div>
            <div class="card-body">
                <pre class="bg-dark text-light p-3 rounded">
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── bookstore
│   │   │           ├── controller
│   │   │           │   └── BookController.java
│   │   │           ├── exception
│   │   │           │   ├── GlobalExceptionHandler.java
│   │   │           │   └── ResourceNotFoundException.java
│   │   │           ├── model
│   │   │           │   └── Book.java
│   │   │           ├── repository
│   │   │           │   └── BookRepository.java
│   │   │           ├── service
│   │   │           │   ├── BookService.java
│   │   │           │   └── BookServiceImpl.java
│   │   │           └── BookstoreApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── data.sql
│   └── test
│       └── java
│           └── com
│               └── bookstore
│                   ├── controller
│                   │   └── BookControllerTest.java
│                   ├── repository
│                   │   └── BookRepositoryTest.java
│                   ├── service
│                   │   └── BookServiceTest.java
│                   └── BookstoreApplicationTests.java
                </pre>
            </div>
        </div>
    </div>
</body>
</html>
'''

@app.route('/')
def home():
    """
    Display documentation for the Bookstore API
    """
    return render_template_string(DOCS_TEMPLATE)