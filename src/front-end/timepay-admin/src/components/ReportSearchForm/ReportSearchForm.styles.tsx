import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssReportSearchFormStyle = css`
  padding: 20px;
  border: 1px solid #cdcdcd;
  border-radius: 5px;
  width: 100%;
  .ant-form {
    display: flex;
    flex-direction: column;
  }
  .ant-row {
    width: 100%;
    gap: 10px;
  }
  .ant-select {
    width: 100%;
    max-width: 120px;
  }
  .ant-input {
    width: 100%;
    max-width: 250px;
  }
  .ant-input-number {
    width: 100%;
    max-width: 150px;
  }
  .ant-btn {
    width: 100%;
    max-width: 100px;
    background-color: ${COMMON_COLOR.MAIN1};
  }
`;
