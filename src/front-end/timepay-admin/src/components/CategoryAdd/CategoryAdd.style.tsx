import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';

export const cssCategoryAdd = css`
  width: 120px;
  height: 48px;
  font-size: 17px;
  font-weight: bold;
  text-align: center;
  background-color: ${COMMON_COLOR.MAIN1};
  &:hover {
    ${COMMON_COLOR.MAIN2}
  }
`;

export const cssBox = css`
  padding-right: 20%;
  padding-bottom: 20px;
  display: flex;
  flex-direction: row-reverse;
`;
