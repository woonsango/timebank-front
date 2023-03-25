import {
  Form,
  Input,
  InputNumber,
  Select,
  DatePicker,
  Row,
  Button,
} from 'antd';
import { cssCommentSearchFormStyle } from './CommentSearchForm.styles';

const CommentSearchForm = () => {
  return (
    <div css={cssCommentSearchFormStyle}>
      <Form layout="horizontal">
        <Row>
          <Form.Item name="idSearchKeyword" initialValue="postId">
            <Select style={{ width: 120 }}>
              <Select.Option value="postId">게시글 번호</Select.Option>
              <Select.Option value="commentId">댓글 번호</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="idSearchValue">
            <InputNumber min={0} placeholder="번호 입력" />
          </Form.Item>
          <Form.Item name="authorSearchKeyword" initialValue="name">
            <Select style={{ width: 100 }}>
              <Select.Option value="name">이름</Select.Option>
              <Select.Option value="userPk">회원번호</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="authorSearchValue">
            <Input placeholder="작성자 정보 입력" />
          </Form.Item>
        </Row>

        <Row>
          <Form.Item name="content" label="내용">
            <Input placeholder="댓글 내용 입력" />
          </Form.Item>
          <Form.Item name="regTime" label="작성시간">
            <DatePicker.RangePicker />
          </Form.Item>
        </Row>
        <Button type="primary">검색</Button>
      </Form>
    </div>
  );
};

export default CommentSearchForm;
