import { useCallback, useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { Button, Layout } from 'antd';
import TextArea from 'antd/es/input/TextArea';
import { IPost } from '../../api/interfaces/IPost';
import { cssMainHeaderStyle } from '../../components/MainHeader/MainHeader.styles';
import {
  cssPostEditPage,
  cssPostDetailFirst,
  cssPostDetailUser,
  cssPostDetailTitle,
  cssLine1,
  cssLine2,
  cssLine3,
  cssPostDetailProfile,
  cssPostEditSecond,
  cssPostDetailThird,
  cssPostDetailCategory1,
  cssPostDetailCategory2,
  cssPostDetailPay,
  cssPostDetailFourth,
  cssPostDetailRegion,
  cssPostDetailTime,
  cssPostDetailFifth,
  cssPostDetailContent1,
  cssPostEditContent2,
  cssPostDetailAttachment,
  cssPostDetail,
  cssPostTextarea,
  cssPostEditBtn1,
  cssPostEditBtn2,
  cssPostEditFooter,
  cssPostEditFooter2,
} from './PostEditPage.style';
import { ClockCircleOutlined, FlagFilled } from '@ant-design/icons';

const Footer = Layout;

interface PostProps {
  post?: IPost;
}

const PostEditPage = ({ post }: PostProps) => {
  const navigate = useNavigate();
  const location = useLocation();
  const {
    title,
    content,
    category,
    attachment,
    pay,
    startTime,
    endTime,
    region,
    user,
  } = location.state;

  useEffect(() => {
    /*
    const getBoard = async () => {
      const { data } = await axios.get();
      return data;
      
    };
    getBoard().then((result) => {
       result?.editTitle     */
    /*
    });
    */
  }, []);

  /*
  const handleTitleChange = useCallback(
    (e: React.ChangeEvent<HTMLTextAreaElement>) => {
      setTitles(e.target.value);
    },
    [],
  );
  */

  const handleClickCancel = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  const handleClickSave = useCallback(() => {
    // 수정된 게시글 정보를 API로 전송하는 로직
    // ...

    navigate(-1);
  }, [navigate]);

  return (
    <Layout css={cssPostDetail}>
      <div css={cssMainHeaderStyle}>
        <span>게시글 수정</span>
      </div>
      <div css={cssPostEditPage}>
        <div css={cssPostDetailFirst}>
          <div css={cssPostDetailProfile}></div>
          <div css={cssPostDetailUser}>{user}</div>
        </div>
        <div css={cssLine1} />
        <div css={cssPostEditSecond}>
          <div css={cssPostDetailTitle}>
            <TextArea
              defaultValue={title}
              css={cssPostTextarea}
              style={{ height: 50, resize: 'none' }}
            />
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
          <TextArea
            defaultValue={content}
            css={cssPostEditContent2}
            style={{ resize: 'none' }}
          >
            {content}
          </TextArea>
          <div css={cssPostDetailAttachment}>{attachment}</div>
        </div>
      </div>
      <Footer css={cssPostEditFooter}>
        <div css={cssLine2} />
        <div css={cssPostEditFooter2}>
          <Button css={cssPostEditBtn1} onClick={handleClickCancel}>
            취소
          </Button>
          <Button css={cssPostEditBtn2} onClick={handleClickSave}>
            저장
          </Button>
        </div>
      </Footer>
    </Layout>
  );
};

export default PostEditPage;
