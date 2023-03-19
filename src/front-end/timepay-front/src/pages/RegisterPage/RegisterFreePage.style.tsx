import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import '../../styles/fonts/pretendard/pretendard.css';

export const cssPostPageStyle = css`
  font-family: unset !important;
  background: ${COMMON_COLOR.WHITE};
  width: 100%;
  height: 100vh;
  display: flex;
  overflow-x: hidden;

  .wrapper {
    height: auto;
    min-height: 100%;
    letter-spacing: 1px;
  }
  .upload {
    margin: 0px 20px 80px 20px;
  }
  h5 {
    font-size: 19px;
    font-weight: 600;
    margin-left: 20px;
    margin-bottom: 15px;
  }
  h6 {
    font-size: 18px;
    font-weight: 500;
    margin-top: 15px;
    margin-left: 20px;
    margin-bottom: 10px;
  }
  p {
    font-size: 16px;
    margin-left: 20px;
  }
`;
export const cssPostTitleInputStyle = css`
  font-size: 25px;
  width: 100%;
  border: none;
  padding-top: 10px;
  padding-bottom: 10px;
  padding-left: 20px;
  padding-right: 20px;
  margin-top: 20px;
  :focus {
    outline: none;
  }
`;
export const cssPostContentInputStyle = css`
  width: 100%;
  font-size: 22px;
  border: none;
  padding-top: 10px;
  padding-bottom: 10px;
  padding-left: 20px;
  padding-right: 20px;
  margin-top: 10px;
  :focus {
    outline: none;
  }
`;

export const cssLineStyle = css`
  border-top: 1px solid ${COMMON_COLOR.FONT1};
  margin-top: 15px;
`;

export const cssPostFooterStyle = css`
  position: fixed;
  width: 100%;
  height: 70px;
  bottom: 0;
  padding: 0;
`;
export const cssPostBtnStyle = css`
  width: 100%;
  height: 70px;
  background: ${COMMON_COLOR.MAIN1};
  color: ${COMMON_COLOR.WHITE};
  border: none;
  letter-spacing: 3px;
  font-size: 24px;
  font-weight: 600;
`;
