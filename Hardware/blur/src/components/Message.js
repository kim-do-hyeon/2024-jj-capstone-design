import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Message.css';

function Message({ userName }) {
  const [unreadMessages, setUnreadMessages] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);

  useEffect(() => {
    const fetchUnreadMessages = async () => {
      const url = `https://jj.system32.kr/get_unread_messages/${userName}`;
      try {
        const response = await axios.get(url);
        if (response.data.result === 'success' && response.data.messages.length > 0) {
          setUnreadMessages(response.data.messages);
          
          await axios.post(`https://jj.system32.kr/mark_messages_as_read/${userName}`);
        } else {
          console.error('No unread messages found or failed to retrieve messages.');
          setUnreadMessages([]);
        }
      } catch (error) {
        console.error('Error fetching unread messages:', error);
        setUnreadMessages([]);
      }
    };

    fetchUnreadMessages();
  }, [userName]);

  useEffect(() => {
    if (unreadMessages.length > 0) {
      const interval = setInterval(() => {
        setCurrentPage(prevPage => (prevPage + 1) % Math.ceil(unreadMessages.length / 3));
      }, 10000);
      return () => clearInterval(interval);
    }
  }, [unreadMessages]);

  const startIndex = currentPage * 3;
  const currentMessages = unreadMessages.slice(startIndex, startIndex + 3);

  return (
    <div className="message">
      {currentMessages.length > 0 ? (
        currentMessages.map((message, index) => (
          <p key={index}>
            <strong>From :</strong> <span>{message.sender}</span> - <span>{message.content}</span>
          </p>
        ))
      ) : (
        <p>No unread messages available.</p>
      )}
    </div>
  );
}

export default Message;