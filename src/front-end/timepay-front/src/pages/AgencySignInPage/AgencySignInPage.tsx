import { Button, Form, Input, message, Typography } from 'antd';
import { useCallback, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { usePostAgencyLogin } from '../../api/hooks/agency';
import agencyLogo from '../../assets/images/icons/agency-logo.png';
import { PATH } from '../../utils/paths';
import { getTokenFromCookie, setTokenToCookie } from '../../utils/token';
import { cssAgencySignInPaeStyle } from './AgencySignInPage.styles';

const AgencySignInPage = () => {
  const navigate = useNavigate();
  const [messageApi, contextHolder] = message.useMessage();

  const postAgencyLoginMutation = usePostAgencyLogin();

  const handleOnLinkAgencySignUp = useCallback(() => {
    navigate(PATH.AGENCY_SIGN_UP);
  }, [navigate]);

  const handleOnFinishLogin = useCallback(
    async (values: any) => {
      await postAgencyLoginMutation.mutateAsync(values, {
        onSuccess: (result) => {
          setTokenToCookie(result.data.jwt, 1);
          messageApi.open({
            type: 'success',
            content: '로그인 성공했습니다.',
            duration: 1,
            onClose: () => navigate(PATH.HOME),
          });
        },
        onError: (err) => {
          console.log(err);
          messageApi.open({
            type: 'error',
            content: (
              <>
                오류 발생: <br />
                {err}
              </>
            ),
          });
        },
      });
    },
    [postAgencyLoginMutation, messageApi, navigate],
  );

  useEffect(() => {
    // 이미 토큰이 있다면 자동 로그인
    if (getTokenFromCookie()) navigate(PATH.HOME);
  }, []);

  return (
    <div css={cssAgencySignInPaeStyle}>
      {contextHolder}
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
      <Button type="link" onClick={handleOnLinkAgencySignUp}>
        회원가입 바로가기
      </Button>
    </div>
  );
};

export default AgencySignInPage;
