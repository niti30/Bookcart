from flask import Flask, jsonify, redirect

app = Flask(__name__)

@app.route('/')
def home():
    return redirect('/api-docs', code=302)

@app.route('/api-status')
def api_status():
    return jsonify({"status": "Java API is running on port 8000", "message": "Please access the Bookstore API at /api/books and Swagger UI at /swagger-ui"})

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)