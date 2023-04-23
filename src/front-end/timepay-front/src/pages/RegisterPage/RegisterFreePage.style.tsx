import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import '../../styles/fonts/pretendard/pretendard.css';

export const cssPostPageStyle = css`
  font-family: unset !important;
  background: ${COMMON_COLOR.WHITE};
  width: 100%;
  height: 100vh;
  display: flex;
  overflow-x: hidden;

  .wrapper {
    height: auto;
    min-height: 100%;
    letter-spacing: 1px;
  }
  .upload {
    margin: 0px 20px 80px 20px;
  }
  h5 {
    font-size: 19px;
    font-weight: 600;
    margin-left: 20px;
    margin-bottom: 15px;
  }
  h6 {
    font-size: 18px;
    font-weight: 500;
    margin-top: 15px;
    margin-left: 20px;
    margin-bottom: 10px;
  }
  p {
    font-size: 16px;
    margin-left: 20px;
  }

  .image-container {
    margin: 20px;
  }
  .imageFont {
    margin-bottom: 20px;
    font-size: 18px;
    font-weight: 500;
  }
  .images-container {
    display: flex;
    height: 200px;
    margin: 20px 0px 20px 0px;
    padding: 10px;
    border: 1px solid ${COMMON_COLOR.FONT1};
    border-radius: 10px;
    overflow: scroll;
    overflow: auto;
    white-space: nowrap;
  }

  .cssImageWrapper1 {
    display: flex;
    flex-direction: row;
    margin: 0;
    padding: 0;
  }
  .uploadBtn {
    width: 100px;
    height: 70px;
    background: ${COMMON_COLOR.FONT1};
    border: 1.5px solid ${COMMON_COLOR.FONT4};
    border-radius: 10px;
    font-size: 16px;
    font-weight: 500;
    text-align: center;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    &:hover {
      background: ${COMMON_COLOR.FONT4};
    }
  }
  #upload {
    display: none;
  }

  .cssImageWrapper2 {
    margin: 0;
    padding-right: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .cssImages {
    margin-top: 10px;
  }
  .changeBtn {
    margin-bottom: 10px;
    width: 85px;
    height: 30px;
    border: 1px solid ${COMMON_COLOR.FONT4};
    border-radius: 5px;
    font-size: 15px;
    font-weight: 500;
    text-align: center;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    &:hover {
      background: ${COMMON_COLOR.FONT1};
    }
  }
  #change {
    display: none;
  }

  .cssImagePlaceholder2 {
    display: flex;
    flex-direction: row;
  }

  .deleteBtn {
    width: 85px;
    height: 30px;
    font-size: 15px;
    font-weight: 500;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .cssSelectedImage {
    width: 100px;
    height: 100px;
  }
  .cssImages {
    display: flex;
    flex-direction: column;
  }

  .category-container {
    margin: 0px 20px;
  }
  .category {
    margin: 5px 10px;
    padding: 7px 10px;
    font-size: 18px;
    background-color: ${COMMON_COLOR.GRAY};
    border: 1px solid ${COMMON_COLOR.LIGHT_GRAY};
    border-radius: 10px;
    cursor: pointer;
  }

  .category.selected {
    background-color: ${COMMON_COLOR.MAIN3};
    border: 1px solid ${COMMON_COLOR.MAIN3};
  }
`;

export const cssPostTitleInputStyle = css`
  font-size: 25px;
  width: 100%;
  border: none;
  padding-top: 10px;
  padding-bottom: 10px;
  padding-left: 20px;
  padding-right: 20px;
  margin-top: 20px;
  :focus {
    outline: none;
  }
`;
export const cssPostContentInputStyle = css`
  width: 100%;
  font-size: 22px;
  border: none;
  padding-top: 10px;
  padding-bottom: 10px;
  padding-left: 20px;
  padding-right: 20px;
  margin-top: 10px;
  :focus {
    outline: none;
  }
`;

export const cssLineStyle = css`
  border-top: 1px solid ${COMMON_COLOR.FONT1};
  margin-top: 15px;
`;

export const cssPostFooterStyle = css`
  position: fixed;
  width: 100%;
  height: 70px;
  bottom: 0;
  padding: 0;
`;
export const cssPostBtnStyle = css`
  width: 100%;
  height: 70px;
  background: ${COMMON_COLOR.MAIN1};
  color: ${COMMON_COLOR.WHITE};
  border: none;
  letter-spacing: 3px;
  font-size: 24px;
  font-weight: 600;
`;
