export const API_URL = {
  DEAL_BOARDS: '/api/deal-boards',
  DEAL_BOARDS_DELETE: '/api/deal-boards/delete',
  DEAL_BOARDS__SEARCH: 'deal-boards/search',
  DEAL_REPORT: '/deal-boards/:id/report',
  DEAL_BOARDS_COMMENT: 'api/deal-boards/comments/:id/applied',
  DEAL_BOARDS_COMMENT_WRITE: '/api/deal-boards/comments/write/:id',
  FREE_BOARDS: '/api/free-boards',
  FREE_BOARDS_WRITE: '/api/free-boards/write',
  DEAL_BOARDS_WRITE: '/api/deal-boards/write/help', // 도움받기
  INQUIRY_WRITE: '/api/inquiry-boards/write',
  INQUIRY: 'api/inquiry-boards',
  NOTIFICATIONS: 'api/notifications',
  ORGANIZATIONS_REGISTER: '/api/organizations/register',
  ORGANIZATIONS_LOGIN: '/api/organizations/login',
  ORGANIZATIONS_LOGOUT: '/api/organizations/logout',
  ORGANIZATIONS_EDIT: '/api/organizations/update',
  ORGANIZATIONS_DELETE: '/api/organizations/delete',
  USER_INFO_GET: '/api/users/mypage/',
  USER_INFO_POST: '/api/users/create/',
  USER_INFO_PUT: '/api/users/mypage/update',
  MY_VOLUNTEER: '/api/users/mypage/certification',
  USERS__CATEGORY: '/api/users/category',
  API_USERS__MYPAGE__BOARD: '/api/users/mypage/board',
  API_USERS__MYPAGE__COMMENT: '/api/users/mypage/comment',
  LOGOUT: '/api/users/logout',
  DELETE: '/api/users/delete',
  INSTANT_MATCHING: '/api/instant-matching',
  DONATION_WRITE: '/api/organizations/donate/write',
};

// 토큰 필요 없는 애들
export const HEADER_NOT_REQUIRED_URLS = [
  API_URL.DEAL_BOARDS,
  API_URL.ORGANIZATIONS_REGISTER,
  API_URL.ORGANIZATIONS_LOGIN,
];

// form data로 전송 필요한 url
export const FORM_DATA_REQUIRED_URLS = [
  API_URL.ORGANIZATIONS_REGISTER,
  API_URL.ORGANIZATIONS_EDIT,
  API_URL.USER_INFO_POST,
  API_URL.USER_INFO_PUT,
  API_URL.DEAL_BOARDS_WRITE,
];
