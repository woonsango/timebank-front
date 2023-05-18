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
        {comment.boardTitle}
      </div>
      <div className="activity-comment-date-time">
        작성시간 : {comment.writtenTime?.split('.')[0].replace('T', ' ')}
      </div>
    </div>
  );
};

export default ActivityCommentCard;
