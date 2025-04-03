# Bookstore RESTful API

A comprehensive RESTful API for a bookstore that provides complete CRUD operations for book resources. The application includes a database with approximately 50 sample book entries.

## Features

- **Complete CRUD Operations**: Create, read, update, and delete book resources.
- **REST Architecture**: Follows REST principles for API design.
- **Rich Database**: Pre-populated with ~50 literary classics.
- **JSON Responses**: All API responses are in JSON format.
- **Input Validation**: Proper validation of all input data.
- **Error Handling**: Comprehensive error handling for all API endpoints.
- **Documentation**: Well-documented API with examples.

## API Endpoints

### Retrieve All Books
```
GET /api/books
```
Returns a list of all books in the database.

### Retrieve a Specific Book
```
GET /api/books/{id}
```
Returns a specific book by its ID.

### Create a New Book
```
POST /api/books
```
Creates a new book entry. Required fields:
- `title`: Book title
- `author`: Book author
- `isbn`: ISBN number (unique)
- `price`: Book price

Optional fields:
- `publisher`: Publisher name
- `publication_year`: Year of publication
- `description`: Book description
- `genre`: Book genre
- `language`: Book language (defaults to "English")
- `page_count`: Number of pages
- `in_stock`: Availability status (defaults to true)

### Update a Book
```
PUT /api/books/{id}
```
Updates an existing book entry. Include only the fields you want to update.

### Delete a Book
```
DELETE /api/books/{id}
```
Deletes a book entry.

## Book Model

The book model includes the following fields:

- `id`: Integer (Primary Key)
- `title`: String (Required)
- `author`: String (Required)
- `isbn`: String (Required, Unique)
- `publisher`: String
- `publication_year`: Integer
- `price`: Float (Required)
- `description`: Text
- `genre`: String
- `language`: String (Default: "English")
- `page_count`: Integer
- `in_stock`: Boolean (Default: true)

## Getting Started

### Prerequisites
- Python 3.7+
- Flask
- Flask-SQLAlchemy
- PostgreSQL (or the database of your choice)

### Installation

1. Clone the repository:
```
git clone https://github.com/your-username/bookstore-api.git
cd bookstore-api
```

2. Install dependencies:
```
pip install -r requirements.txt
```

3. Set the environment variables:
```
export DATABASE_URL=your_database_url
```

4. Run the application:
```
python main.py
```

The API will be running at `http://localhost:5000`.

## Testing the API

You can test the API using curl or Postman:

### Get all books:
```
curl -X GET http://localhost:5000/api/books
```

### Get a specific book:
```
curl -X GET http://localhost:5000/api/books/1
```

### Create a new book:
```
curl -X POST http://localhost:5000/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "New Book",
    "author": "New Author",
    "isbn": "9781234567890",
    "price": 19.99,
    "publisher": "New Publisher",
    "publication_year": 2023,
    "description": "A great new book.",
    "genre": "Fiction",
    "language": "English",
    "page_count": 300,
    "in_stock": true
  }'
```

### Update a book:
```
curl -X PUT http://localhost:5000/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{
    "price": 24.99,
    "in_stock": false
  }'
```

### Delete a book:
```
curl -X DELETE http://localhost:5000/api/books/1
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.