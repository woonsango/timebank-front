import { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';
import {
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
