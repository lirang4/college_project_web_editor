from flask_restful import Resource, reqparse, abort
from pymongo import MongoClient
from bson.json_util import dumps

client = MongoClient('localhost', 27030)
db = client.test                            
mydb = client["WebEditor"]
mycol = mydb["users"]

parser = reqparse.RequestParser()
parser.add_argument('username')
parser.add_argument('password')
parser.add_argument('email')
parser.add_argument('firstName')
parser.add_argument('lastName')


def abort_if_user_doesnt_exist(id):
    result = mycol.find_one({"username" : id})
    if result is None:
        abort(404, message="User {0} doesn't exist".format(id))


class User(Resource):
    def get(self, id):
        abort_if_user_doesnt_exist(id)
        return dumps(mycol.find_one({"username" : id}))

    def put(self, id):
        abort_if_user_doesnt_exist(id)
        args = parser.parse_args()
        mycol.find_one_and_update({"username" : id}, {"$set": args})

        return '', 201

    def delete(self, id):
        abort_if_user_doesnt_exist(id)
        mycol.delete_one({"username" : id})

        return '', 204


class UsersList(Resource):
    def get(self):
        return dumps(mycol.find({}))

    def post(self):
        args = parser.parse_args()
        mycol.insert_one(args)

        return '', 201
