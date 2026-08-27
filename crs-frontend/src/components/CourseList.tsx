import type { Course } from '../types/course';
import type { LoadState } from '../api/useCourses';

interface CourseListProps {
  courses: Course[];
  state: LoadState;
  errorMessage: string;
  onRetry: () => void;
  onEdit?: (course: Course) => void;
  onDelete?: (course: Course) => void;
  onRegister?: (course: Course) => void;
  registeringId?: number | null;
}

export default function CourseList({
  courses,
  state,
  errorMessage,
  onRetry,
  onEdit,
  onDelete,
  onRegister,
  registeringId,
}: CourseListProps) {
  if (state === 'loading') return <p>Đang tải danh sách môn học...</p>;

  if (state === 'error') {
    return (
      <div style={{ color: '#b91c1c' }}>
        <p>{errorMessage}</p>
        <button onClick={onRetry}>Thử lại</button>
      </div>
    );
  }

  if (state === 'empty') return <p>Không tìm thấy môn học nào phù hợp.</p>;

  const showActions = !!onEdit || !!onDelete || !!onRegister;

  return (
    <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: 12 }}>
      <thead>
        <tr style={{ textAlign: 'left', borderBottom: '2px solid #333' }}>
          <th style={{ padding: 8 }}>Tên môn học</th>
          <th style={{ padding: 8 }}>Số tín chỉ</th>
          <th style={{ padding: 8 }}>Số chỗ còn lại</th>
          {showActions && <th style={{ padding: 8 }}>Thao tác</th>}
        </tr>
      </thead>
      <tbody>
        {courses.map((course) => (
          <tr key={course.id} style={{ borderBottom: '1px solid #eee' }}>
            <td style={{ padding: 8 }}>{course.tenMonHoc}</td>
            <td style={{ padding: 8 }}>{course.soTinChi}</td>
            <td
              style={{
                padding: 8,
                color: course.soChoConLai === 0 ? '#b91c1c' : 'inherit',
                fontWeight: course.soChoConLai === 0 ? 'bold' : 'normal',
              }}
            >
              {course.soChoConLai} / {course.soChoToiDa}
            </td>
            {showActions && (
              <td style={{ padding: 8 }}>
                {onEdit && (
                  <button onClick={() => onEdit(course)} style={{ cursor: 'pointer' }}>
                    Sửa
                  </button>
                )}
                {onDelete && (
                  <button
                    onClick={() => onDelete(course)}
                    style={{ marginLeft: 8, color: '#b91c1c', cursor: 'pointer' }}
                  >
                    Xóa
                  </button>
                )}
                {onRegister && (
                  <button
                    onClick={() => onRegister(course)}
                    disabled={course.soChoConLai === 0 || registeringId === course.id}
                    style={{ cursor: course.soChoConLai === 0 ? 'not-allowed' : 'pointer' }}
                  >
                    {registeringId === course.id
                      ? 'Đang đăng ký...'
                      : course.soChoConLai === 0
                      ? 'Hết chỗ'
                      : 'Đăng ký'}
                  </button>
                )}
              </td>
            )}
          </tr>
        ))}
      </tbody>
    </table>
  );
}