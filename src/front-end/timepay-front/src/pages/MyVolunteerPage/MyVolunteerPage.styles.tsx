import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssMyVolunteerPageStyle = css`
  padding: 20px;
  .volunteer-overview {
    font-style: normal;
    font-weight: 700;
    font-size: 15px;
    line-height: 15px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    .title {
      color: ${COMMON_COLOR.MAIN2};
    }
    /* padding-bottom: 20px; */
  }
  .ant-table {
    font-size: 16px;
    .ant-table-cell {
      word-break: keep-all;
    }
  }
  .agency-info {
    color: ${COMMON_COLOR.FONT2};
    font-size: 14px;
  }
  .ant-btn-link {
    padding: 0;
    height: max-content;
    color: ${COMMON_COLOR.MAIN2};
    font-weight: bold;
    font-size: 16px;
    span {
      text-decoration: underline;
    }
    border-radius: 0;
  }
  .ant-spin-container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .volunteer-extra {
    color: ${COMMON_COLOR.FONT2};
    font-size: 14px;
    margin-top: 10px;
  }
`;
