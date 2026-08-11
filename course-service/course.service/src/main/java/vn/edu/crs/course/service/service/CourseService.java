package vn.edu.crs.course.service.service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.edu.crs.course.service.dto.CourseDTO;
import vn.edu.crs.course.service.entity.Course;
import vn.edu.crs.course.service.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class CourseService {

        private final CourseRepository courseRepository;

        // Lấy toàn bộ danh sách môn học
        public List<CourseDTO> getAll() {
            return courseRepository.findAll()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

        // Lấy môn học theo ID
        public CourseDTO getById(Long id) {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Khong tim thay mon hoc id = " + id));
            return toDTO(course);
        }

        // Tạo môn học mới
        public CourseDTO create(CourseDTO dto) {
            if (courseRepository.existsByTenMonHocIgnoreCase(dto.getTenMonHoc())) {
                throw new IllegalArgumentException("Ten mon hoc da ton tai");
            }
            Course course = new Course();
            course.setTenMonHoc(dto.getTenMonHoc());
            course.setSoTinChi(dto.getSoTinChi());
            course.setSoChoToiDa(dto.getSoChoToiDa());
            // Quy tắc nghiệp vụ: Khi mới tạo, số chỗ còn lại = số chỗ tối đa
            course.setSoChoConLai(dto.getSoChoToiDa());

            return toDTO(courseRepository.save(course));
        }

        // Cập nhật thông tin môn học
        public CourseDTO update(Long id, CourseDTO dto) {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Khong tim thay mon hoc id = " + id));

            course.setTenMonHoc(dto.getTenMonHoc());
            course.setSoTinChi(dto.getSoTinChi());
            course.setSoChoToiDa(dto.getSoChoToiDa());
            // Lưu ý: Không thay đổi soChoConLai qua API update thông thường này

            return toDTO(courseRepository.save(course));
        }

        // Xóa môn học
        public void delete(Long id) {
            if (!courseRepository.existsById(id)) {
                throw new NoSuchElementException("Khong tim thay mon hoc id = " + id);
            }
            courseRepository.deleteById(id);
        }

        // Mapper thủ công từ Entity sang DTO
        private CourseDTO toDTO(Course course) {
            return new CourseDTO(
                    course.getId(),
                    course.getTenMonHoc(),
                    course.getSoTinChi(),
                    course.getSoChoToiDa(),
                    course.getSoChoConLai()
            );
        }
    public Page<CourseDTO> search(String keyword, Pageable pageable) {
        Page<Course> page = (keyword == null || keyword.isBlank())
                ? courseRepository.findAll(pageable)
                : courseRepository.findByTenMonHocContainingIgnoreCase(keyword, pageable);
        return page.map(this::toDTO);
    }
    @Transactional
    public CourseDTO reserveSeat(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Khong tim thay mon hoc id = " + courseId));

        if (course.getSoChoConLai() <= 0) {
            throw new IllegalStateException("Mon hoc da het cho, khong the dang ky");
        }

        course.setSoChoConLai(course.getSoChoConLai() - 1);
        return toDTO(courseRepository.save(course));
    }

    @Transactional
    public CourseDTO releaseSeat(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Khong tim thay mon hoc id = " + courseId));

        if (course.getSoChoConLai() < course.getSoChoToiDa()) {
            course.setSoChoConLai(course.getSoChoConLai() + 1);
        }
        return toDTO(courseRepository.save(course));
    }
    }

