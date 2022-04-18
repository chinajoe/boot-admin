package com.hb0730.boot.admin.commons.utils;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.hb0730.boot.admin.exceptions.JsonException;
import com.hb0730.boot.admin.project.system.dict.model.vo.DictVO;
import com.hb0730.boot.admin.project.system.dict.service.IDictService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 数据字典
 *
 * @author bing_huang
 * @since 3.0.0
 */
public class DictUtils {
    /**
     * 获取字典项值
     *
     * @param type 字典类型
     * @param name 字典项名称
     * @return 字典项值
     */
    @Nullable
    public static String getEntryValue(@Nonnull String type, @Nonnull String name) {
        Assert.hasText(type, "字典类型不为空");
        Assert.hasText(name, "字典项名称不为空");
        IDictService service = SpringUtil.getBean(IDictService.class);
        List<DictVO> cache = service.getCache();
        if (CollectionUtils.isEmpty(cache)) {
            return "";
        }
        try {
            Optional<DictVO.DictEntryVO> entryValue = JsonUtils.jsonToList(JsonUtils.objectToJson(cache),
                    DictVO.class)
                .stream()
                .filter(dictType -> type.equals(dictType.getType()))
                .map(DictVO::getEntry)
                .flatMap(List::stream)
                .filter(entry -> name.equals(entry.getLabel()))
                .findFirst();
            if (entryValue.isPresent()) {
                return entryValue.get().getValue();
            } else {
                return "";
            }
        } catch (JsonException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        String secretId = "AKIDVtGY4Zz4P1zm_7m3GZv2yYQ0iyBefGagtIlJoxubGFdBOW1Vk43Ub-i3k6ujqosA";
        String secretKey = "zjnzril4IGOcpzcfOzjYUr4naKkyUwBP4qgFfT52jYA=";
        String oringalFileName = "2022%E5%91%A8%E6%97%A5.png";
        String randomFileName = "l22y15of0psv.png";
        long fileSize = 16082;

        /* sign */
        String qSignAlgorithm = "sha1";
        String qAk = secretId;
        String qSignTime = "1650179104;1650179704"; // startTime;expiredTime
        String qKeyTime = "1650179104;1650179704"; // startTime;expiredTime
        String method = "put";
        String pathName = "/appzFG9dgU09033/image/b_u_5fd3ab12dcefc_4EFEH1Jq/" + randomFileName;
        final String contentDispositionEncode = URLEncodeUtil.encodeAll("attachment; filename=" + oringalFileName);
        String headerToStr = new StringBuilder()
            .append("content-disposition").append("=").append(contentDispositionEncode)
            .append("&")
            .append("content-length").append("=").append(fileSize)
            .toString();

        /* signKey */
        String signKey = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, secretKey).hmacHex(qKeyTime);
        // [method, pathname, util.obj2str(queryParams, true), util.obj2str(headers, true), ''].join('\n');
        String formatString = StringUtils.join(
            Arrays.asList(method, pathName, "", headerToStr, ""), "\n"
        );
        // ['sha1', qSignTime, CryptoJS.SHA1(formatString).toString(), ''].join('\n');
        String toSign = StringUtils.join(
            Arrays.asList(qSignAlgorithm, qSignTime, DigestUtils.sha1Hex(formatString), ""), "\n"
        );
        final String hmacHex = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, signKey).hmacHex(toSign);
        System.out.println(hmacHex);
        System.out.println(hmacHex.equals("9a5c5412853ffb7e600f531190399972bfa86a79"));
    }
}
