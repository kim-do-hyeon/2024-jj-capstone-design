import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Message.css';

function Message({ userName }) {
  const [latestMessage, setLatestMessage] = useState('');
  const [sender, setSender] = useState('');

  useEffect(() => {
    const fetchMessage = async () => {
      const url = `https://jj.system32.kr/get_messages/${userName}`;
      try {
        const response = await axios.get(url);
        if (response.data.result === 'success' && response.data.messages.length > 0) {
          const lastMessageIndex = response.data.messages.length - 1;
          const lastMessage = response.data.messages[lastMessageIndex];
          setLatestMessage(lastMessage.content);
          setSender(lastMessage.sender);
        } else {
          console.error('No messages found or failed to retrieve messages.');
          setLatestMessage("No messages available.");
          setSender('');
        }
      } catch (error) {
        console.error('Error fetching messages:', error);
        setLatestMessage("Error in fetching messages.");
        setSender('');
      }
    };

    fetchMessage();
  }, [userName]);

  return (
    <div className="message">
      <p><strong>From :</strong> <span>{sender}</span>&nbsp;-&nbsp;<span>{latestMessage || "No messages available."}</span></p>
    </div>
  );
}

export default Message;