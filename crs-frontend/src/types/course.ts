export interface Course {
  id: number;
  tenMonHoc: string;
  soTinChi: number;
  soChoToiDa: number;
  soChoConLai: number;
}

export interface PagedResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

// Bổ sung thêm cho Buổi 7:
export interface CourseFormValues {
  tenMonHoc: string;
  soTinChi: string;  // Dùng string trong form để dễ xử lý ô trống, sẽ parse thành Number khi gửi API
  soChoToiDa: string;
}

export const emptyCourseForm: CourseFormValues = {
  tenMonHoc: '',
  soTinChi: '',
  soChoToiDa: '',
};