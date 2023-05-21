import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import '../../styles/fonts/pretendard/pretendard.css';

export const cssQnaDeleteStyle = css`
  text-align: right;
  margin-right: 25px;
`;
export const cssEditBtnStyle = css`
  letter-spacing: 2px;
  font-weight: 500;
  margin-right: 10px;
`;
export const cssDeleteBtnStyle = css`
  letter-spacing: 2px;
  font-weight: 500;
`;
export const cssLikeContainer = css`
  display: flex;
  justify-content: flex-end;
  font-size: 15px;
  p {
    margin: 0;
    padding: 0;
    margin-top: 10px;
    margin-right: 10px;
    font-size: 16px;
    font-weight: 500;
  }
`;
export const cssLike = css`
  width: 45px;
  height: 35px;
  margin-top: 2px;
  padding-top: 5px;
  border: 1px solid ${COMMON_COLOR.FONT1};
  border-radius: 5px;
  background-color: ${COMMON_COLOR.WHITE};
`;

export const cssPostDetail = css`
  font-family: unset !important;
  background: ${COMMON_COLOR.WHITE};
  width: 100%;
  height: 100vh;
  display: flex;
`;

export const cssPostDetailPage = css`
  display: flex;
  flex-direction: column;
  margin-top: 20px;
`;

// 1st block
export const cssPostDetailFirst = css`
  flex-direction: row;
  margin-top: 20px;
  margin-left: 25px;
  margin-right: 25px;
  font-size: 18px;
`;
export const cssPostDetailProfile = css`
  display: flex;
  justify-content: flex-start;
  width: 50px;
  height: 50px;
  margin-top: 10px;
  margin-left: 5px;
  border-radius: 25px;
  background-color: ${COMMON_COLOR.BLUE_GRAY};
`;
export const cssPostDetailUser = css`
  margin-top: -35px;
  margin-left: 80px;
  font-weight: 500;
`;
export const cssPostDetailCreatedAt = css`
  display: flex;
  justify-content: flex-end;
  font-size: 16px;
`;

// 2nd block
export const cssPostDetailSecond = css`
  margin-left: 25px;
  margin-right: 25px;
  margin-top: -10px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;
export const cssPostDetailTitle = css`
  margin-top: 40px;
  font-size: 22px;
  font-weight: 500;
`;
export const cssPostDetailStatus = css`
  margin-top: 40px;
`;

// 3rd block
export const cssPostDetailThird = css`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  margin-top: 5px;
  margin-left: 25px;
  margin-right: 25px;
  .category {
    display: flex;
    flex-direction: row;
    align-items: center;
  }
`;
export const cssPostDetailCategory1 = css`
  margin-top: 20px;
  margin-right: 10px;
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
  color: ${COMMON_COLOR.WHITE};
  border-radius: 10px;
  padding: 5px;
`;

// 4th block
export const cssPostDetailFourth = css`
  margin-top: 25px;
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
  margin-top: 25px;
  margin-left: 25px;
  margin-right: 25px;
  .content {
    font-size: 15px;
    margin-left: 5px;
    margin-top: 10px;
  }
`;
export const cssPostDetailContent2 = css`
  margin-top: 10px;
  padding: 10px 7px;
  border: 1.3px solid ${COMMON_COLOR.FONT1};
  border-radius: 10px;
  font-size: 18px;
  letter-spacing: 1.3px;
`;
export const cssPostDetailAttachment = css`
  margin-bottom: 10px;
`;

// 6th
export const cssPostDetailSixth = css`
  margin-top: 25px;
  margin-left: 25px;
  margin-right: 25px;
  margin-bottom: 200px;
  p {
    text-align: center;
    font-size: 15px;
    font-weight: 500;
    color: ${COMMON_COLOR.FONT2};
    line-height: 20px;
  }
`;

export const cssReportContainer = css`
  display: flex;
  justify-content: flex-end;
  margin-right: 25px;
`;
export const cssReportBtnStyle = css`
  letter-spacing: 1.5px;
  font-weight: 500;
`;

// comment
export const cssCommentContainer = css`
  margin-top: 10px;
  margin-left: 25px;
  margin-right: 25px;
  p {
    font-size: 22px;
    font-weight: 500;
    margin: 0;
    margin-top: 20px;
  }
`;
export const cssCollectButton = css`
  display: flex;
  justify-content: center;
  margin-top: 5px;
  margin-bottom: 15px;
`;
export const cssCollectBtn = css`
  width: 50%;
  height: 40px;
  letter-spacing: 1.5px;
  font-weight: 600;
  font-size: 18px;
  color: ${COMMON_COLOR.WHITE};
  background-color: ${COMMON_COLOR.MAIN2};
  border: none;
  border-radius: 15px;
`;

export const cssApplicant = css`
  height: 300px;
  overflow: scroll;
  font-size: 18px;
`;

// footer
export const cssPostFooter = css`
  box-shadow: 0px -5px 2px 3px rgb(255, 255, 255, 0.5);
  position: fixed;
  width: 100%;
  height: 160px;
  bottom: 0;
  margin: 0;
  padding: 0;
  background-color: ${COMMON_COLOR.WHITE};
`;
export const cssPostFooter2 = css`
  display: flex;
  flex-direction: row;
`;

export const cssPostTextarea = css`
  border: none;
  :focus {
    outline: none;
  }
`;
export const cssPostBtn = css`
  margin-top: 20px;
  margin-right: 10px;
  width: 18%;
  height: 40px;
  border: none;
  border-radius: 10px;
  background-color: ${COMMON_COLOR.FONT1};
  color: ${COMMON_COLOR.BLACK};
  font-weight: 500;
  font-size: 16px;
  letter-spacing: 1.3px;
  :active {
    background-color: ${COMMON_COLOR.GRAY};
  }
`;

// lines
export const cssLine1 = css`
  border-top: 1.5px solid ${COMMON_COLOR.FONT1};
  background-color: black;
  margin-top: 15px;
`;
export const cssLine2 = css`
  border-top: 1.5px solid ${COMMON_COLOR.FONT1};
  background-color: black;
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
export const cssLine5 = css`
  border-top: 1.2px solid ${COMMON_COLOR.FONT4};
  background-color: black;
`;
