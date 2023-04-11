import { useQuery } from 'react-query';
import { apiRequest } from '../api/request';
import { API_URL } from '../api/urls';

export const useGetDealBoards = () => {
  return useQuery({
    queryKey: ['boards', 'deal'],
    queryFn: () => apiRequest.get(API_URL.DEAL_BOARDS),
  });
};
