package com.hb0730.boot.admin.infrastructure;

import com.hb0730.boot.admin.project.course.prepare.entity.TPlatformDO;
import org.springframework.data.repository.CrudRepository;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022/5/3 23:12
 */
public interface TPlatformRepository extends CrudRepository<TPlatformDO, String> {
}
