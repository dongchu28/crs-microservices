export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  userId: number; // Thêm trường userId
  token: string;
  username: string;
  role: 'ADMIN' | 'STUDENT';
}