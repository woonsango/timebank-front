//리다이렉트될 화면
//kakaoRedirectHandler.tsx

import React, { useEffect } from 'react';
import axios from 'axios';

const kakaoRedirectHandler = () => {
  useEffect(() => {
    let authorizationCode = new URL(window.location.href).searchParams.get(
      'code',
    );
    let grant_type = 'authorization_code';
    let client_id = '79587b639a3a9ca1c9433fa63bc55863';
  });
};

export default kakaoRedirectHandler;

//let authorizationCode = new URL(window.location.href).searchParams.get('code',)
