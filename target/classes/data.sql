-- Sample Book Data for Testing
-- 50 diverse books across different genres, authors, and publication dates

-- Fiction Books
INSERT INTO books (title, author, isbn, publication_date, price, description, page_count, publisher, genre)
VALUES 
('To Kill a Mockingbird', 'Harper Lee', '9780061120084', '1960-07-11', 12.99, 'A classic novel about racial injustice and moral growth in the American South.', 336, 'HarperCollins', 'FICTION'),
('1984', 'George Orwell', '9780451524935', '1949-06-08', 10.99, 'A dystopian novel set in a totalitarian society where critical thought is suppressed.', 328, 'Penguin Books', 'FICTION'),
('Pride and Prejudice', 'Jane Austen', '9780141439518', '1813-01-28', 9.99, 'A romantic novel of manners that satirizes issues of marriage, social class, and gender roles.', 480, 'Penguin Classics', 'FICTION'),
('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', '1925-04-10', 14.99, 'A novel about the decadence and excess of the Jazz Age.', 180, 'Scribner', 'FICTION'),
('One Hundred Years of Solitude', 'Gabriel García Márquez', '9780060883287', '1967-05-30', 15.99, 'A landmark novel that tells the multi-generational story of the Buendía family.', 417, 'Harper Perennial', 'FICTION'),
('The Catcher in the Rye', 'J.D. Salinger', '9780316769488', '1951-07-16', 11.99, 'A story of teenage angst and alienation told by narrator Holden Caulfield.', 277, 'Little, Brown and Company', 'FICTION'),
('Moby-Dick', 'Herman Melville', '9780142437247', '1851-10-18', 13.99, 'The saga of Captain Ahab and his obsessive hunt for the white whale.', 720, 'Penguin Classics', 'FICTION');

-- Science Fiction Books
INSERT INTO books (title, author, isbn, publication_date, price, description, page_count, publisher, genre)
VALUES 
('Dune', 'Frank Herbert', '9780441172719', '1965-08-01', 18.99, 'A science fiction epic set on the desert planet Arrakis.', 896, 'Ace Books', 'SCIENCE_FICTION'),
('Foundation', 'Isaac Asimov', '9780553293357', '1951-05-01', 11.99, 'The first novel in the Foundation series, about the decline and fall of a galactic empire.', 244, 'Bantam Spectra', 'SCIENCE_FICTION'),
('Neuromancer', 'William Gibson', '9780441569595', '1984-07-01', 14.99, 'A groundbreaking cyberpunk novel about a washed-up hacker hired for one last job.', 271, 'Ace Books', 'SCIENCE_FICTION'),
('The Left Hand of Darkness', 'Ursula K. Le Guin', '9780441478125', '1969-03-01', 13.99, 'A science fiction novel exploring themes of gender and politics on an alien world.', 320, 'Ace Books', 'SCIENCE_FICTION'),
('The Hitchhiker''s Guide to the Galaxy', 'Douglas Adams', '9780345391803', '1979-10-12', 12.99, 'A comedic science fiction series following the misadventures of an unwitting human.', 224, 'Del Rey Books', 'SCIENCE_FICTION');

-- Fantasy Books
INSERT INTO books (title, author, isbn, publication_date, price, description, page_count, publisher, genre)
VALUES 
('The Hobbit', 'J.R.R. Tolkien', '9780547928227', '1937-09-21', 14.99, 'A fantasy novel about hobbit Bilbo Baggins and his adventure with a group of dwarves.', 366, 'Mariner Books', 'FANTASY'),
('A Game of Thrones', 'George R.R. Martin', '9780553573404', '1996-08-01', 18.99, 'The first novel in the A Song of Ice and Fire series, a medieval fantasy epic.', 835, 'Bantam Books', 'FANTASY'),
('The Name of the Wind', 'Patrick Rothfuss', '9780756404741', '2007-03-27', 16.99, 'The first day of the autobiography of Kvothe, an adventurer and musician.', 662, 'DAW Books', 'FANTASY'),
('The Lion, the Witch and the Wardrobe', 'C.S. Lewis', '9780064471046', '1950-10-16', 8.99, 'The first book in The Chronicles of Narnia, about children who discover a magical world.', 208, 'HarperCollins', 'FANTASY'),
('Mistborn: The Final Empire', 'Brandon Sanderson', '9780765350381', '2006-07-25', 15.99, 'A fantasy novel about a young street thief who discovers she has magical powers.', 541, 'Tor Books', 'FANTASY');

-- Mystery/Thriller Books
INSERT INTO books (title, author, isbn, publication_date, price, description, page_count, publisher, genre)
VALUES 
('The Girl with the Dragon Tattoo', 'Stieg Larsson', '9780307454546', '2005-08-01', 16.99, 'A mystery novel about a journalist and a hacker investigating a wealthy family.', 672, 'Vintage Crime', 'THRILLER'),
('Gone Girl', 'Gillian Flynn', '9780307588371', '2012-06-05', 15.99, 'A psychological thriller about a woman who disappears on her fifth wedding anniversary.', 432, 'Crown Publishing Group', 'THRILLER'),
('The Da Vinci Code', 'Dan Brown', '9780307474278', '2003-03-18', 14.99, 'A mystery thriller about symbols, codes, and conspiracy theories.', 597, 'Anchor Books', 'THRILLER'),
('And Then There Were None', 'Agatha Christie', '9780062073488', '1939-11-06', 10.99, 'A mystery novel about ten strangers who are lured to an island and start dying one by one.', 256, 'William Morrow Paperbacks', 'MYSTERY'),
('The Silence of the Lambs', 'Thomas Harris', '9780312924584', '1988-05-01', 12.99, 'A psychological horror thriller about an FBI trainee seeking help from an imprisoned serial killer.', 352, 'St. Martin''s Press', 'THRILLER');

-- Romance Books
INSERT INTO books (title, author, isbn, publication_date, price, description, page_count, publisher, genre)
VALUES 
('Outlander', 'Diana Gabaldon', '9780440212560', '1991-06-01', 17.99, 'A historical romance about a time-traveling nurse and a Scottish warrior.', 850, 'Dell Publishing', 'ROMANCE'),
('The Notebook', 'Nicholas Sparks', '9781455582877', '1996-10-01', 13.99, 'A romantic novel about the enduring power of love.', 214, 'Grand Central Publishing', 'ROMANCE'),
('Me Before You', 'Jojo Moyes', '9780143124542', '2012-01-05', 15.99, 'A romance novel about a young woman who becomes a caregiver for a wealthy man.', 369, 'Penguin Books', 'ROMANCE'),
('The Fault in Our Stars', 'John Green', '9780142424179', '2012-01-10', 12.99, 'A young adult romance about two teenagers with cancer who fall in love.', 313, 'Dutton Books', 'YOUNG_ADULT');

-- Non-Fiction Books
INSERT INTO books (title, author, isbn, publication_date, price, description, page_count, publisher, genre)
VALUES 
('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', '9780062316097', '2014-02-10', 24.99, 'A survey of the history of humanity from evolutionary origins to present day.', 443, 'Harper', 'NON_FICTION'),
('Becoming', 'Michelle Obama', '9781524763138', '2018-11-13', 32.50, 'The memoir of former First Lady Michelle Obama.', 448, 'Crown Publishing Group', 'BIOGRAPHY'),
('Educated', 'Tara Westover', '9780399590504', '2018-02-20', 28.00, 'A memoir about a woman who leaves her survivalist family and earns a PhD from Cambridge.', 352, 'Random House', 'BIOGRAPHY'),
('A Brief History of Time', 'Stephen Hawking', '9780553380163', '1988-04-01', 18.00, 'A book about cosmology for non-specialists.', 212, 'Bantam Books', 'SCIENCE'),
('The Power of Habit', 'Charles Duhigg', '9780812981605', '2012-02-28', 17.00, 'A book exploring the science of habit formation and change.', 371, 'Random House', 'SELF_HELP'),
('Thinking, Fast and Slow', 'Daniel Kahneman', '9780374533557', '2011-10-25', 20.00, 'A book about the two systems that drive the way we think.', 499, 'Farrar, Straus and Giroux', 'SCIENCE');

-- History Books
INSERT INTO books (title, author, isbn, publication_date, price, description, page_count, publisher, genre)
VALUES 
('Guns, Germs, and Steel', 'Jared Diamond', '9780393317558', '1997-03-01', 18.95, 'A history of human societies that examines geographical and environmental factors.', 480, 'W. W. Norton & Company', 'HISTORY'),
('1776', 'David McCullough', '9780743226721', '2005-05-24', 20.00, 'A history book about America''s founding year.', 386, 'Simon & Schuster', 'HISTORY'),
('The Diary of a Young Girl', 'Anne Frank', '9780553577129', '1947-06-25', 7.99, 'The writings of a teenage Jewish girl while in hiding during the Nazi occupation.', 283, 'Bantam Books', 'BIOGRAPHY');

-- Business Books
INSERT INTO books (title, author, isbn, publication_date, price, description, page_count, publisher, genre)
VALUES 
('Good to Great', 'Jim Collins', '9780066620992', '2001-10-16', 29.99, 'A management book about how companies transition from being good to great.', 320, 'HarperBusiness', 'BUSINESS'),
('Lean In', 'Sheryl Sandberg', '9780385349949', '2013-03-11', 24.95, 'A book about business leadership and women in the workplace.', 228, 'Knopf', 'BUSINESS');

-- Technology Books
INSERT INTO books (title, author, isbn, publication_date, price, description, page_count, publisher, genre)
VALUES 
('Clean Code', 'Robert C. Martin', '9780132350884', '2008-08-01', 49.99, 'A handbook of agile software craftsmanship.', 464, 'Prentice Hall', 'TECHNOLOGY'),
('The Pragmatic Programmer', 'Andrew Hunt, David Thomas', '9780201616224', '1999-10-30', 44.99, 'A guide about improving the development process in pragmatic, practical ways.', 321, 'Addison-Wesley Professional', 'TECHNOLOGY'),
('Design Patterns', 'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides', '9780201633610', '1994-10-31', 59.99, 'A book presenting a catalog of simple solutions to common software design problems.', 416, 'Addison-Wesley Professional', 'TECHNOLOGY');

-- Children's Books
INSERT INTO books (title, author, isbn, publication_date, price, description, page_count, publisher, genre)
VALUES 
('Harry Potter and the Philosopher''s Stone', 'J.K. Rowling', '9780747532743', '1997-06-26', 10.99, 'The first novel in the Harry Potter series about a young wizard.', 223, 'Bloomsbury', 'CHILDREN'),
('Charlotte''s Web', 'E.B. White', '9780064410939', '1952-10-15', 8.99, 'A children''s novel about the friendship between a pig and a spider.', 192, 'HarperCollins', 'CHILDREN'),
('The Very Hungry Caterpillar', 'Eric Carle', '9780399226908', '1969-06-03', 7.99, 'A classic children''s book about a caterpillar eating through various foods.', 26, 'Philomel Books', 'CHILDREN');

-- Cooking Books
INSERT INTO books (title, author, isbn, publication_date, price, description, page_count, publisher, genre)
VALUES 
('Salt, Fat, Acid, Heat', 'Samin Nosrat', '9781476753836', '2017-04-25', 37.50, 'A cooking book that focuses on the four elements of good cooking.', 480, 'Simon & Schuster', 'COOKING'),
('The Joy of Cooking', 'Irma S. Rombauer', '9781501169717', '1931-11-01', 40.00, 'A comprehensive cookbook that has been a kitchen staple for generations.', 1200, 'Scribner', 'COOKING');
