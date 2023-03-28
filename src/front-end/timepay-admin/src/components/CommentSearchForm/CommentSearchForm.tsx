import { Form, Input, InputNumber, Select, Row, Button } from 'antd';
import { cssCommentSearchFormStyle } from './CommentSearchForm.styles';

const CommentSearchForm = () => {
  return (
    <div css={cssCommentSearchFormStyle}>
      <Form layout="horizontal">
        <Row>
          <Form.Item name="postId" label="게시글 번호">
            <InputNumber min={0} placeholder="번호 입력" />
          </Form.Item>
          <Form.Item name="authorSearchKeyword" initialValue="name">
            <Select style={{ width: 100 }}>
              <Select.Option value="name">이름</Select.Option>
              <Select.Option value="nickname">닉네임</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="authorSearchValue">
            <Input placeholder="작성자 정보 입력" />
          </Form.Item>
        </Row>
        <Button type="primary">검색</Button>
      </Form>
    </div>
  );
};

export default CommentSearchForm;
