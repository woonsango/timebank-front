import { useMemo } from 'react';
import { IPostType } from '../../api/interfaces/IPost';
import { COMMON_COLOR } from '../../styles/constants/colors';
import { cssPostTypeTagStyle } from './PostTypeTag.styles';

export interface PostTypeTagColorProps {
  backgroundColor: string;
}
const PostTypeTag = ({ type }: { type?: IPostType }) => {
  const typeColor: PostTypeTagColorProps = useMemo(() => {
    switch (type) {
      case '도움요청':
        return {
          backgroundColor: COMMON_COLOR.MAIN1,
        };
      case '도움주기':
        return {
          backgroundColor: COMMON_COLOR.MAIN2,
        };
      case '자유':
        return {
          backgroundColor: COMMON_COLOR.FONT2,
        };
      case '후기':
        return {
          backgroundColor: COMMON_COLOR.NEON_GREEN,
        };
      default:
        return {
          backgroundColor: COMMON_COLOR.FONT2,
        };
    }
  }, [type]);
  return <div css={cssPostTypeTagStyle(typeColor)}>{type || '로딩 중'}</div>;
};

export default PostTypeTag;
