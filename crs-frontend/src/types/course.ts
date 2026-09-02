// Thêm vào cuối file types/course.ts hiện tại:
export interface CourseFormValues {
  tenMonHoc: string;
  soTinChi: string; // Dùng string để kiểm soát input rỗng, ép kiểu Number khi gửi đi
  soChoToiDa: string;
}

export const emptyCourseForm: CourseFormValues = {
  tenMonHoc: '',
  soTinChi: '',
  soChoToiDa: '',
};