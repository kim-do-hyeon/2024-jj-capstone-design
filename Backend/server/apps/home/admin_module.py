from flask import request, jsonify

''' Import DB '''
from apps import db
from apps.authentication.models import Users, Widget

def admin_module(path_type) :
    if path_type[0] == "management" :
            if path_type[1] == "user" :
                if path_type[2] == "list" :
                    user_data = {}
                    user_list = Users.query.all()
                    for i in user_list :
                        user_data[i.id] = {"username" : i.username, "email" : i.email}
                    return jsonify(result = "success", type = "user_list", message = user_data)
            elif path_type[1] == "widgets" :
                if path_type[2] == "list" :
                    widget_names = {}
                    widget_list = Widget.query.all()
                    for i in widget_list :
                        widget_names[i.id] = i.widget_name
                    return jsonify(result = "success", type = "widget_list", message = widget_names)
                elif path_type[2] == "add" :
                    data = request.args.to_dict()
                    new_widget = Widget(widget_name = data['widget_name'])
                    db.session.add(new_widget)
                    db.session.commit()
                    return jsonify(result = "success", type = "widget_add")
                elif path_type[2] == "delete" :
                    data = request.args.to_dict()
                    delete_widget = Widget.query.filter_by(widget_name = data['widget_name']).first()
                    db.session.delete(delete_widget)
                    db.session.commit()
                    return jsonify(result = "success", type = "widget_delete")


            