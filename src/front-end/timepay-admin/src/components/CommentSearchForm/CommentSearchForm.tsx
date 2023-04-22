import { Form, Input, InputNumber, Select, Row, Button, message } from 'antd';
import { useCallback } from 'react';
import { useQueryClient } from 'react-query';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import { IGetCommentRequest } from '../../api/interfaces/IComment';
import { commentSearchState } from '../../states/commentSearchState';
import { cssCommentSearchFormStyle } from './CommentSearchForm.styles';

const CommentSearchForm = () => {
  const [messageApi, contextHolder] = message.useMessage();

  const queryClient = useQueryClient();
  const commentSearchValue = useRecoilValue(commentSearchState);
  const setCommentSearch = useSetRecoilState(commentSearchState);
  const handleOnSearch = useCallback(
    async (values: IGetCommentRequest) => {
      if (values.writerSearchValue && values.originBoardId) {
        messageApi.warning({
          content: '게시글 번호나 작성자 정보 둘 중 하나만 입력해야합니다.',
        });
      } else {
        setCommentSearch({
          ...values,
          [values.writerSearchKeyword as string]: values.writerSearchValue,
        });
        await queryClient.invalidateQueries({
          queryKey: ['useGetComments', values],
        });
      }
    },
    [setCommentSearch, messageApi, queryClient],
  );

  return (
    <div css={cssCommentSearchFormStyle}>
      <Form layout="horizontal" onFinish={handleOnSearch}>
        <Row>
          <Form.Item
            name="originBoardId"
            label="게시글 번호"
            initialValue={commentSearchValue?.originBoardId}
          >
            <InputNumber min={0} placeholder="번호 입력" />
          </Form.Item>
          <Form.Item
            name="writerSearchKeyword"
            initialValue={commentSearchValue?.writerSearchKeyword}
          >
            <Select style={{ width: 100 }}>
              <Select.Option value="writerName">이름</Select.Option>
              <Select.Option value="writerNickName">닉네임</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item
            name="writerSearchValue"
            initialValue={commentSearchValue?.writerSearchValue}
          >
            <Input placeholder="작성자 정보 입력" />
          </Form.Item>
          <Button
            type="primary"
            htmlType="submit"
            style={{ marginLeft: 'auto' }}
          >
            검색
          </Button>
        </Row>
      </Form>
      {contextHolder}
    </div>
  );
};

export default CommentSearchForm;
