package com.hb0730.boot.admin.infrastructure;

import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseDO;
import org.springframework.data.repository.CrudRepository;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/3 23:12
 */
public interface TCourseRepository extends CrudRepository<TCourseDO, String> {
}
