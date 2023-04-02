import {
  Form,
  Input,
  InputNumber,
  Select,
  DatePicker,
  Row,
  Button,
} from 'antd';
import { cssQnASearchFormStyle } from './QnASearchForm.styles';

const QnASearchForm = () => {
  return (
    <div css={cssQnASearchFormStyle}>
      <Form layout="horizontal">
        <Row>
          <Form.Item name="QnAState" initialValue="question">
            <Select style={{ width: 150 }}>
              <Select.Option value="question">답변대기</Select.Option>
              <Select.Option value="answer">답변완료</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item
            name="QnAName"
            style={{ marginRight: 15 }}
            label="문의 제목"
          >
            <Input style={{ width: 300 }} placeholder="제목 입력" />
          </Form.Item>

          <Button style={{ marginLeft: 'auto' }} type="primary">
            검색
          </Button>
        </Row>
      </Form>
    </div>
  );
};

export default QnASearchForm;
