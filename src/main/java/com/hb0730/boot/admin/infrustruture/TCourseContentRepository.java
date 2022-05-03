package com.hb0730.boot.admin.infrustruture;

import com.hb0730.boot.admin.project.course.orchestrate.entity.TCourseContentDO;
import org.springframework.data.repository.CrudRepository;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/3 23:12
 */
public interface TCourseContentRepository extends CrudRepository<TCourseContentDO, String> {
}
