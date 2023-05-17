import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssActivityCommentCardStyle = css`
  padding: 10px 20px;
  border-top: 1px solid ${COMMON_COLOR.FONT1};
  border-bottom: 1px solid ${COMMON_COLOR.FONT1};
  display: flex;
  flex-direction: column;
  background-color: white;
  margin-bottom: 5px;
  .activity-comment-content {
    font-style: normal;
    font-weight: 700;
    font-size: 15px;
    line-height: 20px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    word-break: break-all;
    margin-bottom: 15px;
  }
  .activity-origin-post-title {
    font-style: normal;
    font-weight: 700;
    font-size: 14px;
    line-height: 20px;
    color: ${COMMON_COLOR.FONT2};

    display: flex;
    flex-direction: row;
    gap: 5px;
  }
  .activity-comment-date-time {
    font-style: normal;
    font-weight: 700;
    font-size: 14px;
    line-height: 20px;
    color: ${COMMON_COLOR.FONT2};
  }
`;
