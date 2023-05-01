export const API_URL = {
  DEAL_BOARDS: '/api/deal-boards',
  FREE_BOARDS: '/api/free-boards',
  FREE_BOARDS_WRITE: '/api/free-boards/write',
  INQUIRY_WRITE: '/api/inquiry-boards/write',
  INQUIRY: 'api/inquiry-boards',
  NOTIFICATIONS: 'api/notifications',
  USER_INFO: '/api/users/get/',
  USER_INFO_POST: '/api/users/create/',
};

// 토큰 필요 없는 애들
export const HEADER_NOT_REQUIRED_URLS = [API_URL.DEAL_BOARDS];
