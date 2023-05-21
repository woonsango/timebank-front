import { AxiosError, AxiosResponse } from 'axios';
import { useMutation, useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { PATH } from '../../utils/paths';
import {
  IDonationBoard,
  IPostDonationBoardWriteRequest,
  IPostDonationBoardWriteResponse,
} from '../interfaces/IDonation';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const usePostDonationBoardWrite = () => {
  return useMutation<
    AxiosResponse<IPostDonationBoardWriteResponse>,
    AxiosError,
    IPostDonationBoardWriteRequest
  >({
    mutationKey: 'usePostDonationBoardWrite',
    mutationFn: (data: IPostDonationBoardWriteRequest) =>
      apiRequest.post(API_URL.DONATION_WRITE, data),
  });
};

export const useGetDonationBoardWithId = (boardId?: number) => {
  const navigate = useNavigate();
  return useQuery<AxiosResponse<IDonationBoard>, AxiosError>({
    queryKey: ['useGetDonationBoardWithId', boardId],
    queryFn: () => apiRequest.get(`${API_URL.DONATION_BOARD_ID}/${boardId}`),
    refetchOnWindowFocus: false,
    retry: false, // api 호출 실패해도 계속 호출하지 않음
    onError: (err: any) => {
      console.log('[ERROR] useGetDonationBoardWithId', err);
      navigate(PATH.HOME);
    },
  });
};
