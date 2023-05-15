import { css } from '@emotion/react';
import { Input, Button, Form, Row } from 'antd';
import React, { useCallback } from 'react';
import { useQueryClient } from 'react-query';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import { mainSearchStateUserId } from './mainSearchState';
import { IGetUserInfoUserIdRequest } from '../../api/interfaces/IUser';
import { cssPushSearchFormStyle } from '../../components/PushSearchForm/PushSearchForm.styles';

const SearchForm = () => {
  const queryClient = useQueryClient();

  const SearchUserId = useRecoilValue(mainSearchStateUserId);
  const setSearchUserId = useSetRecoilState(mainSearchStateUserId);

  /*필터 검색*/
  const handleSearchBtn = useCallback(
    async (values: IGetUserInfoUserIdRequest) => {
      console.log(values);
      setSearchUserId(values);
      await queryClient.invalidateQueries({
        queryKey: ['userId', values],
      });
    },
    [setSearchUserId, queryClient],
  );

  return (
    <div css={cssPushSearchFormStyle}>
      <Form layout="horizontal" onFinish={handleSearchBtn}>
        <Row>
          <Form.Item
            label="UID"
            name="userId"
            initialValue={SearchUserId?.userId}
          >
            <Input placeholder="UID로 검색" />
          </Form.Item>
          <Form.Item
            label="닉네임/이름"
            name="value"
            //initialValue={SearchValue?.userId}
            style={{ marginLeft: '20px' }}
          >
            <Input placeholder="닉네임 또는 이름으로 검색" />
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
