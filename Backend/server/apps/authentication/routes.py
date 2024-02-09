from flask import redirect, url_for
from apps.authentication import blueprint

@blueprint.route('/')
def route_default():
    return redirect(url_for('home_blueprint.index'))