package com.hb0730.boot.admin.infrastructure;

import com.hb0730.boot.admin.project.course.prepare.entity.TFileMappingDO;
import org.springframework.data.repository.CrudRepository;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/3 23:12
 */
public interface TFileMappingRepository extends CrudRepository<TFileMappingDO, String> {
}
