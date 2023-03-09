import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import styled from "styled-components";


/*프로필 이미지 설정 CSS*/
export const JoinStyleImage = styled.div`
    img{
        position: absolute;
        width: 130px;
        height: 130px;
        top: 160px;
        left: 50%;
        transform: translate(-50%, -50%);

        border-radius: 70%
        //object-fit: cover;

    }
    label{
        //padding: 6px 25px;
        //background-color:#FF6600;
        //border-radius: 4px;
        color: ${COMMON_COLOR.MAIN1};
        cursor: pointer;

        position: absolute;
        width: 150px;
        height: 20px;
        top: 260px;
        left: 50%;
        transform: translate(-50%, -50%);

        font-size: 0.8rem;
        display:inline-block;
        text-align:center;
    }
    input[type="file"]{
        position: absolute;
        width: 0;
        height: 0;
        padding: 0;
        margin: -1px;
        overflow: hidden;
        clip: rect(0, 0, 0, 0);
        border: 0;
    }
`

/*닉네임 설정 CSS*/

export const cssJoinStyle_nicknameTitle = css`
    position: absolute;
    width: 150px;
    top: 290px;
    left: 50%;
    transform: translate(-50%, -50%);
`;

export const cssJoinStyle_nicknameInput = css`
    position: absolute;
    width: 150px;
    height: 20px;
    top: 340px;
    left: 50%;
    transform: translate(-50%, -50%);

    border: none; // input 박스 테두리 없애기
    outline: 1px solid ${COMMON_COLOR.MAIN1}; //input 박스 클릭 시 테두리 색 변경
`;



/*거주 동네 설정 CSS*/
export const cssJoinStyle_townTitle = css`
    position: absolute;
    width: 150px;
    top: 360px;
    left: 50%;
    transform: translate(-50%, -50%);

   
`;

export const cssJoinStyle_townInputGu: { [name: string]: React.CSSProperties } = {
    container: {
      marginTop: 700,
      display: "flex",
      flexDirection: "column",
      alignItems: "center",
    },
    select: {
      padding: 5,
      width: 200,
    },
    result: {
      marginTop: 30,
    },
  };

  export const cssJoinStyle_townInputDong: { [name: string]: React.CSSProperties } = {
    container: {
      marginTop: 800,
      display: "flex",
      flexDirection: "column",
      alignItems: "center",
    },
    select: {
      padding: 5,
      width: 200,
    },
    result: {
      marginTop: 30,
    },
  };


/*소개 한마디 설정 CSS*/
export const cssJoinStyle_introductionTitle = css`
    position: absolute;
    width: 150px;
    top: 430px;
    left: 50%;
    transform: translate(-50%, -50%);
`;
export const cssJoinStyle_introductionInput = css`
    position: absolute;
    width: 150px;
    height: 20px;
    top: 480px;
    left: 50%;
    transform: translate(-50%, -50%);

    border: none; // input 박스 테두리 없애기
    outline: 1px solid ${COMMON_COLOR.MAIN1}; //input 박스 클릭 시 테두리 색 변경
`;


/*가입완료 버튼 CSS*/
export const cssJoinStyle_finishBtn = 
css`
    background: ${COMMON_COLOR.MAIN1};
    color: white;

    position: absolute;
    width: 170px;
    height: 35px;
    top: 580px;
    left: 50%;
    transform: translate(-50%, -50%);
`;
