import { IPostState, IPostType } from '../api/interfaces/IPost';
import dayjs from 'dayjs';

export const getStatus: (boardStatus?: string) => IPostState = (
  boardStatus?: string,
) => {
  if (boardStatus)
    switch (boardStatus) {
      case 'MATCHING_IN_PROGRESS':
        return '매칭중';
      case 'MATCHING_COMPLETE':
        return '매칭완료';
      case 'ACTIVITY_IN_PROGRESS':
        return '활동중';
      case 'ACTIVITY_COMPLETE':
        return '활동완료';
      case 'ACTIVITY_DELAY':
        return '활동지연';
      case 'ACTIVITY_CANCEL':
        return '활동취소';
      default:
        return '매칭중';
    }
  else return '매칭중';
};

export const getType: (type?: string) => IPostType = (type?: string) => {
  if (type)
    switch (type) {
      case 'help':
        return '도움요청';
      case 'helper':
        return '같이하기';
      case 'event':
        return '기부하기';

      default:
        return '도움요청';
    }
  else return '도움요청';
};

export const getDateDiffToday = (createdAt: string) => {
  console.log(dayjs().diff(dayjs().add(-1, 'days'), 'days'));
  const diffDay = dayjs().diff(
    dayjs(createdAt, 'YYYY-MM-DDTHH:mm:ss.SSS'),
    'minutes',
  );
  if (diffDay <= 60 * 24) {
    if (diffDay < 60) return `${diffDay}분 전`;
    else {
      return `${Math.floor(diffDay / 60)}시간 전`;
    }
  }
  if (diffDay <= 60 * 24 * 3) {
    return `${Math.floor(diffDay / (60 * 24))} 일 전`;
  }
  return createdAt.split('T')[0];
};
