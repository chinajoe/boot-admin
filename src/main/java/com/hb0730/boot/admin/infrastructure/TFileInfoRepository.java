package com.hb0730.boot.admin.infrastructure;

import com.hb0730.boot.admin.project.course.prepare.entity.TFileInfoDO;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.shaded.commons.io.FilenameUtils;
import org.springframework.data.repository.CrudRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The interface T file info repository.
 *
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022 /5/3 23:12
 */
public interface TFileInfoRepository extends CrudRepository<TFileInfoDO, String> {
    /**
     * The constant FILE_BASE_URL.
     */
    String FILE_BASE_URL = "/course/";

    /**
     * Save all collection.
     *
     * @param files the files
     * @return the collection
     */
    default Collection<TFileInfoDO> saveAll(Collection<File> files) {
        return saveAll(FILE_BASE_URL, files);
    }

    /**
     * Save all collection.
     *
     * @param pathPrefix the path prefix
     * @param files      the files
     * @return the collection
     */
    default Collection<TFileInfoDO> saveAll(String pathPrefix, Collection<File> files) {
        if (null == files || files.isEmpty()) {
            return Collections.emptyList();
        }

        /* deal */
        List<TFileInfoDO> list = files.stream().map(file -> {
            String name = file.getName();
            long length = file.length();
            String strip = StringUtils.strip(pathPrefix, "/");
            String ossKey = "/" + strip + "/" + UUID.randomUUID() + "_" + name;
            long currentTimeMillis = System.currentTimeMillis();
            String extension = FilenameUtils.getExtension(name);
            return new TFileInfoDO(name, extension, String.valueOf(length),
                ossKey, currentTimeMillis, currentTimeMillis);
        }).collect(Collectors.toList());
        // save file infos
        Iterable<TFileInfoDO> tFileInfoDOS = saveAll(list);
        List<TFileInfoDO> idList = new ArrayList<>();
        tFileInfoDOS.forEach(idList::add);
        return idList;
    }
}
