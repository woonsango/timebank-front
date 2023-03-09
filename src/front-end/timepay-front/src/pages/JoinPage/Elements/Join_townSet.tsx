import {
  cssJoinStyle_townTitle,
  cssJoinStyle_townInputGu,
  cssJoinStyle_townInputDong,
} from '../../../components/Join/Join.styles';
import { useState } from 'react';
import Join_selectTown from './Join_selectTown';

const Join_townSet = () => {
  const [text, setText] = useState<any>();

  const handleTextChange = (e: any) => {
    setText(e.target.value);
  };

  return (
    <div>
      <h5 css={cssJoinStyle_townTitle}>동네</h5>
      <Join_selectTown></Join_selectTown>
    </div>
  );
};

export default Join_townSet;

//<input value={text} onChange={handleTextChange} css={cssJoinStyle_townInput}/>
