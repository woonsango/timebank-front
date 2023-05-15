import { css } from '@emotion/react';
import { Input, Button, Form, Row } from 'antd';
import { useCallback } from 'react';
import { useQueryClient } from 'react-query';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import { mainSearchState } from './mainSearchState';
import { IGetUserInfoRequest } from '../../api/interfaces/IUser';
import { cssPushSearchFormStyle } from '../../components/PushSearchForm/PushSearchForm.styles';

const SearchForm = () => {
  const queryClient = useQueryClient();

  const SearchValue = useRecoilValue(mainSearchState);
  const setSearch = useSetRecoilState(mainSearchState);

  /*필터 검색*/
  const handleSearchBtn = useCallback(
    async (values: IGetUserInfoRequest) => {
      setSearch(values);
      await queryClient.invalidateQueries({
        queryKey: ['userId', values],
      });
    },
    [setSearch, queryClient],
  );

  return (
    <div css={cssPushSearchFormStyle}>
      <Form layout="horizontal" onFinish={handleSearchBtn}>
        <Row>
          <Form.Item
            label="이름 / 닉네임 / UID"
            name="title"
            initialValue={SearchValue?.userId}
          >
            <Input placeholder="입력" />
          </Form.Item>
          <Button
            style={{ marginLeft: 'auto' }}
            htmlType="submit"
            type="primary"
          >
            검색
          </Button>
        </Row>
      </Form>
    </div>
  );
};
export default SearchForm;
