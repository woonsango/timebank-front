import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import styled from 'styled-components';

/*프로필 이미지 설정 CSS*/
export const JoinStyleImage = styled.div`
  img {
    position: absolute;
    width: 130px;
    height: 130px;
    top: 160px;
    left: 50%;
    transform: translate(-50%, -50%);

    border-radius: 70%;
    //object-fit: cover;
  }
  label {
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
    display: inline-block;
    text-align: center;
  }
  input[type='file'] {
    position: absolute;
    width: 0;
    height: 0;
    padding: 0;
    margin: -1px;
    overflow: hidden;
    clip: rect(0, 0, 0, 0);
    border: 0;
  }
`;

export const cssJoinStyle_townInputGu: { [name: string]: React.CSSProperties } =
  {
    container: {
      marginTop: 100,
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    },
    select: {
      padding: 5,
      width: 200,
    },
    result: {
      marginTop: 30,
    },
  };

export const cssJoinStyle_townInputDong: {
  [name: string]: React.CSSProperties;
} = {
  container: {
    marginTop: 800,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  select: {
    padding: 5,
    width: 200,
  },
  result: {
    marginTop: 30,
  },
};
