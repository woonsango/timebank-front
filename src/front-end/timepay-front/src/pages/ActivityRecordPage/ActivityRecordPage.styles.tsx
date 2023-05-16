import { css } from '@emotion/react';

export const cssActivityRecordPageStyle = css`
  .total-count {
    text-align: right;
  }
  .ant-pagination {
    text-align: center;
  }
`;

export const cssHorizontalForm = css`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 5px;
  padding: 5px;
  justify-content: space-between;
  /* padding-right: 5px; */
  .ant-select {
    padding-right: 5px;
  }
`;

export const cssNothingStyle = css`
  display: flex;
  flex-direction: column;
  .emoji {
    font-size: 25px;
  }
  text-align: center !important;
  padding-top: 250px;
  width: 100%;
`;

export const cssSpinStyle = css`
  text-align: center !important;
  padding-top: 280px !important;
  width: 100%;
`;
