from flask import Flask, jsonify, redirect, request, render_template_string
import subprocess
import os
import requests
import threading
import time

app = Flask(__name__)

# Flag to track if the Java API is running
java_api_running = False
java_api_process = None

# Template for the homepage
HOME_TEMPLATE = '''
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookstore API</title>
    <link href="https://cdn.replit.com/agent/bootstrap-agent-dark-theme.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Bookstore RESTful API</h1>
        
        <div class="card mb-4">
            <div class="card-header">
                <h2 class="card-title h5 mb-0">API Status</h2>
            </div>
            <div class="card-body">
                <p id="status-message">{{ status_message }}</p>
                <div class="d-flex gap-2">
                    <a href="/swagger-ui" class="btn btn-primary" target="_blank">Open Swagger UI</a>
                    <a href="/api/books" class="btn btn-secondary" target="_blank">View All Books</a>
                    <a href="/h2-console" class="btn btn-info" target="_blank">H2 Database Console</a>
                </div>
            </div>
        </div>
        
        <div class="card">
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
    </div>
    
    <script>
        // Periodically check API status
        function checkApiStatus() {
            fetch('/api-status')
                .then(response => response.json())
                .then(data => {
                    document.getElementById('status-message').textContent = data.message;
                })
                .catch(err => {
                    console.error('Error checking API status:', err);
                });
        }
        
        // Check status every 5 seconds
        setInterval(checkApiStatus, 5000);
    </script>
</body>
</html>
'''

def start_java_api():
    global java_api_running, java_api_process
    
    try:
        # Start the Java API in the workspace directory
        java_api_process = subprocess.Popen(
            ["./mvnw", "spring-boot:run"],
            cwd="workspace",
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT
        )
        
        # Wait for the API to start
        time.sleep(30)
        
        # Check if the API is running
        try:
            response = requests.get("http://localhost:8000/api/books")
            if response.status_code == 200:
                java_api_running = True
                print("Java API started successfully")
            else:
                print(f"Java API responded with status code {response.status_code}")
        except requests.exceptions.ConnectionError:
            print("Failed to connect to Java API")
    except Exception as e:
        print(f"Error starting Java API: {e}")

# Start the Java API in a separate thread
threading.Thread(target=start_java_api, daemon=True).start()

@app.route('/')
def home():
    status_message = "Java API is running" if java_api_running else "Java API is starting up, please wait..."
    return render_template_string(HOME_TEMPLATE, status_message=status_message)

@app.route('/api-status')
def api_status():
    global java_api_running
    
    # Try to connect to the Java API
    try:
        response = requests.get("http://localhost:8000/api/books", timeout=1)
        if response.status_code == 200:
            java_api_running = True
            message = "Java API is running. You can access the API endpoints and Swagger UI."
        else:
            message = f"Java API responded with status code {response.status_code}."
    except requests.exceptions.RequestException:
        java_api_running = False
        message = "Java API is starting up, please wait..."
    
    return jsonify({
        "status": "running" if java_api_running else "starting",
        "message": message
    })

# Proxy routes to the Java API
@app.route('/api/<path:path>', methods=['GET', 'POST', 'PUT', 'DELETE'])
def proxy_api(path):
    if not java_api_running:
        return jsonify({"error": "Java API is not running yet. Please wait."}), 503
    
    url = f"http://localhost:8000/api/{path}"
    try:
        resp = requests.request(
            method=request.method,
            url=url,
            headers={key: value for key, value in request.headers if key != 'Host'},
            params=request.args,
            data=request.get_data(),
            cookies=request.cookies,
            allow_redirects=False,
            timeout=5
        )
        
        excluded_headers = ['content-encoding', 'content-length', 'transfer-encoding', 'connection']
        headers = [(name, value) for name, value in resp.raw.headers.items()
                  if name.lower() not in excluded_headers]
        
        return (resp.content, resp.status_code, headers)
    except requests.exceptions.RequestException as e:
        return jsonify({"error": f"Error connecting to Java API: {str(e)}"}), 503

# Proxy routes for Swagger UI and H2 console
@app.route('/<path:path>')
def proxy_other(path):
    if not java_api_running:
        return jsonify({"error": "Java API is not running yet. Please wait."}), 503
    
    url = f"http://localhost:8000/{path}"
    try:
        resp = requests.get(
            url=url,
            headers={key: value for key, value in request.headers if key != 'Host'},
            params=request.args,
            cookies=request.cookies,
            allow_redirects=False,
            timeout=5
        )
        
        excluded_headers = ['content-encoding', 'content-length', 'transfer-encoding', 'connection']
        headers = [(name, value) for name, value in resp.raw.headers.items()
                  if name.lower() not in excluded_headers]
        
        return (resp.content, resp.status_code, headers)
    except requests.exceptions.RequestException:
        return "Service is starting, please wait or refresh the page.", 503

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)