package com.xinyunlian.jinfu.picture.dao;

import com.xinyunlian.jinfu.picture.entity.PicturePo;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 图片DAO接口
 *
 * @author KimLL
 */
public interface PictureDao extends JpaRepository<PicturePo, Long>, JpaSpecificationExecutor<PicturePo> {

    List<PicturePo> findByParentIdAndPictureType(String parentId, EPictureType pictureType);

    @Query("from PicturePo i where i.parentId = ?1 order by i.pictureId desc")
    List<PicturePo> findByParentId(String parentId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from picture where parent_id = ?1 and picture_type = ?2")
    void delete(String parentId,String pictureType);

    void deleteByParentId(String parentId);
}
