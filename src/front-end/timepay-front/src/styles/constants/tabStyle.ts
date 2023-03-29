import { css } from '@emotion/react';
import { COMMON_COLOR } from './colors';

export const cssTabStyle = css`
  .ant-tabs-nav {
    margin-bottom: 0px;
    border-bottom: 1px solid #cdcdcd;
  }
  .ant-tabs-tab {
    padding: 10px 30px;
  }
  .ant-tabs-tab-btn {
    font-style: normal;
    font-weight: 700;
    font-size: 16px;
    line-height: 20px;
    color: ${COMMON_COLOR.FONT2} !important;
  }
  .ant-tabs-tab-active {
    .ant-tabs-tab-btn {
      color: ${COMMON_COLOR.MAIN1} !important;
    }
  }
  .ant-tabs-ink-bar {
    background-color: ${COMMON_COLOR.MAIN1};
    height: 5px;
  }

  .ant-tabs-content-holder {
    min-height: calc(100vh - 190px); // 실제 화면 높이 - (푸터, 헤더, 탭 높이)
    background-color: #f7f7f7;
    padding-bottom: 10px;
  }
`;
