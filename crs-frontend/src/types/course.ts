export interface Course {
  id: number;
  tenMonHoc: string;
  soTinChi: number;
  soChoToiDa: number;
  soChoConLai: number;
}

// Cấu trúc phân trang trả về từ Spring Data JPA (Page<CourseDTO>)
export interface PagedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number; // trang hiện tại (bắt đầu từ 0)
  size: number;
}