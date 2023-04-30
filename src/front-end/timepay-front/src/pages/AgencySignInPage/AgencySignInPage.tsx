import { Button, Form, Input, Typography } from 'antd';
import { useCallback } from 'react';
import agencyLogo from '../../assets/images/icons/agency-logo.png';
import { cssAgencySignInPaeStyle } from './AgencySignInPage.styles';

const AgencySignInPage = () => {
  const handleOnFinishLogin = useCallback((values: any) => {
    console.log(values);
  }, []);

  return (
    <div css={cssAgencySignInPaeStyle}>
      <div className="title">
        <Typography.Text>모두에게 동일한 시간</Typography.Text>
        <Typography.Text>시간을 거래하는 타임페이</Typography.Text>
      </div>
      <img src={agencyLogo} alt="기관용 로그인" />
      <Typography.Text>기관 사용자 로그인</Typography.Text>
      <Form layout="horizontal" onFinish={handleOnFinishLogin}>
        <Form.Item
          name="id"
          rules={[
            { required: true, message: '아이디를 입력해주세요' },
            { type: 'email', message: '아이디를 이메일 형식으로 입력해주세요' },
          ]}
        >
          <Input style={{ width: 216 }} placeholder="아이디" />
        </Form.Item>
        <Form.Item
          name="pw"
          rules={[{ required: true, message: '비밀번호를 입력해주세요' }]}
        >
          <Input
            style={{ width: 216 }}
            placeholder="비밀번호"
            type="password"
          />
        </Form.Item>
        <Button type="primary" htmlType="submit">
          로그인
        </Button>
      </Form>
      <Button type="link">회원가입 바로가기</Button>
    </div>
  );
};

export default AgencySignInPage;
