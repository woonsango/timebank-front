import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssActivityPostCardStyle = css`
  padding: 10px 20px;
  border-top: 1px solid ${COMMON_COLOR.FONT1};
  border-bottom: 1px solid ${COMMON_COLOR.FONT1};
  display: flex;
  flex-direction: column;

  .activity-post-card-header {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    gap: 10px;
    margin-bottom: 5px;
    margin-left: -5px;
  }
  .activity-post-card-title {
    font-style: normal;
    font-weight: 700;
    font-size: 15px;
    line-height: 20px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-break: break-all;
  }
  .activity-post-date-time {
    font-style: normal;
    font-weight: 700;
    font-size: 14px;
    line-height: 20px;
    color: ${COMMON_COLOR.FONT2};
  }
  .activity-post-pay {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 5px;
    font-weight: 700;
    font-size: 14px;
    line-height: 20px;
    color: ${COMMON_COLOR.FONT2};
  }
`;
