import { AxiosError, AxiosResponse } from 'axios';
import { useQuery } from 'react-query';
import {
  IGetUserBoardRequest,
  IGetUserBoardResponse,
} from '../interfaces/IUser';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useGetUserBoards = (params: IGetUserBoardRequest) => {
  return useQuery<AxiosResponse<IGetUserBoardResponse>, AxiosError>({
    queryKey: ['useGetUserBoards', params],
    queryFn: () =>
      apiRequest.get(`${API_URL.API_USERS__MYPAGE__BOARD}`, { params: params }),
    refetchOnWindowFocus: false,
    retry: false, // api 호출 실패해도 계속 호출하지 않음
    onError: (err: any) => {
      console.log('[ERROR] useGetUserBoards:', err);
    },
  });
};
