package vn.edu.crs.course.service.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.crs.course.service.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByTenMonHocIgnoreCase(String tenMonHoc);

    // Tìm kiếm chứa keyword (không phân biệt hoa thường) kèm phân trang
    Page<Course> findByTenMonHocContainingIgnoreCase(String keyword, Pageable pageable);
}


