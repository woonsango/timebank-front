import { css } from '@emotion/react';
import { COMMON_COLOR } from '../../styles/constants/colors';
import { PostTypeTagColorProps } from './PostTypeTag';

export const cssPostTypeTagStyle = (props: PostTypeTagColorProps) => css`
  background-color: ${props.backgroundColor};
  color: ${COMMON_COLOR.WHITE};
  border-radius: 30px;
  width: 60px;
  padding: 0 5px;
  font-style: normal;
  font-weight: 700;
  font-size: 13px;
  line-height: 20px;
  text-align: center;
`;
