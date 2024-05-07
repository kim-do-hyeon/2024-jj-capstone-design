# -*- encoding: utf-8 -*-
from flask import request, jsonify
from datetime import datetime, timezone

''' Import DB '''
from apps import db
from apps.authentication.models import Users, Message

def send_message_module() :
    data = request.json
    sender = Users.query.filter_by(username=data['sender_username']).first()
    receiver = Users.query.filter_by(username=data['receiver_username']).first()
    
    if sender and receiver:
        new_message = Message(
            sender_id=sender.id,
            receiver_id=receiver.id,
            content=data['content'],
            timestamp=datetime.now(timezone.utc)
        )
        db.session.add(new_message)
        db.session.commit()
        return jsonify(result="success", type="message_sent", message="Message sent"), 200
    else:
        return jsonify(result="fail", type="user_not_found", message="Sender or Receiver not found"), 404

def get_message_module(receiver_username) :
    receiver = Users.query.filter_by(username=receiver_username).first()
    
    if receiver:
        # messages = Message.query.filter_by(receiver_id=receiver.id, is_read=False).all()
        messages = Message.query.filter_by(receiver_id=receiver.id).all()
        messages_data = [{
            'sender': Users.query.get(m.sender_id).username, 
            'content': m.content, 
            'timestamp': m.timestamp.strftime("%Y-%m-%d %H:%M:%S")
        } for m in messages]
        
        # for message in messages:
        #     message.is_read = True
        # db.session.commit()

        return jsonify(result="success", type="messages_retrieved", messages=messages_data), 200
    else:
        return jsonify(result="fail", type="receiver_not_found", message="Receiver not found"), 404
