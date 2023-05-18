import { useMemo } from 'react';
import { IBoard } from '../../api/interfaces/IPost';
import PostStatusTag from '../PostStatusTag';
import PostTypeTag from '../PostTypeTag';
import { cssActivityPostCardStyle } from './ActivityPostCard.styles';

interface ActivityPostCardProps {
  post: IBoard;
}
const ActivityPostCard = ({ post }: ActivityPostCardProps) => {
  const isHelpActivity = useMemo(() => {
    return post.type === 'help' || post.type === 'helper';
  }, [post]);
  return (
    <div css={cssActivityPostCardStyle}>
      <div className="activity-post-card-header">
        <PostTypeTag type={post.type} />
        {isHelpActivity && <PostStatusTag status={post.boardStatus} />}
      </div>
      <div className="activity-post-card-title">{post.title}</div>
      {isHelpActivity ? (
        <>
          <div className="activity-post-date-time">
            활동시간 : {post.startTime || '-'} ~{' '}
            {post.endTime ? post.endTime.split(' ')[1] : '-'}
          </div>
          <div className="activity-post-pay">
            {post.type === 'help' ? '소모 예정 타임페이' : '획득 예정 타임페이'}
            <div className="pay-number">{post?.pay || '-'} TP</div>
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
