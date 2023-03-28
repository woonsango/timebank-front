import {
  Form,
  Input,
  InputNumber,
  Select,
  DatePicker,
  Row,
  Button,
} from 'antd';
import { cssReportSearchFormStyle } from './ReportSearchForm.styles';

const ReportSearchForm = () => {
  return (
    <div css={cssReportSearchFormStyle}>
      <Form layout="horizontal">
        <Row>
          <Form.Item
            name="reportName"
            style={{ marginRight: 15 }}
            label="신고자 이름"
          >
            <Input style={{ width: 120 }} placeholder="이름 입력" />
          </Form.Item>
          <Form.Item
            name="reportNickname"
            style={{ marginRight: 15 }}
            label="신고자 닉네임"
          >
            <Input style={{ width: 120 }} placeholder="닉네임 입력" />
          </Form.Item>
          <Form.Item name="reportPostType" initialValue="post">
            <Select style={{ width: 150 }}>
              <Select.Option value="post">게시글 번호</Select.Option>
              <Select.Option value="comment">댓글 번호</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="reportPostNumber">
            <Input style={{ width: 150 }} placeholder="신고글 번호 입력" />
          </Form.Item>
          <Button style={{ marginLeft: 'auto' }} type="primary">
            검색
          </Button>
        </Row>
      </Form>
    </div>
  );
};

export default ReportSearchForm;
