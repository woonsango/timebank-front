//카카오 로그인 페이지

const REST_API_KEY = '79587b639a3a9ca1c9433fa63bc55863'; //카카오-타임페이 커뮤니티 REST API 앱 키
const REDIRECT_URI = 'http://localhost:3000/oauth/redirect/kakao'; //카카오-타임페이 커뮤니티 Redirect URI, 로컬 IP 주소 넣기

const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`; //`` '' 주의

export default KAKAO_AUTH_URL;
