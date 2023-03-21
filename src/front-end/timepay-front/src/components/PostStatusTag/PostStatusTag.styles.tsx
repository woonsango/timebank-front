import { css } from '@emotion/react';
import { PostStatusTagColorProps } from './PostStatusTag';

export const cssPostStatusTagStyle = (props: PostStatusTagColorProps) => css`
  margin: 0;
  padding: 0 5px;
  color: ${props.pointColor};
  border: 1px solid ${props.pointColor};
  background-color: ${props.backgroundColor};
  font-style: normal;
  font-weight: 700;
  font-size: 15px;
  line-height: 20px;
`;
