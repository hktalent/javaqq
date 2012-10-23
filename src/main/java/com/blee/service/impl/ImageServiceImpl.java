package com.blee.service.impl;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blee.constant.QQURLS;
import com.blee.constant.Static;
import com.blee.exception.ServiceException;
import com.blee.http.RequestContext;
import com.blee.http.ResponseContext;
import com.blee.http.impl.DefaultRequestContext;
import com.blee.service.ImageService;
import com.blee.util.ServiceUtil;

public class ImageServiceImpl implements ImageService {
    
    private static Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public Icon loadVerifyIcon(String qqNumber) {
        log.info("获取qq[{}]的验证码", qqNumber);
        RequestContext request = new DefaultRequestContext().usingGet().usingUrl(QQURLS.QQ_VERIFY_URL);
        request.addParam("aid", Static.APP_ID);
        request.addParam("r", Math.random());
        request.addParam("uin", qqNumber);
        try {
            ResponseContext response = ServiceUtil.getHttpService().execute(request);
            ByteArrayInputStream bai = new ByteArrayInputStream(response.getAsByteArray());
            Image image = ImageIO.read(bai);
            return new ImageIcon(image);
        } catch (IOException e) {
            log.error("can't load verify code", e);
            throw new ServiceException(e);
        }
    }

}
