import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import '../../styles/fonts/pretendard/pretendard.css';

export const cssPostDetailPage = css`
  display: flex;
  flex-direction: column;
  margin-top: 70px;
`;

// 1st block
export const cssPostDetailFirst = css`
  flex-direction: row;
  margin-top: 10px;
  margin-bottom: 15px;
  margin-left: 25px;
  margin-right: 25px;
  font-size: 18px;
`;
export const cssPostDetailProfile = css`
  display: flex;
  justify-content: flex-start;
  width: 50px;
  height: 50px;
  margin-top: -10px;
  border-radius: 25px;
  background-color: ${COMMON_COLOR.BLUE_GRAY};
`;
export const cssPostDetailUser = css`
  margin-top: -35px;
  margin-left: 65px;
  font-weight: 500;
`;
export const cssPostDetailCreatedAt = css`
  display: flex;
  justify-content: flex-end;
  font-size: 15px;
`;

// 2nd block
export const cssPostDetailSecond = css`
  margin-left: 25px;
  margin-right: 25px;
  display: flex;
  justify-content: space-between;
`;
export const cssPostDetailTitle = css`
  margin-top: 25px;
  font-size: 22px;
  font-weight: 500;
`;
export const cssPostDetailStatus = css`
  margin-top: 25px;
`;

// 3rd block
export const cssPostDetailThird = css`
  display: grid;
  flex-direction: row;
  grid-template-columns: 85px 5fr 68px;
  align-items: center;
  margin-left: 25px;
  margin-right: 25px;
`;
export const cssPostDetailCategory1 = css`
  margin-top: 20px;
  font-size: 18px;
  font-weight: 500;
`;
export const cssPostDetailCategory2 = css`
  margin-top: 20px;
  font-size: 16px;
  font-weight: 700;
  color: ${COMMON_COLOR.FONT2};
`;
export const cssPostDetailPay = css`
  margin-top: 20px;
  font-size: 18px;
  font-weight: 500;
  background-color: ${COMMON_COLOR.MAIN3};
  border-radius: 10px;
  padding: 5px;
`;

// 4th block
export const cssPostDetailFourth = css`
  margin-left: 25px;
  margin-right: 25px;
`;
export const cssPostDetailRegion = css`
  margin-top: 20px;
  color: ${COMMON_COLOR.FONT2};
  font-size: 16px;
  font-weight: 600;
`;
export const cssPostDetailTime = css`
  margin-top: 10px;
  color: ${COMMON_COLOR.FONT2};
  font-size: 16px;
  font-weight: 600;
`;

// 5th block
export const cssPostDetailFifth = css`
  margin-left: 25px;
  margin-right: 25px;
`;
export const cssPostDetailContent1 = css`
  margin-top: 20px;
  font-size: 18px;
  font-weight: 500;
`;
export const cssPostDetailContent2 = css`
  margin-top: 10px;
  padding: 10px;
  border: 1.3px solid ${COMMON_COLOR.FONT1};
  border-radius: 10px;
  background-color: ${COMMON_COLOR.GRAY};
  font-size: 18px;
  letter-spacing: 1.3px;
`;
export const cssPostDetailAttachment = css``;

// lines
export const cssLine1 = css`
  border-top: 1.5px solid ${COMMON_COLOR.FONT1};
  background-color: black;
  margin-top: 15px;
`;
export const cssLine3 = css`
  border-top: 1.5px solid ${COMMON_COLOR.FONT1};
  background-color: black;
  margin-top: 20px;
  margin-left: 25px;
  margin-right: 25px;
`;
export const cssLine4 = css`
  border-top: 5px solid ${COMMON_COLOR.FONT1};
  background-color: black;
  margin-top: 25px;
`;
