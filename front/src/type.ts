export interface UserInfo {
    username: string;
    email: string;
    name: string;
    phoneNumber: string;
    birthDate: string;
    avatar: string | null;
    status: string;
    createTime: string;
  }
  
  export interface ApiResponse<T> {
    msg: string;
    code: number;
    data: T;
  }