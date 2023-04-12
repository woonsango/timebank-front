import { css } from '@emotion/react';
import { Card, Input, Button, Typography } from 'antd';
import { useState } from 'react';

const SearchForm = () => {
  const { Text } = Typography;
  const [userNickName, setUserNickName] = useState<string>();
  const [userRealName, setUserRealName] = useState<string>();

  const onChangeSearchRealName = (value: any) => {
    setUserRealName(value);
  };

  const onChangeSearchNickName = (value: any) => {
    setUserNickName(value);
  };

  /*필터 검색*/
  const handleSearchBtn = () => {
    //이름과 닉네임으로 사용자 검색
  };

  return (
    <Card style={{ textAlign: 'center' }}>
      <Text strong>이름</Text>
      <Input
        css={{ width: 170, marginLeft: 20 }}
        onChange={onChangeSearchRealName}
      />
      <Text strong style={{ marginLeft: 90 }}>
        닉네임
      </Text>
      <Input
        css={{ width: 170, marginLeft: 20 }}
        onChange={onChangeSearchNickName}
      />
      <Button
        css={css`
          background: gray;
        `}
        style={{ width: 80, marginLeft: 70 }}
        type="primary"
        onClick={handleSearchBtn}
      >
        검색
      </Button>
    </Card>
  );
};
export default SearchForm;
