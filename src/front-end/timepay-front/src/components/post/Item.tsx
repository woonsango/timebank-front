import React, { useState } from 'react';
import {
  cssCommentItem,
  cssPostDetailProfile,
  cssEditDelete,
} from './Item.style';

interface ItemProps {
  text: string;
  id: number;
}

const Item = ({ text, id }: ItemProps) => {
  return (
    <>
      <div>
        <div css={cssEditDelete}>
          <div>수정</div>
          <div>삭제</div>
        </div>

        <div css={cssCommentItem}>
          <div css={cssPostDetailProfile}></div>
          <div>{text}</div>
        </div>
      </div>
    </>
  );
};

export default Item;
