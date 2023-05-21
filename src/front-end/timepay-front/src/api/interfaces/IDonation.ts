export interface IPostDonationBoardWriteRequest {
  category: string;
  content: string;
  targetTimePay: number;
  title: string;
}

export interface IPostDonationBoardWriteResponse {
  id: number;
  title: string;
  content: string;
  type: string;
  targetTimePay: number;
  donateTimePay: number;
  category: string;
}

export interface IDonationBoard {
  id: number;
  title: string;
  content: string;
  type: string;
  targetTimePay: number;
  donateTimePay: number;
  category: string;
}
