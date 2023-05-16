import { Button, Card, Input, List, Typography } from 'antd';
import { useLocation, useNavigate } from 'react-router-dom';
import { cssInquiryDetailManagementPageStyle } from './InquiryDetailManagementPage.style';
import { useGetInquiryDetail } from '../../api/hooks/inquiry';
import { useCallback } from 'react';
import { COMMON_COLOR } from '../../styles/constants/colors';

const { TextArea } = Input;

interface adminAnswerResponses {
  content: string;
  adminName: string;
  createdAt: string;
}

const InquiryDetailManagementPage = () => {
  const location = useLocation();
  const state = location.state;
  const { data } = useGetInquiryDetail(state);
  console.log(data?.data.inquiryResponse.content);
  const adminAnswerResponses: adminAnswerResponses[] =
    data?.data.adminAnswerResponses || [];

  const navigate = useNavigate();

  const handleClickBack = useCallback(() => {
    navigate(-1);
  }, [navigate]);

  const handleClickAnswerBtn = useCallback(() => {}, []);

  return (
    <Card
      title="문의 조회"
      css={cssInquiryDetailManagementPageStyle}
      extra={
        <Button
          block
          onClick={handleClickBack}
          style={{ color: COMMON_COLOR.FONT2 }}
        >
          이전화면
        </Button>
      }
    >
      <div className="InquiryTitle">
        <Card title={data?.data.inquiryResponse.title}>
          <div className="InquiryInnerContent">
            {data?.data.inquiryResponse.content}
          </div>
        </Card>
        <List
          itemLayout="horizontal"
          dataSource={adminAnswerResponses}
          renderItem={(item) => (
            <List.Item>
              <Card style={{ width: '100%' }} title={item.adminName}>
                {item.content}
              </Card>
            </List.Item>
          )}
        />
        <div className="answerInput">
          <TextArea
            className="textInput"
            placeholder="답변을 입력해주세요"
            autoSize={{ minRows: 5, maxRows: 5 }}
          />
          <div className="answerBtnBlock">
            <Button
              block
              className="answerBtn"
              type="primary"
              onClick={handleClickAnswerBtn}
              style={{ color: COMMON_COLOR.WHITE }}
            >
              답변등록
            </Button>
          </div>
        </div>
      </div>
    </Card>
  );
};

export default InquiryDetailManagementPage;
