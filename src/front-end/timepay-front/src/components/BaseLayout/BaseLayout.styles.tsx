import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import '../../styles/fonts/pretendard/pretendard.css';

export const cssBaseLayoutStyle = css`
  font-family: unset !important;
  background: ${COMMON_COLOR.WHITE};
  overflow-x: hidden;
  width: 100%;
  main {
    font-family: unset !important;
    &.show-header {
      padding-top: 70px; // 헤더에 가려진만큼 패딩 추가
    }
    &.show-search-header {
      padding-top: 134px;
    }
    padding-bottom: 80px;
    overflow-y: scroll;
  }
`;

export const cssHomeHeaderNotificationStyle = css`
  font-size: 28px;
  margin-top: 10px;
  margin-left: auto;
  margin-right: 10px;
  margin-bottom: 5px;
  color: ${COMMON_COLOR.FONT2};
  .ant-badge-dot {
    width: 9px;
    height: 9px;
    background: ${COMMON_COLOR.MAIN1};
  }
`;
