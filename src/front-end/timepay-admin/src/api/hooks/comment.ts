import { Modal } from 'antd';
import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';
import {
  IGetCommentRequest,
  IGetCommentResponse,
  IPatchCommentRequest,
} from '../interfaces/IComment';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useGetComments = (params?: IGetCommentRequest) => {
  console.log(params);
  return useQuery<AxiosResponse<IGetCommentResponse>, AxiosError>({
    queryKey: ['useGetComments', params],
    queryFn: () =>
      params && (params.originBoardId || params.writerSearchValue)
        ? apiRequest.get(API_URL.COMMENTS__SEARCH, {
            params: {
              ...params,
              writerSearchKeyword: null,
              writerSearchValue: null,
            },
          })
        : apiRequest.get(API_URL.COMMENTS),
    refetchOnWindowFocus: false,
    retry: false, // api 호출 실패해도 계속 호출하지 않음
    onError: (err: any) => {
      console.log('[ERROR] useGetComments:', err);
      Modal.error({
        type: 'error',
        content: '에러가 발생했습니다.',
      });
    },
  });
};

export const useGetComments = () => {
  return useQuery({
    queryKey: 'comment',
    queryFn: () => apiRequest.get(API_URL.COMMENTS),
  });
};
