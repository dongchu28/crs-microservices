package vn.edu.crs.course.service.Controller;
import vn.edu.crs.course.service.dto.CourseDTO;
import vn.edu.crs.course.service.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/courses")
@RequiredArgsConstructor
    public class InternalCourseController {


        private final CourseService courseService;

        @PatchMapping("/{id}/reserve-seat")
        public CourseDTO reserveSeat(@PathVariable Long id) {
            return courseService.reserveSeat(id);
        }

        @PatchMapping("/{id}/release-seat")
        public CourseDTO releaseSeat(@PathVariable Long id) {
            return courseService.releaseSeat(id);
        }
    }

