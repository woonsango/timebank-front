import { Tag } from 'antd';
import { useMemo } from 'react';
import useFontSize from '../../hooks/useFontSize';
import { COMMON_COLOR } from '../../styles/constants/colors';
import { getStatus } from '../../utils/board';
import { cssPostStatusTagStyle } from './PostStatusTag.styles';

export interface PostStatusTagColorProps {
  pointColor: string;
  backgroundColor?: string;
}
const PostStatusTag = ({ status }: { status?: string | null }) => {
  const { scaleValue } = useFontSize();
  const statusColor: PostStatusTagColorProps = useMemo(() => {
    switch (getStatus(status)) {
      case '매칭중':
        return {
          pointColor: COMMON_COLOR.MAIN1,
        };
      case '매칭완료':
        return {
          pointColor: COMMON_COLOR.FONT2,
        };
      case '활동중':
        return {
          pointColor: COMMON_COLOR.FONT2,
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
      default:
        return {
          pointColor: COMMON_COLOR.FONT2,
        };
    }
  }, [status]);
  return (
    <Tag css={cssPostStatusTagStyle(statusColor, scaleValue)}>
      {getStatus(status) || '로딩 중'}
    </Tag>
  );
};

export default PostStatusTag;
