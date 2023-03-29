import React from 'react';
import { cssPostTextarea } from '../../pages/PostPage/PostPage.style';

interface InputTextProps {
  onChange(e: React.ChangeEvent<HTMLTextAreaElement>): void;

  inputText: string;
}

const InputText = ({ onChange, inputText }: InputTextProps) => {
  return (
    <>
      <textarea
        placeholder="댓글 입력 창 입니다 :)"
        onChange={(e) => onChange(e)}
        value={inputText}
        css={cssPostTextarea}
        style={{
          padding: 10,
          height: 80,
          width: '90%',
          fontSize: 20,
          resize: 'none',
        }}
      />
    </>
  );
};

export default InputText;
