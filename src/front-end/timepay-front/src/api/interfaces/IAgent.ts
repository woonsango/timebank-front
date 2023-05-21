export interface IGetAgentResponse {
  status: boolean;
  agentName: string;
  myUid: number;
  myName: string;
}

export interface IPostAgentRequest {
  uid: number;
}
