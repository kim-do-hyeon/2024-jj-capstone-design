# -*- encoding: utf-8 -*-
import json
from flask import request, jsonify, session

''' Import DB '''
from apps import db
from apps.authentication.models import Widget, CustomLocation, Production, Todo

def widgets_module() :
    try :
        widget_names = {}
        widget_list = Widget.query.all()
        for i in widget_list :
            widget_names[i.id] = i.widget_name
        return jsonify(result = "success", type = "widget_list", message = widget_names)
    except Exception as e :
        return jsonify(result = "fail", type = "Logic Error", message = "Error : {}".format(str(e)))

def widgets_custom_module() :
    try :
        data = request.args.to_dict()
        location = data['index']
        model_code = data['model_code']
        try :
            if (CustomLocation.query.filter_by(username = session['username'], model_code = str(model_code)).first()) == None :
                new_data = CustomLocation(username = session['username'], model_code = str(model_code), index = str(location))
                db.session.add(new_data)
                db.session.commit()
                return jsonify(result = "success", type = "widget_custom", message = "New Widget")
            else :
                CustomLocation.query.filter_by(username = session['username'], model_code = str(model_code)).update(dict(index = str(location)))
                db.session.commit()
                return jsonify(result = "success", type = "widget_custom", message = "Update Widget")
        except Exception as e :
            return jsonify(result = "fail", type = "widget_custom", message = "Fail : {}".format(str(e)))
    except Exception as e :
        return jsonify(result = "fail", type = "Logic Error", message = "Error : {}".format(str(e)))

def get_widgets_custom_module(subpath) :
    try :
        data = subpath.split("=")
        username = data[-1]
        # model_code = data['model_code']
        model_code = "1234-5678" # Temporary Code
        user_data = CustomLocation.query.filter_by(username = username, model_code = str(model_code)).first()
        if user_data != None :
            user_widgets = json.loads(user_data.index)
            return jsonify(result = "success", type = "get_widgets_custom", message = user_widgets)
        else :
            return jsonify(result = "fail", type = "get_widgets_custom", message = "Empty Database")
    except Exception as e :
        return jsonify(result = "fail", type = "Logic Error", message = "Error : {}".format(str(e)))

def get_model_user_list_module() :
    try :
        data = request.args.to_dict()
        model_code = data['code']
        user_list = Production.query.filter_by(code = model_code).all()
        user_names = []
        for i in user_list :
            user_names.append(i.username)
        if len(user_list) != 0 :
            return jsonify(result = "success", type = "model_user_list", message = str(user_names))
        else :
            return jsonify(result = "success", type = "model_user_list", message = "Empty Model User List")
    except Exception as e :
        return jsonify(result = "fail", type = "Logic Error", message = "Error : {}".format(str(e)))

def get_guset_widgets_index_module() :
    try :
        return jsonify(result = "success",
                    message = {'Weather' : [1, 1],
                                'DateTime' : [1, 2],
                                'Login' : [1, 3],
                                'CheerUp' : [1, 4],
                                'Finance' : [2, 1],
                                })
    except Exception as e :
        return jsonify(result = "fail", type = "Logic Error", message = "Error : {}".format(str(e)))

def daily_module(subpath) :
    data = request.args.to_dict()
    username = data['username']
    if subpath == "add" :
        try :
            new_todo = Todo(username = username,
                            localdate = data['localdate'],
                            message = data['message'],
                            complete = 0)
            db.session.add(new_todo)
            db.session.commit()
            return jsonify(result = "success", type = "todo_add", message = "Successful save Todo")
        except Exception as e :
            return jsonify(result = "fail", type = "todo_add", message = "Error " + str(e))
    elif subpath == "view" :
        try :
            todo_lists = []
            todo_datas = Todo.query.filter_by(username = username, localdate = data['localdate']).all()
            for i in todo_datas :
                todo_lists.append([i.id, i.localdate, i.message, i.complete])
            return jsonify(result = "success", type = "todo_view", message = str(todo_lists))
        except Exception as e :
            return jsonify(result = "fail", type = "todo_view", message = "Error " + str(e))
    elif subpath == "check" :
        try :
            if Todo.query.filter_by(id = data['id']).first().complete == 0 :
                Todo.query.filter_by(id = data['id']).update(dict(complete = 1))
            else :
                Todo.query.filter_by(id = data['id']).update(dict(complete = 0))
            db.session.commit()
            return jsonify(result = "success", type = "todo_check", message = "Successful save Todo")
        except Exception as e :
            return jsonify(result = "fail", type = "todo_check", message = "Error " + str(e))
    elif subpath == "month" :
        try :
            filter_month = data['year'] + data['month'].zfill(2)
            filtered_todos = Todo.query.filter(
                Todo.localdate.like(f"%{filter_month}%"),
                Todo.username == username
            ).all()
            print(filtered_todos)
            datas = {}
            for i in filtered_todos :
                if i.localdate in datas :
                    datas[i.localdate] += 1
                else :
                    datas[i.localdate] = 1
            return jsonify(result = "success", type = "todo_month", message = str(datas))
        except :
            return jsonify(result = "fail", type = "todo_month", message = "Error" + str(e))