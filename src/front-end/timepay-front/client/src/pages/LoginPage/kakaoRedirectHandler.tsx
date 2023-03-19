//리다이렉트될 화면
//kakaoRedirectHandler.tsx

import { useCallback, useEffect } from 'react';
import axios from 'axios';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { PATH } from '../../utils/paths';

const KakaoRedirectHandler = () => {
  const [AuthorizationCode, setAuthorizationCode] = useState<string>();

  const navigate = useNavigate(); //history

  const handleLogin = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  let authorizationCode = new URL(window.location.href).searchParams.get(
    'code',
  );

  console.log('인가 코드(Authorization Code): ', authorizationCode);

  const fetchAuthorizationCode = async () => {
    axios({
      method: 'post',
      url: 'http://localhost:5000/authorization',
      data: authorizationCode,
    })
      .then((res) => {
        console.log(res); //서버 응답

        //서버로부터 정상적으로 토큰이 넘어오면
        //! 토큰 저장하고?
        //회원가입(Join Page로 화면 전환)
        handleLogin(PATH.JOIN);
      })
      .catch((err) => {
        console.log('카카오 로그인 에러', err);
      });
  };

  const handleAuthorization = (props: any) => {
    setAuthorizationCode(props);

    fetchAuthorizationCode();
  };

  useEffect(() => {
    //리다이렉트 페이지 진입 시, 실행
    //서버에 인가 코드 전송(string)
    console.log('Enter Redirect Page!');
    handleAuthorization(authorizationCode);
  }, []);

  return <div></div>;
};

export default KakaoRedirectHandler;
