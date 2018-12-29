from flask import Blueprint, Flask
from flask_restful import Api, Resource, url_for

from resources.users import User, UsersList

app = Flask(__name__)
api_bp = Blueprint('api', __name__)
api = Api(api_bp)

api.add_resource(UsersList, '/users')
api.add_resource(User, '/users/<string:id>')

app.register_blueprint(api_bp)

if __name__ == '__main__':
    app.run(debug=True)
