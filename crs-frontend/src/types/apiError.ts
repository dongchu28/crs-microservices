export interface ApiErrorResponse {
  message?: string;
  [field: string]: string | undefined; // Dành cho trường hợp lỗi validation
}