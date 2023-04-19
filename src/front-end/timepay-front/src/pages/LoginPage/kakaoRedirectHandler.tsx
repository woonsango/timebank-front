//리다이렉트될 화면
//kakaoRedirectHandler.tsx

import { useCallback, useEffect } from 'react';
import axios from 'axios';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { PATH } from '../../utils/paths';

const KakaoRedirectHandler = () => {
  const [AuthorizationCode, setAuthorizationCode] = useState<string>();
  const [url, setUrl] = useState<string>();

  const navigate = useNavigate(); //history

  const goToJoin = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate],
  );

  let authorizationCode = new URL(window.location.href).searchParams.get(
    'code',
  );

  const GetAuthorizationCode = async () => {};

  const handleAuthorization = (props: any) => {
    setAuthorizationCode(props);

    //GetAuthorizationCode(); //서버 전송 테스트 함수

    goToJoin(PATH.JOIN); //서버 적용x
  };

  useEffect(() => {
    //리다이렉트 페이지 진입 시, 실행
    //서버에 인가 코드 전송(string)
    console.log('Enter Redirect Page!');
    console.log('인가 코드(Authorization Code): ', authorizationCode);

    handleAuthorization(authorizationCode);

    const temp = 'http://localhost:8080/login/code=' + authorizationCode;
    //setUrl(temp);
    console.log(temp);
    console.log('entire: ', new URL(window.location.href));

    //console.log('url 주소: ', url);

    //GET
  }, []);

  return <div></div>;
};

export default KakaoRedirectHandler;
