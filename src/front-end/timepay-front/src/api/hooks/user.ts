import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { IAgency } from '../interfaces/IAgency';
import {
  IGetUserBoardRequest,
  IGetUserBoardResponse,
  IGetUserCommentRequest,
  IGetUserCommentResponse,
} from '../interfaces/IUser';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useLogout = () => {
  return useMutation<AxiosResponse<any>, AxiosError, any>({
    mutationKey: 'useLogout',
    mutationFn: () => apiRequest.post(API_URL.LOGOUT),
  });
};

export const useDelete = () => {
  return useMutation<AxiosResponse<any>, AxiosError>({
    mutationKey: 'useDelete',
    mutationFn: () => apiRequest.delete(API_URL.DELETE),
  });
};

export const useGetUserInfo = () => {
  return useQuery<AxiosResponse<{ body: IAgency }>, AxiosError>({
    queryKey: ['useGetUserInfo'],
    queryFn: () => apiRequest.get(API_URL.USER_INFO_GET),
    refetchOnWindowFocus: false,
    retry: false, // api 호출 실패해도 계속 호출하지 않음
    onError: (err: any) => {
      console.log('[ERROR] useGetUserInfo:', err);
    },
  });
};

export const useGetUserBoards = (params?: any) => {
  return useQuery<AxiosResponse<any>, AxiosError>({
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

export const useGetUserComments = (params: any) => {
  return useQuery<AxiosResponse<any>, AxiosError>({
    queryKey: ['useGetUserComments', params],
    queryFn: () =>
      apiRequest.get(`${API_URL.API_USERS__MYPAGE__COMMENT}`, {
        params: params,
      }),
    refetchOnWindowFocus: false,
    retry: false, // api 호출 실패해도 계속 호출하지 않음
    onError: (err: any) => {
      console.log('[ERROR] useGetUserComments:', err);
    },
  });
};
