import {
  cssJoinStyle_nicknameTitle,
  cssJoinStyle_nicknameInput,
} from '../../../components/Join/Join.styles';
import { useState } from 'react';

const Join_nicknameSet = () => {
  const [text, setText] = useState<any>();

  const handleTextChange = (e: any) => {
    setText(e.target.value);
  };

  return (
    <div>
      <h5 css={cssJoinStyle_nicknameTitle}>닉네임</h5>
      <input
        value={text}
        onChange={handleTextChange}
        css={cssJoinStyle_nicknameInput}
      />
    </div>
  );
};

export default Join_nicknameSet;
