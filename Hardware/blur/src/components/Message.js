import React, {useState, useEffect} from "react";
import './Message.css';
import axios from 'axios';

function Message() {
    const [lastestmessage, setLastestMessage] = useState('');

    useEffect ( () => {
        const fetchMessage = async () => {
            try {
              const response = await axios.get('https://jj.system32.kr/get_messages/test');
                if(response.data.result === 'success' && response.data.type === 'messages_retrieved') {
                  const messages = response.data.messages;
                  if(messages.length > 0) {
                    // 최신 메시지의 content를 가져와 상태에 설정합니다.
                    setLastestMessage(messages[0].content);
                  } else {
                    console.error('No messages found.');
                    
                  }
                  } else {
                    console.error('Failed to retrieve messages.');
                  }
                } catch(error) {
                  console.error('Error fetching messages:', error);
                }
        };

        fetchMessage();
    }, []);


  return (
    <div className="message">
      <h2>Latest Message:</h2>
      <p>{lastestmessage}</p>
    </div>
  );
}

export default Message;
