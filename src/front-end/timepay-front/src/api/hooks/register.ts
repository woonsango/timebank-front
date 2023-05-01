import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';
import {
  IPostFreeBoard,
  IGetFreeBoard,
  IPostDealBoard,
} from '../interfaces/IPost';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useGetFreeBoards = () => {
  return useQuery<AxiosResponse<IGetFreeBoard>, AxiosError>({
    queryKey: ['useGetFreeBoards'],
    queryFn: () => apiRequest.get(API_URL.FREE_BOARDS),
    refetchOnWindowFocus: false,
  });
};

export const useCreateFreeBoards = () => {
  return useMutation<AxiosResponse<boolean>, AxiosError, IPostFreeBoard>({
    mutationKey: 'usePostFreeBoards',
    mutationFn: (data) =>
      apiRequest.post(API_URL.FREE_BOARDS_WRITE, {
        ...data,
      }),
  });
};

export const useCreateDealBoards = () => {
  return useMutation<AxiosResponse<boolean>, AxiosError, IPostFreeBoard>({
    mutationKey: 'usePostFreeBoards',
    mutationFn: (data) =>
      apiRequest.post(API_URL.FREE_BOARDS_WRITE, {
        ...data,
      }),
  });
};
