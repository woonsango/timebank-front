export interface IComment {
  commentId: number; // 게시글의 id (= postId)
  id: number; // 댓글 id
  parentId: null; // 대댓글을 위한
  content: string;
  createdAt?: string;
  updatedAt?: string;
  isApplied: boolean;
  isAdopted: boolean;
}

export interface Comment {
  commentId: number; // 게시글의 id (= postId)
  id: number; // 댓글 id
  parentId: number | null; // 대댓글을 위한
  content: string;
  createdAt?: string;
  updatedAt?: string;
  isApplied: boolean;
  isAdopted: boolean;
  replies: string[];
}
