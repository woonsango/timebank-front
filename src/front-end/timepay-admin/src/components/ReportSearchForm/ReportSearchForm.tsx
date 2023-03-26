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
          <Form.Item name="reportNumber" label="신고번호">
            <Input style={{ width: 130 }} placeholder="신고번호 입력" />
          </Form.Item>
          <Form.Item name="reportAuthorName" initialValue="name">
            <Select>
              <Select.Option value="name">이름</Select.Option>
              <Select.Option value="userPk">회원번호</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="authorSearchValue">
            <Input placeholder="신고자 정보 입력" />
          </Form.Item>
          <Form.Item name="reportPostNumber" label="신고대상 글번호">
            <Input style={{ width: 150 }} placeholder="신고게시글 번호 입력" />
          </Form.Item>
          <Form.Item name="regTime" label="작성시간">
            <DatePicker.RangePicker />
          </Form.Item>
          <Form.Item name="reportPostList" initialValue="title">
            <Select style={{ width: 150 }}>
              <Select.Option value="report">신고사유</Select.Option>
              <Select.Option value="title">신고글 제목</Select.Option>
              <Select.Option value="post">신고글 내용</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="reportPostListValue">
            <Input placeholder="신고 정보 입력" />
          </Form.Item>
          <Button type="primary">검색</Button>
        </Row>
      </Form>
    </div>
  );
};

export default ReportSearchForm;
