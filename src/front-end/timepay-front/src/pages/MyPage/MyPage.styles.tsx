import { css } from '@emotion/react';

export const cssMyInfoStyle = css`
  margin: 0 10%;
  margin-bottom: 25px;
  .info-header {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 15px;
    font-style: normal;
    font-weight: 400;
    font-size: 20px;
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
        font-size: 16px;
        line-height: 15px;
        color: #f1af23;
      }
      .value {
        font-style: normal;
        font-weight: 400;
        font-size: 16px;
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
