import { Form, Input, InputNumber, Select, Row, Button } from 'antd';
import { cssPostSearchFormStyle } from './PostSearchForm.styles';

const PostSearchForm = () => {
  return (
    <div css={cssPostSearchFormStyle}>
      <Form layout="horizontal">
        <Row>
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
        <Row>
          <Form.Item name="originPostId" label="원본 게시글 번호">
            <InputNumber placeholder="원본 게시글 번호 입력" />
          </Form.Item>
        </Row>
        <Row>
          <Form.Item name="title" label="제목">
            <Input style={{ width: 400 }} placeholder="게시글 제목 입력" />
          </Form.Item>
        </Row>
        <Button type="primary">검색</Button>
      </Form>
    </div>
  );
};

export default PostSearchForm;
