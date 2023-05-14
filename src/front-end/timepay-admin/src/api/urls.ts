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
  REPORTS: '/api/admins/reports/main',
  REPORTS__SEARCH: '/api/admins/reports/search',
  REPORTS__PENALTY: '/api/admins/reports/penalty',
  CATEGORIES: '/api/admins/categories/main',
  CATEGORIES__CREATE: '/api/admins/categories/create',
  CATEGORIES__UPDATE: '/api/admins/categories/update',
  BOARDS: '/api/admins/boards',
  BOARDS__SEARCH: '/api/admins/boards/search',
};

/** 토큰을 넣지 않아도 되는 api url 모음 */
export const HEADER_NOT_REQUIRED_URLS = [API_URL.ADMIN_LOGIN];

// form data로 전송 필요한 url
export const FORM_DATA_REQUIRED_URLS = [API_URL.NOTIFICATIONS];
