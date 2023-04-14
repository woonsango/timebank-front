export const API_URL = {
  ADMIN_LOGIN: '/api/admins/login',
  COMMENTS: 'api/admins/reports/main', // 전체 댓글 리스트 조회
  ADMIN_PASSWORD_CHANGE: '/api/admins/password/change', //첫로그인시 비밀번호 변경
};

/** 토큰을 넣지 않아도 되는 api url 모음 */
export const HEADER_NOT_REQUIRED_URLS = [API_URL.ADMIN_LOGIN];
