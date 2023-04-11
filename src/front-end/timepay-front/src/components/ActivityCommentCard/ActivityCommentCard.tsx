import { SelectOutlined } from '@ant-design/icons';
import { ICommentActivity } from '../../api/interfaces/IComment';
import { cssActivityCommentCardStyle } from './ActivityCommentCard.styles';

interface ActivityCommentCardProps {
  comment: ICommentActivity;
}
const ActivityCommentCard = ({ comment }: ActivityCommentCardProps) => {
  return (
    <div css={cssActivityCommentCardStyle}>
      <div className="activity-comment-content">{comment.content}</div>
      <div className="activity-origin-post-title">
        <SelectOutlined />
        {comment.postTitle}
      </div>
      <div className="activity-comment-date-time">
        작성시간 : {comment.createdAt}
      </div>
    </div>
  );
};

export default ActivityCommentCard;
