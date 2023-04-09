import {
  Form,
  Input,
  InputNumber,
  Select,
  DatePicker,
  Row,
  Button,
} from 'antd';
import { cssPushSearchFormStyle } from './PushSearchForm.styles';

const PushSearchForm = () => {
  return (
    <div css={cssPushSearchFormStyle}>
      <Form layout="horizontal">
        <Row>
          <Form.Item label="공지 제목" name="pushTitle">
            <Input placeholder="공지 제목 입력" />
          </Form.Item>
          <Button style={{ marginLeft: 'auto' }} type="primary">
            검색
          </Button>
        </Row>
      </Form>
    </div>
  );
};

export default PushSearchForm;
