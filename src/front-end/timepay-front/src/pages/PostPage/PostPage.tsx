import { useCallback, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { ReactComponent as BackArrow } from '../../assets/images/icons/header-back-arrow.svg';
import { cssMainHeaderStyle } from '../../components/MainHeader/MainHeader.styles';
import {
  cssPostDetailPage,
  cssPostDetailFirst,
  cssPostDetailUser,
  cssPostDetailTitle,
  cssLine1,
  cssLine3,
  cssLine4,
  cssPostDetailCreatedAt,
  cssPostDetailProfile,
  cssPostDetailSecond,
  cssPostDetailStatus,
  cssPostDetailThird,
  cssPostDetailCategory1,
  cssPostDetailCategory2,
  cssPostDetailPay,
  cssPostDetailFourth,
  cssPostDetailRegion,
  cssPostDetailTime,
  cssPostDetailFifth,
  cssPostDetailContent1,
  cssPostDetailContent2,
  cssPostDetailAttachment,
  cssPostFooter,
  cssPostDetail,
  cssPostTextarea,
  cssLine2,
  cssPostBtn,
  cssPostFooter2,
} from './PostPage.style';
import PostStatusTag from '../../components/PostStatusTag';
import { ClockCircleOutlined, FlagFilled } from '@ant-design/icons';
import { Button, Layout } from 'antd';
import TextArea from 'antd/es/input/TextArea';

const Footer = Layout;

const PostPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const {
    type,
    title,
    content,
    createdAt,
    category,
    attachment,
    status,
    pay,
    startTime,
    endTime,
    region,
    user,
  } = location.state;

  const handleClickBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  return (
    <Layout css={cssPostDetail}>
      <div css={cssMainHeaderStyle}>
        <BackArrow onClick={handleClickBack} />
        <span>{type}</span>
      </div>
      <div css={cssPostDetailPage}>
        <div css={cssPostDetailFirst}>
          <div css={cssPostDetailCreatedAt}>{createdAt}</div>
          <div css={cssPostDetailProfile}></div>
          <div css={cssPostDetailUser}>{user}</div>
        </div>
        <div css={cssLine1} />
        <div css={cssPostDetailSecond}>
          <div css={cssPostDetailTitle}>{title}</div>
          <div css={cssPostDetailStatus}>
            <PostStatusTag status={status} />
          </div>
        </div>
        <div css={cssLine3} />
        <div>
          <div css={cssPostDetailThird}>
            <div css={cssPostDetailCategory1}>카테고리</div>
            <div css={cssPostDetailCategory2}>{category}</div>
            <div css={cssPostDetailPay}>{pay} TP</div>
          </div>
        </div>
        <div css={cssLine3} />
        <div css={cssPostDetailFourth}>
          <div css={cssPostDetailRegion}>
            <FlagFilled style={{ marginRight: 10 }} />
            {region}
          </div>
          <div css={cssPostDetailTime}>
            <ClockCircleOutlined style={{ marginRight: 10 }} />
            {startTime} ~ {endTime}
          </div>
        </div>
        <div css={cssLine1} />
        <div css={cssPostDetailFifth}>
          <div css={cssPostDetailContent1}>내용</div>
          <div css={cssPostDetailContent2}>{content}</div>
          <div css={cssPostDetailAttachment}>{attachment}</div>
        </div>
        <div css={cssLine4} />
      </div>
      <Footer css={cssPostFooter}>
        <div css={cssLine2} />
        <div css={cssPostFooter2}>
          <TextArea
            css={cssPostTextarea}
            style={{ height: 100, width: '90%', fontSize: 20, resize: 'none' }}
          ></TextArea>
          <Button css={cssPostBtn}>등록</Button>
        </div>
      </Footer>
    </Layout>
  );
};
export default PostPage;
