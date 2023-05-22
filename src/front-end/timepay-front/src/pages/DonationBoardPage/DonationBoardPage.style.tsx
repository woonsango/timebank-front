import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssDonationBoardPageStyle = () => css`
  padding: 20px;
  .board-header {
    display: flex;
    flex-direction: row;

    justify-content: space-between;
    .default {
      display: flex;
      flex-direction: row;
      gap: 10px;
    }
    .mine {
      display: flex;
      flex-direction: row;
      gap: 5px;
      .ant-btn {
        font-size: 12px;
        padding: 4px 10px;
      }
    }
  }
  .title-container {
    font-size: 20px;
    font-weight: 700;
    padding: 10px 0;
  }
  .donation-info-container {
    border-radius: 5px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    color: ${COMMON_COLOR.FONT2};
  }
  .organization-info-container {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    flex-direction: row;
    margin-bottom: 10px;
    gap: 5px;
    padding-right: 10px;
    font-size: 16px;
    img {
      width: 25px;
      height: 25px;
      border-radius: 20px;
    }
  }
  .content-container {
    border: 1px solid ${COMMON_COLOR.FONT1};
    padding: 15px;
    min-height: 400px;
    margin-bottom: 40px;
    word-break: break-all;
  }
  .ant-btn {
    width: 100%;
    font-size: 16px;
    font-weight: 700;
    height: max-content;
  }
`;
