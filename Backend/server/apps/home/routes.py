# -*- encoding: utf-8 -*-
from flask import jsonify, session, send_file

''' Import Apps Module '''
from apps.home import blueprint

from apps.home.user_module import *
from apps.home.admin_module import *
from apps.home.face_module import *
from apps.home.widgets_module import *
from apps.home.message_module import *


@blueprint.route('/index')
def index():
    return ""

''' Start User Section '''
@blueprint.route('/register/<path:subpath>', methods = ['GET', 'POST'])
def register(subpath) :
    path_type = subpath.split("/")
    return register_module(path_type)

@blueprint.route('/login', methods = ['GET', 'POST'])
def login() :
    return login_module()

@blueprint.route('/reset_password', methods = ['GET', 'POST'])
def reset_password() :
    return reset_password_module()

@blueprint.route('/change_password', methods = ['GET', 'POST'])
def change_password() :
    return change_password_module()

@blueprint.route('/change_profile', methods = ['GET', 'POST'])
def change_profile():
    return chagne_profile_module()

@blueprint.route('/get_user_info', methods = ['GET', 'POST'])
def get_user_info() :
    return get_user_info_module()

@blueprint.route('/download_image/<path:subpath>')
def download_image(subpath) :
    upload_dir = "../upload/profile_image/"
    PATH = upload_dir + subpath
    return send_file(PATH, as_attachment=True)
''' End User Section '''

''' Start Face Section '''
@blueprint.route('/face', methods = ['GET', 'POST'])
def face() :
    return redirect_face_module()
    
@blueprint.route("/distance", methods = ['GET', 'POST'])
def distnace() :
    return distance_module()

@blueprint.route("/personal_color", methods = ['GET', 'POST'])
def personal_color() :
    return personal_color_module()
''' End Face Section '''

''' Start Admin Section '''
@blueprint.route('/admin/<path:subpath>', methods = ['GET', 'POST'])
def admin(subpath) :
    path_type = subpath.split("/")
    return admin_module(path_type)

@blueprint.route('/session_test', methods=['POST', 'GET'])
def session_test() :
    if session['username'] :
        return jsonify(result = "Success", type = "session test", message = "Loggined")
    else :
        return jsonify(result = "Fail", type = "session test", message = "Not Loggined")
''' End Admin Section '''

''' Start Widgets Section '''
@blueprint.route('/widgets')
def widgets():
    return widgets_module()
    
@blueprint.route('/widgets_custom', methods = ['GET', 'POST'])
def widgets_custom() :
    return widgets_custom_module()

@blueprint.route('/get_widgets_custom/<path:subpath>', methods = ['GET', 'POST'])
def get_widgets_custom(subpath) :
    return get_widgets_custom_module(subpath)

@blueprint.route('/get_model_user_list', methods = ['GET', 'POST'])
def model_user_list() :
    return get_model_user_list_module()
    
@blueprint.route('/widgets_index', methods = ['GET', 'POST'])
def widgets_index() :
    return get_guset_widgets_index_module()
''' End Widgets Section '''

''' Start Message Section '''
@blueprint.route('/send_message', methods=['POST'])
def send_message():
    return send_message_module()

@blueprint.route('/get_messages/<receiver_username>', methods=['GET'])
def get_messages(receiver_username):
    return get_message_module(receiver_username)

@blueprint.route('/get_unread_messages/<receiver_username>', methods=['GET'])
def get_unread_messages(receiver_username):
    return get_unread_message_module(receiver_username)

@blueprint.route('/mark_messages_as_read/<receiver_username>', methods=['POST'])
def mark_messages_as_read(receiver_username):
    return mark_messages_as_read_module(receiver_username)
''' End Message Section '''

''' Daily TODO Section '''
@blueprint.route('/daily/<path:subpath>', methods = ['GET', 'POST'])
def daily(subpath) :
    return daily_module(subpath)