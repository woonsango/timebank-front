import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssHomeHeaderStyle = css`
  position: fixed;
  top: 0;

  width: 100%;
  height: 70px;
  padding: 30px 20px 15px 20px;

  background-color: ${COMMON_COLOR.WHITE};
  border-bottom: 1px solid #cdcdcd;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  z-index: 100;
`;

export const cssHomeHeaderMenuStyle = css`
  font-style: normal;
  font-weight: 700;
  font-size: 15px;
  line-height: 20px;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 5px;
`;

export const cssHomeHeaderNotificationStyle = css`
  font-size: 28px;
  .ant-badge-dot {
    width: 9px;
    height: 9px;
    background: ${COMMON_COLOR.MAIN1};
  }
`;
