import { css } from '@emotion/react';

export const cssMyInfoStyle = (scaleValue: number) => css`
  margin: 0 10%;
  margin-bottom: 25px;
  .info-header {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 15px;
    font-style: normal;
    font-weight: 400;
    font-size: calc(20px * ${scaleValue});
    line-height: 15px;
    color: #858585;
    img {
      width: 70px;
      height: 70px;
      border-radius: 100%;
    }
    margin-bottom: 20px;
  }
  .info-detail {
    display: flex;
    flex-direction: column;
    gap: 7px;

    div {
      display: flex;
      flex-direction: row;
      width: 100%;
      gap: 15px;
      .label {
        font-style: normal;
        font-weight: 700;
        font-size: calc(16px * ${scaleValue});
        line-height: 15px;
        color: #f1af23;
      }
      .value {
        font-style: normal;
        font-weight: 400;
        font-size: calc(16px * ${scaleValue});
        line-height: 15px;
        color: #787878;
        .yes {
          color: green;
        }
        .no {
          color: red;
        }
      }
    }
  }
`;

export const cssMyPage = css`
  width: 100%;
  height: 100%;
  padding: 10px 30px;
`;
export const cssMyPageProfileBlock = (scaleValue: number) => css`
  .MyTopBox {
    display: flex;
    width: 80%;
  }
  .MyImageWrap {
    width: 25%;
  }
  .MyProfileImage {
    width: 100px;
    height: 100px;
    border-radius: 70%;
  }
  .MyNameWrap {
    width: 70%;
    margin-left: 20%;
  }
  .MyName {
    font-size: calc(24px * ${scaleValue});
    font-weight: 600;
    color: gray;
    margin-top: 10px;

    text-overflow: ellipsis;
    overflow: hidden;
  }
  .MyPersonalNum {
    font-size: calc(12px * ${scaleValue});
    color: #6d6a6aa2;
  }
`;

export const cssMyPageInfoBlock = css`
  .MyTotalBox {
    display: flex;
    width: 100%;
    margin-top: 20px;
    margin-bottom: 40px;
  }
  MyLeftBox {
    display: inline-block;
    width: 40%;
  }
  .MyRightBox {
    display: inline-block;
    width: 60%;
    margin-left: 8%;
  }
  .MyTitleText {
    font-size: 13px;
    font-weight: 600;
    color: #f1af23;
    margin-top: 10px;
  }
  .MyContentText {
    font-size: 13px;
    font-weight: 600;
    color: gray;
    margin-top: 10px;
    overflow: hidden;
    width: 100%;

    /*벗어난 텍스트 처리*/
    text-overflow: ellipsis;
    overflow: hidden;
  }
`;

export const cssMyPageButtonBlock = css`
  .MyBlockBox {
    width: 80%;
    justify-content: center;
    border: 2px solid #e0dfdf;
    border-radius: 8px;
    margin: 0px 30px;
  }
  .MyPageMoveBox {
    width: 80%;
    height: 40px;
  }
  .MyPageText {
    width: 100%;
    height: 100%;
    text-align: left;

    background-color: white;
    color: #787878;

    border: 1px solid white;
    border-radius: 8px;
    font-size: 15px;
    font-weight: 700;
  }
`;
