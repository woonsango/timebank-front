import { Card, Spin } from 'antd';
import { useCallback, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { IPost } from '../../api/interfaces/IPost';
import PostStatusTag from '../PostStatusTag';
import {
  cssSimplePostCardBodyStyle,
  cssSimplePostCardFooterStyle,
  cssSimplePostCardHeadStyle,
  cssSimplePostCardStyle,
} from './SimplePostCard.styles';
import { ReactComponent as RegionPin } from '../../assets/images/icons/region-pin.svg';
import { ReactComponent as Clock } from '../../assets/images/icons/clock.svg';
import { ReactComponent as Attachment } from '../../assets/images/icons/attachment.svg';
import { UserOutlined } from '@ant-design/icons';
import { ReactComponent as PostDetailArrow } from '../../assets/images/icons/post-detail-arrow.svg';
import PostTypeTag from '../PostTypeTag';
import { cssPostTypeTagStyle } from '../PostTypeTag/PostTypeTag.styles';
import { COMMON_COLOR } from '../../styles/constants/colors';
interface SimplePostCardProps {
  post?: IPost;
}

const SimplePostCard = ({ post }: SimplePostCardProps) => {
  const navigate = useNavigate();
  const handlePageChange = () => {
    navigate(`/post/${post?.postId}`, {
      state: {
        type: post?.type,
        title: post?.title,
        content: post?.content,
        createdAt: post?.createdAt,
        status: post?.status,
        category: post?.category,
        pay: post?.pay,
        startTime: post?.startTime,
        endTime: post?.endTime,
        region: post?.region,
        attachment: post?.attachment,
      },
    });
  };
  const footerComponent = useCallback(
    (nickname?: string, createdAt?: string) => {
      return (
        <div css={cssSimplePostCardFooterStyle}>
          <div className="nickname">
            <UserOutlined />
            {nickname || '-'}
          </div>
          <div className="view-more-container">
            <span className="view-more-text">더보기</span>
            <span className="view-more-icon">
              <PostDetailArrow />
            </span>
          </div>
          <div>{createdAt || '-'}</div>
        </div>
      );
    },
    [],
  );

  const postCardContent = useMemo(() => {
    return (
      <div>
        <div css={cssSimplePostCardHeadStyle}>
          <div className="tag">
            <div className="type">
              <PostTypeTag type={post?.type} />
            </div>
            <div
              className="amount"
              css={cssPostTypeTagStyle({
                backgroundColor: COMMON_COLOR.MAIN1,
              })}
            >
              {post?.pay || '-'} TP
            </div>
          </div>
          <div className="title">
            <PostStatusTag status={post?.status} />
            <div>{post?.title || '-'}</div>
            <div className="attachment">
              {post?.attachment && <Attachment />}
            </div>
          </div>
        </div>
        <div css={cssSimplePostCardBodyStyle}>
          <div className="post-card-region">
            <RegionPin />
            {post?.region || '-'}
          </div>
          <div className="post-card-time">
            <Clock />
            {post ? (
              <span>
                {post.startTime} ~ {post.endTime}
              </span>
            ) : (
              <span>-</span>
            )}
          </div>
          <div className="post-card-content">
            {post?.content || '로딩 중...'}
          </div>
        </div>
      </div>
    );
  }, [post]);

  return (
    <Card
      bordered={false}
      css={cssSimplePostCardStyle}
      onClick={handlePageChange}
    >
      <Spin size="large" spinning={!post}>
        {postCardContent}
      </Spin>
      {footerComponent(post?.user.nickname, post?.createdAt)}
    </Card>
  );
};

export default SimplePostCard;
