import {
  cssJoinStyle_introductionInput,
  cssJoinStyle_introductionTitle,
} from '../../../components/Join/Join.styles';
import { useState } from 'react';

const Join_introductionSet = () => {
  const [text, setText] = useState<any>();

  const handleTextChange = (e: any) => {
    setText(e.target.value);
  };

  return (
    <div>
      <h5 css={cssJoinStyle_introductionTitle}>소개</h5>
      <input
        value={text}
        onChange={handleTextChange}
        css={cssJoinStyle_introductionInput}
      />
    </div>
  );
};

export default Join_introductionSet;
