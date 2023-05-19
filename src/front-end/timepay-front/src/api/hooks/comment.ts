import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { IComment, IPostComment } from '../interfaces/IComment';
import { IPost } from '../interfaces/IPost';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useGetComment = () => {
  return useQuery<AxiosResponse<IComment>, AxiosError>({
    queryKey: ['useGetComment'],
    queryFn: () => apiRequest.get(API_URL.DEAL_BOARDS_COMMENT),
    refetchOnWindowFocus: false,
  });
};

export const useCreateComment = () => {
  return useMutation<AxiosResponse<boolean>, AxiosError, IPostComment>({
    mutationKey: 'usePostComment',
    mutationFn: (data) =>
      apiRequest.post(API_URL.DEAL_BOARDS_COMMENT_WRITE, {
        ...data,
      }),
  });
};
