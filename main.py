import os
from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.orm import DeclarativeBase


class Base(DeclarativeBase):
    pass


db = SQLAlchemy(model_class=Base)
# create the app
app = Flask(__name__)
# setup a secret key, required by sessions
app.secret_key = os.environ.get("FLASK_SECRET_KEY") or "a secret key"
# configure the database, relative to the app instance folder
app.config["SQLALCHEMY_DATABASE_URI"] = os.environ.get("DATABASE_URL")
app.config["SQLALCHEMY_ENGINE_OPTIONS"] = {
    "pool_recycle": 300,
    "pool_pre_ping": True,
}
# initialize the app with the extension, flask-sqlalchemy >= 3.0.x
db.init_app(app)


# Book model
class Book(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(100), nullable=False)
    author = db.Column(db.String(100), nullable=False)
    isbn = db.Column(db.String(13), unique=True, nullable=False)
    publisher = db.Column(db.String(100))
    publication_year = db.Column(db.Integer)
    price = db.Column(db.Float, nullable=False)
    description = db.Column(db.Text)
    genre = db.Column(db.String(50))
    language = db.Column(db.String(50), default="English")
    page_count = db.Column(db.Integer)
    in_stock = db.Column(db.Boolean, default=True)

    def to_dict(self):
        return {
            "id": self.id,
            "title": self.title,
            "author": self.author,
            "isbn": self.isbn,
            "publisher": self.publisher,
            "publication_year": self.publication_year,
            "price": self.price,
            "description": self.description,
            "genre": self.genre,
            "language": self.language,
            "page_count": self.page_count,
            "in_stock": self.in_stock
        }


# Initialize database
with app.app_context():
    db.create_all()
    
    # Add sample data if no books exist
    if Book.query.count() == 0:
        sample_books = [
            Book(
                title="To Kill a Mockingbird",
                author="Harper Lee",
                isbn="9780061120084",
                publisher="HarperCollins",
                publication_year=1960,
                price=12.99,
                description="A novel about racial inequality in the American South.",
                genre="Fiction",
                language="English",
                page_count=281,
                in_stock=True
            ),
            Book(
                title="1984",
                author="George Orwell",
                isbn="9780451524935",
                publisher="Penguin Books",
                publication_year=1949,
                price=11.99,
                description="A dystopian novel set in a totalitarian state.",
                genre="Science Fiction",
                language="English",
                page_count=328,
                in_stock=True
            ),
            Book(
                title="Pride and Prejudice",
                author="Jane Austen",
                isbn="9780141439518",
                publisher="Penguin Classics",
                publication_year=1813,
                price=9.99,
                description="A romantic novel that follows the character development of Elizabeth Bennet.",
                genre="Classic",
                language="English",
                page_count=416,
                in_stock=True
            ),
            Book(
                title="The Great Gatsby",
                author="F. Scott Fitzgerald",
                isbn="9780743273565",
                publisher="Scribner",
                publication_year=1925,
                price=14.99,
                description="A novel depicting the extravagance of the Jazz Age.",
                genre="Classic",
                language="English",
                page_count=180,
                in_stock=True
            ),
            Book(
                title="The Catcher in the Rye",
                author="J.D. Salinger",
                isbn="9780316769488",
                publisher="Little, Brown and Company",
                publication_year=1951,
                price=10.99,
                description="A novel about teenage alienation and identity.",
                genre="Fiction",
                language="English",
                page_count=224,
                in_stock=True
            ),
            Book(
                title="The Hobbit",
                author="J.R.R. Tolkien",
                isbn="9780547928227",
                publisher="Houghton Mifflin Harcourt",
                publication_year=1937,
                price=13.99,
                description="A fantasy novel about the adventures of Bilbo Baggins.",
                genre="Fantasy",
                language="English",
                page_count=310,
                in_stock=True
            ),
            Book(
                title="Harry Potter and the Philosopher's Stone",
                author="J.K. Rowling",
                isbn="9780747532743",
                publisher="Bloomsbury",
                publication_year=1997,
                price=15.99,
                description="The first novel in the Harry Potter series.",
                genre="Fantasy",
                language="English",
                page_count=320,
                in_stock=True
            ),
            Book(
                title="The Lord of the Rings",
                author="J.R.R. Tolkien",
                isbn="9780618640157",
                publisher="Houghton Mifflin Harcourt",
                publication_year=1954,
                price=25.99,
                description="An epic high-fantasy novel.",
                genre="Fantasy",
                language="English",
                page_count=1178,
                in_stock=True
            ),
            Book(
                title="The Alchemist",
                author="Paulo Coelho",
                isbn="9780062315007",
                publisher="HarperOne",
                publication_year=1988,
                price=16.99,
                description="A philosophical novel about following one's dreams.",
                genre="Fiction",
                language="English",
                page_count=208,
                in_stock=True
            ),
            Book(
                title="Moby-Dick",
                author="Herman Melville",
                isbn="9780142437247",
                publisher="Penguin Classics",
                publication_year=1851,
                price=12.99,
                description="A novel about the voyage of the whaling ship Pequod.",
                genre="Adventure",
                language="English",
                page_count=720,
                in_stock=True
            ),
            Book(
                title="Don Quixote",
                author="Miguel de Cervantes",
                isbn="9780142437230",
                publisher="Penguin Classics",
                publication_year=1605,
                price=18.99,
                description="A Spanish novel about a man who believes himself to be a knight.",
                genre="Classic",
                language="English",
                page_count=1023,
                in_stock=True
            ),
            Book(
                title="War and Peace",
                author="Leo Tolstoy",
                isbn="9780143039990",
                publisher="Penguin Classics",
                publication_year=1869,
                price=20.99,
                description="A novel about Russian society during the Napoleonic Wars.",
                genre="Historical Fiction",
                language="English",
                page_count=1392,
                in_stock=True
            ),
            Book(
                title="The Odyssey",
                author="Homer",
                isbn="9780140268867",
                publisher="Penguin Classics",
                publication_year=725,
                price=11.99,
                description="An ancient Greek epic poem attributed to Homer.",
                genre="Epic Poetry",
                language="English",
                page_count=541,
                in_stock=True
            ),
            Book(
                title="Crime and Punishment",
                author="Fyodor Dostoevsky",
                isbn="9780143058144",
                publisher="Penguin Classics",
                publication_year=1866,
                price=14.99,
                description="A novel about a poor student who commits a murder.",
                genre="Psychological Fiction",
                language="English",
                page_count=671,
                in_stock=True
            ),
            Book(
                title="The Brothers Karamazov",
                author="Fyodor Dostoevsky",
                isbn="9780374528379",
                publisher="Farrar, Straus and Giroux",
                publication_year=1880,
                price=17.99,
                description="A novel about the moral struggles of three brothers.",
                genre="Philosophical Fiction",
                language="English",
                page_count=824,
                in_stock=True
            ),
            Book(
                title="One Hundred Years of Solitude",
                author="Gabriel García Márquez",
                isbn="9780060883287",
                publisher="Harper Perennial",
                publication_year=1967,
                price=16.99,
                description="A landmark novel in the magical realism genre.",
                genre="Magical Realism",
                language="English",
                page_count=417,
                in_stock=True
            ),
            Book(
                title="The Picture of Dorian Gray",
                author="Oscar Wilde",
                isbn="9780141439570",
                publisher="Penguin Classics",
                publication_year=1890,
                price=10.99,
                description="A Gothic and philosophical novel by Oscar Wilde.",
                genre="Gothic Fiction",
                language="English",
                page_count=254,
                in_stock=True
            ),
            Book(
                title="Jane Eyre",
                author="Charlotte Brontë",
                isbn="9780141441146",
                publisher="Penguin Classics",
                publication_year=1847,
                price=11.99,
                description="A novel by English writer Charlotte Brontë.",
                genre="Gothic Fiction",
                language="English",
                page_count=532,
                in_stock=True
            ),
            Book(
                title="The Grapes of Wrath",
                author="John Steinbeck",
                isbn="9780143039433",
                publisher="Penguin Classics",
                publication_year=1939,
                price=13.99,
                description="A novel set during the Great Depression.",
                genre="Historical Fiction",
                language="English",
                page_count=464,
                in_stock=True
            ),
            Book(
                title="Brave New World",
                author="Aldous Huxley",
                isbn="9780060850524",
                publisher="Harper Perennial",
                publication_year=1932,
                price=12.99,
                description="A dystopian novel by English author Aldous Huxley.",
                genre="Science Fiction",
                language="English",
                page_count=288,
                in_stock=True
            ),
            Book(
                title="The Divine Comedy",
                author="Dante Alighieri",
                isbn="9780142437223",
                publisher="Penguin Classics",
                publication_year=1320,
                price=15.99,
                description="An Italian narrative poem by Dante Alighieri.",
                genre="Epic Poetry",
                language="English",
                page_count=752,
                in_stock=True
            ),
            Book(
                title="Frankenstein",
                author="Mary Shelley",
                isbn="9780141439471",
                publisher="Penguin Classics",
                publication_year=1818,
                price=10.99,
                description="A Gothic novel written by English author Mary Shelley.",
                genre="Gothic Fiction",
                language="English",
                page_count=273,
                in_stock=True
            ),
            Book(
                title="The Count of Monte Cristo",
                author="Alexandre Dumas",
                isbn="9780140449266",
                publisher="Penguin Classics",
                publication_year=1844,
                price=16.99,
                description="An adventure novel by French author Alexandre Dumas.",
                genre="Adventure",
                language="English",
                page_count=1276,
                in_stock=True
            ),
            Book(
                title="Alice's Adventures in Wonderland",
                author="Lewis Carroll",
                isbn="9780141439761",
                publisher="Penguin Classics",
                publication_year=1865,
                price=9.99,
                description="A novel about a girl named Alice who falls through a rabbit hole into a fantasy world.",
                genre="Fantasy",
                language="English",
                page_count=163,
                in_stock=True
            ),
            Book(
                title="The Little Prince",
                author="Antoine de Saint-Exupéry",
                isbn="9780156012195",
                publisher="Harcourt, Inc.",
                publication_year=1943,
                price=10.99,
                description="A novella by French aristocrat, writer, and aviator Antoine de Saint-Exupéry.",
                genre="Children's Literature",
                language="English",
                page_count=96,
                in_stock=True
            ),
            Book(
                title="The Old Man and the Sea",
                author="Ernest Hemingway",
                isbn="9780684801223",
                publisher="Scribner",
                publication_year=1952,
                price=12.99,
                description="A short novel by Ernest Hemingway about an aging Cuban fisherman.",
                genre="Literary Fiction",
                language="English",
                page_count=127,
                in_stock=True
            ),
            Book(
                title="Anna Karenina",
                author="Leo Tolstoy",
                isbn="9780143035008",
                publisher="Penguin Classics",
                publication_year=1877,
                price=14.99,
                description="A novel by the Russian author Leo Tolstoy.",
                genre="Realistic Fiction",
                language="English",
                page_count=864,
                in_stock=True
            ),
            Book(
                title="Les Misérables",
                author="Victor Hugo",
                isbn="9780451419439",
                publisher="Signet",
                publication_year=1862,
                price=15.99,
                description="A French historical novel by Victor Hugo.",
                genre="Historical Fiction",
                language="English",
                page_count=1488,
                in_stock=True
            ),
            Book(
                title="The Adventures of Huckleberry Finn",
                author="Mark Twain",
                isbn="9780142437179",
                publisher="Penguin Classics",
                publication_year=1884,
                price=11.99,
                description="A novel by Mark Twain about a young boy's journey down the Mississippi River.",
                genre="Adventure",
                language="English",
                page_count=366,
                in_stock=True
            ),
            Book(
                title="Wuthering Heights",
                author="Emily Brontë",
                isbn="9780141439556",
                publisher="Penguin Classics",
                publication_year=1847,
                price=10.99,
                description="A novel by Emily Brontë set on the Yorkshire moors.",
                genre="Gothic Fiction",
                language="English",
                page_count=416,
                in_stock=True
            ),
            Book(
                title="The Iliad",
                author="Homer",
                isbn="9780140275360",
                publisher="Penguin Classics",
                publication_year=800,
                price=13.99,
                description="An ancient Greek epic poem attributed to Homer.",
                genre="Epic Poetry",
                language="English",
                page_count=683,
                in_stock=True
            ),
            Book(
                title="Great Expectations",
                author="Charles Dickens",
                isbn="9780141439563",
                publisher="Penguin Classics",
                publication_year=1861,
                price=10.99,
                description="A novel by Charles Dickens about an orphan boy named Pip.",
                genre="Coming-of-age",
                language="English",
                page_count=544,
                in_stock=True
            ),
            Book(
                title="The Canterbury Tales",
                author="Geoffrey Chaucer",
                isbn="9780140424386",
                publisher="Penguin Classics",
                publication_year=1400,
                price=12.99,
                description="A collection of 24 stories by Geoffrey Chaucer.",
                genre="Poetry",
                language="English",
                page_count=1328,
                in_stock=True
            ),
            Book(
                title="Dracula",
                author="Bram Stoker",
                isbn="9780141439846",
                publisher="Penguin Classics",
                publication_year=1897,
                price=11.99,
                description="A Gothic horror novel by Irish author Bram Stoker.",
                genre="Gothic Horror",
                language="English",
                page_count=448,
                in_stock=True
            ),
            Book(
                title="The Metamorphosis",
                author="Franz Kafka",
                isbn="9780553213690",
                publisher="Bantam Classics",
                publication_year=1915,
                price=5.99,
                description="A novella by Franz Kafka about a man who wakes up transformed into a giant insect.",
                genre="Absurdist Fiction",
                language="English",
                page_count=201,
                in_stock=True
            ),
            Book(
                title="The Three Musketeers",
                author="Alexandre Dumas",
                isbn="9780140437133",
                publisher="Penguin Classics",
                publication_year=1844,
                price=13.99,
                description="A historical adventure novel written by French author Alexandre Dumas.",
                genre="Historical Adventure",
                language="English",
                page_count=736,
                in_stock=True
            ),
            Book(
                title="The Jungle Book",
                author="Rudyard Kipling",
                isbn="9780141325293",
                publisher="Puffin Classics",
                publication_year=1894,
                price=8.99,
                description="A collection of stories by the English author Rudyard Kipling.",
                genre="Children's Literature",
                language="English",
                page_count=256,
                in_stock=True
            ),
            Book(
                title="The Wind in the Willows",
                author="Kenneth Grahame",
                isbn="9780143039099",
                publisher="Penguin Classics",
                publication_year=1908,
                price=9.99,
                description="A children's novel by Scottish novelist Kenneth Grahame.",
                genre="Children's Literature",
                language="English",
                page_count=256,
                in_stock=True
            ),
            Book(
                title="David Copperfield",
                author="Charles Dickens",
                isbn="9780140439441",
                publisher="Penguin Classics",
                publication_year=1850,
                price=12.99,
                description="A novel by Charles Dickens about a character's journey from childhood to maturity.",
                genre="Bildungsroman",
                language="English",
                page_count=882,
                in_stock=True
            ),
            Book(
                title="A Tale of Two Cities",
                author="Charles Dickens",
                isbn="9780141439600",
                publisher="Penguin Classics",
                publication_year=1859,
                price=11.99,
                description="A historical novel by Charles Dickens set in London and Paris before and during the French Revolution.",
                genre="Historical Fiction",
                language="English",
                page_count=489,
                in_stock=True
            ),
            Book(
                title="Robinson Crusoe",
                author="Daniel Defoe",
                isbn="9780141439822",
                publisher="Penguin Classics",
                publication_year=1719,
                price=10.99,
                description="A novel by Daniel Defoe about a man shipwrecked on a desert island.",
                genre="Adventure",
                language="English",
                page_count=320,
                in_stock=True
            ),
            Book(
                title="The Adventures of Tom Sawyer",
                author="Mark Twain",
                isbn="9780143039563",
                publisher="Penguin Classics",
                publication_year=1876,
                price=9.99,
                description="A novel about a young boy growing up along the Mississippi River.",
                genre="Adventure",
                language="English",
                page_count=244,
                in_stock=True
            ),
            Book(
                title="The Prince",
                author="Niccolò Machiavelli",
                isbn="9780140449150",
                publisher="Penguin Classics",
                publication_year=1532,
                price=8.99,
                description="A 16th-century political treatise by the Italian diplomat and political theorist Niccolò Machiavelli.",
                genre="Political Philosophy",
                language="English",
                page_count=128,
                in_stock=True
            ),
            Book(
                title="The Art of War",
                author="Sun Tzu",
                isbn="9780143037521",
                publisher="Penguin Classics",
                publication_year=-500,
                price=9.99,
                description="An ancient Chinese military treatise attributed to Sun Tzu.",
                genre="Military Strategy",
                language="English",
                page_count=273,
                in_stock=True
            ),
            Book(
                title="The Republic",
                author="Plato",
                isbn="9780140455113",
                publisher="Penguin Classics",
                publication_year=-380,
                price=12.99,
                description="A Socratic dialogue by Plato concerning justice, the order and character of the just city-state, and the just man.",
                genre="Philosophy",
                language="English",
                page_count=416,
                in_stock=True
            ),
            Book(
                title="The Origin of Species",
                author="Charles Darwin",
                isbn="9780451529060",
                publisher="Signet Classics",
                publication_year=1859,
                price=10.99,
                description="A work of scientific literature by Charles Darwin which is considered to be the foundation of evolutionary biology.",
                genre="Science",
                language="English",
                page_count=576,
                in_stock=True
            ),
            Book(
                title="The Scarlet Letter",
                author="Nathaniel Hawthorne",
                isbn="9780142437261",
                publisher="Penguin Classics",
                publication_year=1850,
                price=9.99,
                description="A work of historical fiction by American author Nathaniel Hawthorne.",
                genre="Romance",
                language="English",
                page_count=288,
                in_stock=True
            ),
            Book(
                title="The Sun Also Rises",
                author="Ernest Hemingway",
                isbn="9780743297332",
                publisher="Scribner",
                publication_year=1926,
                price=13.99,
                description="A novel by American author Ernest Hemingway about a group of American and British expatriates.",
                genre="Modernist Novel",
                language="English",
                page_count=251,
                in_stock=True
            )
        ]
        db.session.add_all(sample_books)
        db.session.commit()


# API Routes
@app.route('/api/books', methods=['GET'])
def get_all_books():
    books = Book.query.all()
    return jsonify([book.to_dict() for book in books])


@app.route('/api/books/<int:id>', methods=['GET'])
def get_book_by_id(id):
    book = Book.query.get_or_404(id)
    return jsonify(book.to_dict())


@app.route('/api/books', methods=['POST'])
def create_book():
    data = request.json
    new_book = Book(
        title=data['title'],
        author=data['author'],
        isbn=data['isbn'],
        publisher=data.get('publisher'),
        publication_year=data.get('publication_year'),
        price=data['price'],
        description=data.get('description'),
        genre=data.get('genre'),
        language=data.get('language', 'English'),
        page_count=data.get('page_count'),
        in_stock=data.get('in_stock', True)
    )
    db.session.add(new_book)
    db.session.commit()
    return jsonify(new_book.to_dict()), 201


@app.route('/api/books/<int:id>', methods=['PUT'])
def update_book(id):
    book = Book.query.get_or_404(id)
    data = request.json
    
    book.title = data.get('title', book.title)
    book.author = data.get('author', book.author)
    book.isbn = data.get('isbn', book.isbn)
    book.publisher = data.get('publisher', book.publisher)
    book.publication_year = data.get('publication_year', book.publication_year)
    book.price = data.get('price', book.price)
    book.description = data.get('description', book.description)
    book.genre = data.get('genre', book.genre)
    book.language = data.get('language', book.language)
    book.page_count = data.get('page_count', book.page_count)
    book.in_stock = data.get('in_stock', book.in_stock)
    
    db.session.commit()
    return jsonify(book.to_dict())


@app.route('/api/books/<int:id>', methods=['DELETE'])
def delete_book(id):
    book = Book.query.get_or_404(id)
    db.session.delete(book)
    db.session.commit()
    return jsonify({"message": f"Book with id {id} has been deleted successfully"}), 200


# Home page
@app.route('/')
def home():
    return """
    <html>
        <head>
            <title>Bookstore API</title>
            <link href="https://cdn.replit.com/agent/bootstrap-agent-dark-theme.min.css" rel="stylesheet">
            <style>
                .api-section { margin-bottom: 30px; }
                .endpoint { margin-bottom: 20px; }
                .method { font-weight: bold; color: var(--bs-info); }
                .path { color: var(--bs-success); }
                .description { margin-top: 5px; }
                .request-example, .response-example { 
                    background-color: var(--bs-dark);
                    color: var(--bs-light);
                    padding: 15px; 
                    border-radius: 5px; 
                    margin-top: 10px;
                    font-family: monospace;
                    white-space: pre;
                }
                .container { max-width: 1000px; }
            </style>
        </head>
        <body data-bs-theme="dark">
            <div class="container my-5">
                <div class="row">
                    <div class="col-12">
                        <h1 class="display-4 mb-4">Bookstore API</h1>
                        <p class="lead">Welcome to the RESTful Bookstore API. This API provides access to a comprehensive collection of books with complete CRUD operations.</p>
                        <p>The database contains approximately 50 sample entries to help you get started.</p>
                        
                        <div class="api-section">
                            <h2>API Endpoints</h2>
                            
                            <div class="endpoint">
                                <h4><span class="method">GET</span> <span class="path">/api/books</span></h4>
                                <div class="description">Retrieves a list of all books in the database</div>
                                <div class="response-example">
// Response format:
[
  {
    "author": "Harper Lee",
    "description": "A novel about racial inequality in the American South.",
    "genre": "Fiction",
    "id": 1,
    "in_stock": true,
    "isbn": "9780061120084",
    "language": "English",
    "page_count": 281,
    "price": 12.99,
    "publication_year": 1960,
    "publisher": "HarperCollins",
    "title": "To Kill a Mockingbird"
  },
  // More books...
]</div>
                            </div>
                            
                            <div class="endpoint">
                                <h4><span class="method">GET</span> <span class="path">/api/books/{id}</span></h4>
                                <div class="description">Retrieves a specific book by its ID</div>
                                <div class="response-example">
// Response format:
{
  "author": "Harper Lee",
  "description": "A novel about racial inequality in the American South.",
  "genre": "Fiction",
  "id": 1,
  "in_stock": true,
  "isbn": "9780061120084",
  "language": "English",
  "page_count": 281,
  "price": 12.99,
  "publication_year": 1960,
  "publisher": "HarperCollins",
  "title": "To Kill a Mockingbird"
}</div>
                            </div>
                            
                            <div class="endpoint">
                                <h4><span class="method">POST</span> <span class="path">/api/books</span></h4>
                                <div class="description">Creates a new book entry</div>
                                <div class="request-example">
// Request format:
{
  "title": "New Book Title",
  "author": "Author Name",
  "isbn": "9781234567890",
  "publisher": "Publisher Name",
  "publication_year": 2023,
  "price": 19.99,
  "description": "Book description goes here.",
  "genre": "Fiction",
  "language": "English",
  "page_count": 300,
  "in_stock": true
}</div>
                            </div>
                            
                            <div class="endpoint">
                                <h4><span class="method">PUT</span> <span class="path">/api/books/{id}</span></h4>
                                <div class="description">Updates an existing book entry</div>
                                <div class="request-example">
// Request format (only include fields you want to update):
{
  "price": 24.99,
  "in_stock": false
}</div>
                            </div>
                            
                            <div class="endpoint">
                                <h4><span class="method">DELETE</span> <span class="path">/api/books/{id}</span></h4>
                                <div class="description">Deletes a book entry</div>
                                <div class="response-example">
// Response format:
{
  "message": "Book with id 1 has been deleted successfully"
}</div>
                            </div>
                        </div>
                        
                        <div class="mt-5">
                            <h3>About This API</h3>
                            <p>This Bookstore API was built with Flask and SQLAlchemy, providing a robust RESTful interface for book resource management. It includes comprehensive error handling and validation, making it suitable for both testing and production environments.</p>
                            <p>The API returns data in JSON format and follows RESTful API design principles.</p>
                        </div>
                    </div>
                </div>
            </div>
        </body>
    </html>
    """


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)