import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssHomePageStyle = css`
  display: flex;
  flex-direction: column;
  .title-search-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 30px;
    background-color: ${COMMON_COLOR.LOGO1};
    padding-top: 100px;
    padding-bottom: 36px;
    .title-search {
      display: flex;
      flex-direction: row;
      gap: 5px;
      .ant-btn {
        border-color: ${COMMON_COLOR.WHITE};
        color: ${COMMON_COLOR.WHITE};
        font-style: normal;
        font-weight: 700;
        font-size: 18px;
        line-height: 15px;
      }
    }
  }
  .category-search-container {
    padding-top: 45px;
    padding-bottom: 22px;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 20px;

    font-style: normal;
    font-weight: 700;
    font-size: 18px;
    line-height: 15px;
    color: ${COMMON_COLOR.FONT3};
  }
  .info-container {
    font-style: normal;
    font-weight: 700;
    font-size: 14px;
    line-height: 20px;
    color: #c1c1c1;
    width: 290px;
    margin: auto;
    word-break: keep-all;
    padding-bottom: 20px;
  }
`;

export const cssCategoryListStyle = css`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
  width: 290px;
  .ant-btn {
    width: 140px;
    height: 56px;
    font-style: normal;
    font-weight: 400;
    font-size: 15px;
    line-height: 20px;
    color: ${COMMON_COLOR.FONT2};
  }
`;
