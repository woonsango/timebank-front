import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssSimplePostCardHeadStyle = css`
  display: flex;
  flex-direction: column;
  gap: 5px;
  padding: 10px 10px 0 10px;
  .tag {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 5px;
    .amount {
      width: max-content;
    }
  }
  .title {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 5px;
    font-weight: 700;
    font-size: 16px;
    line-height: 15px;
    font-style: normal;
    .attachment {
      height: 14px;
    }
  }
`;

export const cssSimplePostCardBodyStyle = css`
  padding: 10px 20px;
  padding-bottom: 30px;
  .post-card-location {
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 20px;
    gap: 6px;
    font-style: normal;
    font-weight: 400;
    font-size: 13px;
    line-height: 20px;
    color: ${COMMON_COLOR.FONT3};
  }
  .post-card-time {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 6px;
    height: 20px;
    font-style: normal;
    font-weight: 400;
    font-size: 13px;
    line-height: 20px;
    color: ${COMMON_COLOR.FONT3};
  }
  .post-card-content {
    font-style: normal;
    font-weight: 400;
    font-size: 12px;
    line-height: 20px;
    margin-top: 6px;
    height: 5em;
    overflow: hidden;
    color: ${COMMON_COLOR.BLACK};
  }
`;

export const cssSimplePostCardFooterStyle = css`
  position: absolute;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  color: ${COMMON_COLOR.FONT3};
  width: 100%;
  bottom: 0;
  height: 70px;
  padding: 40px 10px 20px 10px;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0) 0%, #ffffff 28%);
  font-style: normal;
  font-weight: 400;
  font-size: 13px;
  line-height: 15px;
  .nickname {
    .anticon {
      margin-right: 5px;
    }
  }
  .view-more-container {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, 15%);
    display: flex;
    flex-direction: column;
    align-items: center;
    .view-more-text {
      font-style: normal;
      font-weight: 700;
      font-size: 12px;
      color: ${COMMON_COLOR.FONT2};
    }
    .view-more-icon {
      height: 14px;
      svg {
        width: 10px;
        height: 14px;
      }
    }
  }
`;

export const cssSimplePostCardStyle = css`
  width: calc(100% - 10px);
  margin: 5px;
  .ant-card-body {
    padding: 0px;
    padding-bottom: 20px;
  }
`;
