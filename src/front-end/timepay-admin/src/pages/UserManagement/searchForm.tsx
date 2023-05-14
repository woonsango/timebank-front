import { css } from '@emotion/react';
import { Input, Button, Form, Row } from 'antd';
import { useCallback } from 'react';
import { useQueryClient } from 'react-query';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import { mainSearchState } from './mainSearchState';
import { IGetUserInfoRequest } from '../../api/interfaces/IUser';

const SearchForm = () => {
  const queryClient = useQueryClient();

  const SearchValue = useRecoilValue(mainSearchState);
  const setSearch = useSetRecoilState(mainSearchState);

  /*필터 검색*/
  const handleSearchBtn = useCallback(
    async (values: IGetUserInfoRequest) => {
      setSearch(values);
      await queryClient.invalidateQueries({
        queryKey: ['useGetInfo', values],
      });
    },
    [setSearch, queryClient],
  );

  return (
    <div>
      <Form layout="horizontal" onFinish={handleSearchBtn}>
        <Row>
          <Form.Item
            label="이름/닉네임"
            name="title"
            initialValue={SearchValue?.value}
          >
            <Input />
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
