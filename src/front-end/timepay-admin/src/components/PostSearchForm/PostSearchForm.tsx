import {
  Form,
  Input,
  InputNumber,
  Select,
  DatePicker,
  Row,
  Button,
} from 'antd';
import { cssPostSearchFormStyle } from './PostSearchForm.styles';

const PostSearchForm = () => {
  return (
    <div css={cssPostSearchFormStyle}>
      <Form layout="horizontal">
        <Row>
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
          <Form.Item name="postIdSearchKeyword" initialValue="postId">
            <Select style={{ width: 150 }}>
              <Select.Option value="postId">게시글 번호</Select.Option>
              <Select.Option value="originPostId">
                원본 게시글 번호
              </Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="postId">
            <InputNumber placeholder="게시글 번호 입력" />
          </Form.Item>
          <Form.Item name="postMetaSearchKeyword" initialValue="title">
            <Select style={{ width: 100 }}>
              <Select.Option value="title">제목</Select.Option>
              <Select.Option value="category">카테고리</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item name="postMetaSearchValue">
            <Input placeholder="게시글 정보 입력" />
          </Form.Item>
        </Row>
        <Row>
          <Form.Item name="fromStartTimeToEndTime" label="활동시간">
            <DatePicker.RangePicker />
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

export default PostSearchForm;
