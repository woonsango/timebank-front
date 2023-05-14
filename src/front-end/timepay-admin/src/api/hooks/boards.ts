import { useMutation, useQuery } from 'react-query';
import {
  IGetBoardRequest,
  IGetBoardResponse,
} from '../interfaces/IBoard';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useGetBoards = (params?: IGetBoardRequest) => {
  return useQuery<AxiosResponse<IGetBoardResponse>, AxiosError>({
    queryKey: ['useGetBoards', params],
    queryFn: () =>
      apiRequest.get(API_URL.BOARDS__SEARCH, {
        params: {
          ...params,
          [params?.authorSearchKeyword as string]: params?.authorSearchValue,
          authorSearchKeyword: null,
          authorSearchValue: null,
        },
      }),
    refetchOnWindowFocus: false,
  });
};

