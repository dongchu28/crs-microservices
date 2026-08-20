import { useEffect, useState } from 'react';
import { getCourses } from './api/courseApi';
import type { Course } from './types/course';

function App() {
  const [courses, setCourses] = useState<Course[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    getCourses()
      .then((res) => setCourses(res.data.content))
      .catch((err) => {
        console.error(err);
        setError('Không kết nối được tới hệ thống. Kiểm tra lại api-gateway đã chạy chưa.');
      });
  }, []);

  return (
    <div style={{ padding: 24, fontFamily: 'sans-serif' }}>
      <h1>Kiểm tra kết nối CRS qua Gateway</h1>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <pre>{JSON.stringify(courses, null, 2)}</pre>
    </div>
  );
}

export default App;