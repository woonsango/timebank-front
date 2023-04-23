export const API_URL = {
  ADMIN_LOGIN: '/api/admins/login',
  COMMENTS: 'api/admins/comments/main',
  COMMENTS__SEARCH: 'api/admins/comments/search',
  COMMENTS__HIDE: '/api/admins/comments/hide',
  ADMIN_PASSWORD_CHANGE: '/api/admins/password/change', //첫로그인시 비밀번호 변경
  ADMIN_LOGOUT: '/api/admins/logout',
  NOTIFICATIONS: 'api/notifications',
  NOTIFICATIONS__SEARCH: 'api/notifications/search',
  ADMIN_REGISTER: '/api/admins/register', //관리자 가입
  ADMINS: '/api/admins',
};

/** 토큰을 넣지 않아도 되는 api url 모음 */
export const HEADER_NOT_REQUIRED_URLS = [API_URL.ADMIN_LOGIN];
