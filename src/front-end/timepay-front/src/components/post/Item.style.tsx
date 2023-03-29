import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import '../../styles/fonts/pretendard/pretendard.css';

export const cssCommentItem = css`
  display: flex;
  flex-direction: row;
  width: 100%;
  background-color: ${COMMON_COLOR.FONT1};
  margin-bottom: 15px;
  padding: 15px;
  border-radius: 10px;
  font-size: 18px;
  align-items: center;
`;
export const cssPostDetailProfile = css`
  justify-content: flex-start;
  width: 50px;
  height: 50px;
  margin-right: 20px;
  border-radius: 50px;
  background-color: ${COMMON_COLOR.BLUE_GRAY};
`;

export const cssEditDelete = css`
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
`;
