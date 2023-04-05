import { useQuery } from 'react-query';
import { apiRequest } from '../request';
import { API_URL } from '../urls';

export const useGetComments = () => {
  return useQuery({
    queryKey: 'comment',
    queryFn: () => apiRequest.get(API_URL.COMMENTS),
  });
};
