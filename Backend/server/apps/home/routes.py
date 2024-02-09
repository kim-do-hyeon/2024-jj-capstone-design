# -*- encoding: utf-8 -*-
from flask import render_template, redirect, request, send_file
from flask_login import login_required, logout_user

''' Import Apps Module '''
from apps.home import blueprint

@blueprint.route('/index')
def index():
    return "INDEX"

@blueprint.route('/register')
def register() :
    return "A"
