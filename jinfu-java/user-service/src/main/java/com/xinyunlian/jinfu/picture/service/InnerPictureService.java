package com.xinyunlian.jinfu.picture.service;

import com.xinyunlian.jinfu.picture.dao.PictureDao;
import com.xinyunlian.jinfu.picture.entity.PicturePo;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by JL on 2016/9/18.
 */
@Service
public class InnerPictureService {

    @Autowired
    private PictureDao pictureDao;

    @Transactional
    public void updatePicture(Long pictureId, String parentId, EPictureType pictureType) {
        if (parentId != null) {
            List<PicturePo> picturePos = pictureDao.findByParentIdAndPictureType(parentId, pictureType);
            if (!picturePos.isEmpty() && !picturePos.get(0).getPictureId().equals(pictureId)) {
                pictureDao.delete(picturePos);
            }
            if (pictureId != null) {
                PicturePo p = pictureDao.findOne(pictureId);
                if (p != null) {
                    p.setParentId(parentId);
                    pictureDao.save(p);
                }
            }

        }
    }
}
