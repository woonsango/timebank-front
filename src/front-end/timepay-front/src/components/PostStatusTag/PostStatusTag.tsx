import { Tag } from 'antd';
import { useMemo } from 'react';
import { IPostState } from '../../api/interfaces/IPost';
import { COMMON_COLOR } from '../../styles/constants/colors';
import { cssPostStatusTagStyle } from './PostStatusTag.styles';

export interface PostStatusTagColorProps {
  pointColor: string;
  backgroundColor?: string;
}
const PostStatusTag = ({ status }: { status?: IPostState }) => {
  const statusColor: PostStatusTagColorProps = useMemo(() => {
    switch (status) {
      case '매칭중':
        return {
          pointColor: COMMON_COLOR.MAIN1,
        };
      case '매칭완료':
        return {
          pointColor: COMMON_COLOR.FONT2,
        };
      case '활동시작':
        return {
          pointColor: COMMON_COLOR.BLACK,
        };
      case '활동완료':
        return {
          pointColor: COMMON_COLOR.BLUE_GRAY,
        };
      case '활동취소':
        return {
          pointColor: COMMON_COLOR.FONT2,
          backgroundColor: COMMON_COLOR.FONT1,
        };
      case '활동지연':
        return {
          pointColor: COMMON_COLOR.CORAL,
        };
      default:
        return {
          pointColor: COMMON_COLOR.FONT2,
        };
    }
  }, [status]);
  return (
    <Tag css={cssPostStatusTagStyle(statusColor)}>{status || '로딩 중'}</Tag>
  );
};

export default PostStatusTag;
