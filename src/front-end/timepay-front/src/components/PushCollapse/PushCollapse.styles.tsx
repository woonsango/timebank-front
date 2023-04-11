import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssPushCollapseStyle = css`
  display: flex;
  flex-direction: column;
  gap: 5px;
  margin-top: 5px;
`;
export const cssPushPanelStyle = css`
  &.not-read {
    background-color: ${COMMON_COLOR.LIGHT_YELLOW};
  }
  &.is-already-read {
    background-color: ${COMMON_COLOR.WHITE};
  }
  .ant-collapse-header {
    padding: 12px 30px !important;
  }
  .ant-collapse-header-text {
    width: 100%;
    font-style: normal;
    font-weight: 700;
    font-size: 15px;
    line-height: 20px;
    color: ${COMMON_COLOR.FONT2};
    border-radius: 0 !important;
    .icon {
      margin-right: 10px;
    }
    .title {
      width: 100%;
    }
    .detail-push {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
    }
    .show-detail {
      font-size: 12px;
    }
    .push-header {
      display: flex;
      flex-direction: row;
      width: 100%;
    }
    .comment-preview {
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      word-break: break-all;
      width: 100%;
      display: block;
    }
  }
  .ant-collapse-content-box {
    font-style: normal;
    font-weight: 700;
    font-size: 15px;
    line-height: 20px;
    color: ${COMMON_COLOR.FONT2};
    padding: 12px 30px !important;
  }
  border-top: 1px solid ${COMMON_COLOR.SILVER_GRAY} !important;
  border-bottom: 1px solid ${COMMON_COLOR.SILVER_GRAY} !important;
`;
