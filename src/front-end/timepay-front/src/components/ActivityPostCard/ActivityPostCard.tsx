import { useMemo } from 'react';
import { IPost } from '../../api/interfaces/IPost';
import { COMMON_COLOR } from '../../styles/constants/colors';
import PostStatusTag from '../PostStatusTag';
import PostTypeTag from '../PostTypeTag';
import { cssPostTypeTagStyle } from '../PostTypeTag/PostTypeTag.styles';
import { cssActivityPostCardStyle } from './ActivityPostCard.styles';

interface ActivityPostCardProps {
  post: IPost;
}
const ActivityPostCard = ({ post }: ActivityPostCardProps) => {
  const isHelpActivity = useMemo(() => {
    return post.type === '도움요청' || post.type === '도움주기';
  }, [post]);
  return (
    <div css={cssActivityPostCardStyle}>
      <div className="activity-post-card-header">
        <PostTypeTag type={post.type} />
        {isHelpActivity && <PostStatusTag status={post.status} />}
      </div>
      <div className="activity-post-card-title">{post.title}</div>
      {isHelpActivity ? (
        <>
          <div className="activity-post-date-time">
            활동시간 : {post.startTime} ~ {post.endTime.split(' ')[1]}
          </div>
          <div className="activity-post-pay">
            {post.type === '도움요청'
              ? '소모 예정 타임페이'
              : '획득 예정 타임페이'}
            <div
              className="amount"
              css={cssPostTypeTagStyle({
                backgroundColor: COMMON_COLOR.MAIN1,
              })}
            >
              {post?.pay || '-'} TP
            </div>
          </div>
        </>
      ) : (
        <div className="activity-post-date-time">
          작성시간 : {post.createdAt}
        </div>
      )}
    </div>
  );
};

export default ActivityPostCard;
