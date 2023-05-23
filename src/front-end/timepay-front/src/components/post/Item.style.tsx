import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import '../../styles/fonts/pretendard/pretendard.css';

export const cssComments = css`
  height: 100px;
  margin-bottom: 45px;
  z-index: 1;
`;
export const cssCommentItem = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  border: 1.3px solid ${COMMON_COLOR.LIGHT_GRAY};
  margin-bottom: 15px;
  padding: 15px;
  border-radius: 10px;
  font-size: 18px;
`;
export const cssPostDetailProfile = css`
  display: flex;
  margin-right: 20px;
  margin-bottom: 10px;
  border-radius: 50px;
  align-items: center;
`;
export const cssCommentProfile = css`
  width: 50px;
  height: 50px;
  margin-right: 20px;
  border-radius: 50px;
  background-color: ${COMMON_COLOR.BLUE_GRAY};
`;
export const cssCommentUser = css`
  background: ${COMMON_COLOR.WHITE};
  font-weight: 500;
  font-size: 18px;
`;
export const cssCommentText = css`
  width: 100%;
  margin-top: 10px;
  font-size: 18px;
  font-weight: 400;
  line-height: 25px;
  span {
    white-space: pre-line;
  }
`;

export const cssEditDelete = css`
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
  font-size: 16px;
  .edit {
    margin-right: 10px;
    height: 25px;
    border: none;
    padding: 0;
    font-size: 16px;
  }
  .sidebar {
    margin-bottom: 3px;
  }
  .delete {
    margin-left: 8px;
    margin-right: 5px;
    height: 25px;
    border: none;
    padding: 0;
    font-size: 16px;
  }
`;
