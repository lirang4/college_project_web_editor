import uuid
from flask_restful import Resource, reqparse, abort

USERS = {}

parser = reqparse.RequestParser()
parser.add_argument('username')
parser.add_argument('password')
parser.add_argument('email')
parser.add_argument('firstName')
parser.add_argument('lastName')


def abort_if_user_doesnt_exist(id):
    if id not in USERS:
        abort(404, message="User {0} doesn't exist".format(id))


class User(Resource):
    def get(self, id):
        abort_if_user_doesnt_exist(id)
        return USERS[id]

    def put(self, id):
        abort_if_user_doesnt_exist(id)
        args = parser.parse_args()
        USERS[id] = args
        return USERS[id], 201

    def delete(self, id):
        abort_if_user_doesnt_exist(id)
        del USERS[id]
        return '', 204


class UsersList(Resource):
    def get(self):
        return USERS

    def post(self):
        args = parser.parse_args()
        id = uuid.uuid1()
        args[id] = id
        USERS[id] = args

        return USERS[id], 201
