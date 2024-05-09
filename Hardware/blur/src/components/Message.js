import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Message.css';

function Message({ userName }) {
  const [latestMessage, setLatestMessage] = useState('');

  useEffect(() => {
    const fetchMessage = async () => {
      const url = `https://jj.system32.kr/get_messages/${userName}`;
      try {
        const response = await axios.get(url);
        if (response.data.result === 'success' && response.data.messages.length > 0) {
          const lastMessageIndex = response.data.messages.length - 1;
          setLatestMessage(response.data.messages[lastMessageIndex].content);
        } else {
          console.error('No messages found or failed to retrieve messages.');
          setLatestMessage("No messages available.");
        }
      } catch (error) {
        console.error('Error fetching messages:', error);
        setLatestMessage("Error in fetching messages.");
      }
    };

    fetchMessage();
  }, [userName]);

  return (
    <div className="message">
      <h2>Latest Message:</h2>
      <p>{latestMessage || "No messages available."}</p>
    </div>
  );
}

export default Message;