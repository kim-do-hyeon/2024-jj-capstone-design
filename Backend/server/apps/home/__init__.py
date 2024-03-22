from flask import Blueprint
from flask_cors import CORS

blueprint = Blueprint(
    'home_blueprint',
    __name__,
    url_prefix=''
)

CORS(blueprint, resources={r"/*": {"origins": "http://localhost:3000"}})