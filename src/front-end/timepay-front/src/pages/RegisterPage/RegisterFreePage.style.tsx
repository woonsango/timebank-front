import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import '../../styles/fonts/pretendard/pretendard.css';

export const cssPostPageStyle = css`
  background: ${COMMON_COLOR.WHITE};
  width: 100%;
  display: flex;
  flex-direction: column;
  overflow-x: hidden;
  padding: 0;
  margin: 0;

  .ant-form-item-row {
    flex-direction: column;
  }
  .time {
    display: flex;
    flex-direction: row;
  }
`;

export const cssPostCategoryStyle = css`
  margin-top: 180px;
  margin-left: 20px;
`;

export const cssPostDateStyle = css`
  margin-top: 10px;
  margin-left: 20px;
`;

export const cssPostTitleStyle = css`
  font-size: 22px;
  margin-left: 20px;
  border: none;
  :focus {
    outline: none;
  }
`;
export const cssPostTitleInputStyle = css`
  font-size: 22px;
  border: none;
  padding-right: 20px;
  :focus {
    outline: none;
  }
`;
export const cssPostContentStyle = css`
  font-size: 18px;
  border: none;
  margin-left: 20px;
  padding-bottom: 10px;
  padding-right: 20px;
  :focus {
    outline: none;
  }
`;
export const cssPostContentInputStyle = css`
  font-size: 18px;
  border: none;
  padding-bottom: 10px;
  padding-right: 20px;
  :focus {
    outline: none;
  }
`;

export const cssLineStyle = css`
  width: 100%;
  border-top: 1px solid ${COMMON_COLOR.FONT1};
  margin-top: 15px;
  margin-bottom: 15px;
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
export const cssPostBtnStyle2 = css`
  width: 100%;
  height: 70px;
  background: ${COMMON_COLOR.LIGHT_GRAY};
  color: ${COMMON_COLOR.WHITE};
  border: none;
  letter-spacing: 3px;
  font-size: 24px;
  font-weight: 600;
  opacity: 100;
`;
