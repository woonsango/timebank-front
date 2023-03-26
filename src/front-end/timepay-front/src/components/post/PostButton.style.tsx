import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import '../../styles/fonts/pretendard/pretendard.css';

export const cssPostButtons = css`
  display: flex;
  justify-content: center;
`;

export const cssPostButton = css`
  height: 50px;
  margin-top: 7px;
  margin-bottom: 7px;
  font-size: 20px;
  font-weight: 600;
  letter-spacing: 2.3px;
  border-radius: 5px;
  border: none;
  color: ${COMMON_COLOR.WHITE};
  &.start {
    width: 95%;
    background-color: #ffa621;
  }
  &.delayed {
    width: 65%;
    background-color: #ff7a00;
  }
  &.completed {
    width: 65%;
    background-color: #ff4910;
  }
  &.end {
    width: 65%;
    background-color: #6c6363;
  }
  &.pause {
    width: 65%;
    background-color: #6c6363;
  }
  &.review {
    width: 95%;
    background-color: ${COMMON_COLOR.NEON_GREEN};
  }
  &.theEnd {
    width: 95%;
    color: ${COMMON_COLOR.FONT3};
    background-color: ${COMMON_COLOR.WHITE};
    font-weight: 400;
    letter-spacing: 1.5px;
  }
  &.goBack {
    width: 30%;
    margin-right: 10px;
    background-color: ${COMMON_COLOR.FONT4};
  }
`;
