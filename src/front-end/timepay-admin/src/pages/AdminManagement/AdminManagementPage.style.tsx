import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import '../../styles/fonts/pretendard/pretendard.css';

export const cssTitle = css`
  padding-left: 2%;
  font-size: 35px;
  color: ${COMMON_COLOR.MAIN1};
  border-bottom: solid 8px ${COMMON_COLOR.MAIN1};
  height: 60px;
  width: 20%;
`;
