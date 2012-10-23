package com.blee.qq.impl;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blee.exception.ServiceException;
import com.blee.model.Check;
import com.blee.qq.VerifyCodeProvider;
import com.blee.util.ServiceUtil;
import com.blee.util.StringUtils;

public class DefaultVerifyCodeProvider implements VerifyCodeProvider {
    
    private static Logger log = LoggerFactory.getLogger(DefaultVerifyCodeProvider.class);

    @Override
    public String getVerifyCode(Check check) {
        if(check.getEnVerifyCode().startsWith("!")) {
            return check.getEnVerifyCode();
        } else {//需要读取图片验证码
            Icon icon = ServiceUtil.getImageService().loadVerifyIcon(check.getQqNumber());
            if (icon != null) {
                String vCode = (String) JOptionPane.showInputDialog(null, "验证码：", "请输入验证码：", JOptionPane.QUESTION_MESSAGE, icon, null, null);
                log.info("用户输入的验证码是:{}", vCode);
                if (StringUtils.isEmpty(vCode)) {
                    JOptionPane.showMessageDialog(null, "验证码输入有误!");
                }
                return vCode;
            } else {
                throw new ServiceException("验证码获取失败！请重试。");
            }
        }
    }

}
