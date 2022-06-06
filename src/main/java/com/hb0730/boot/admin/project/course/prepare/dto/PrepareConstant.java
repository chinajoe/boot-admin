package com.hb0730.boot.admin.project.course.prepare.dto;

/**
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022-06-06
 **/
public interface PrepareConstant {
    String EXPORT_TEMPLATE_PREFIX = "<html lang=\"utf-8\">\n" +
        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n" +
        "<style>\n" +
        "  .box {\n" +
        "    background-image: url('https://qk-cosole-1309456451.cos.ap-hongkong.myqcloud.com/bg/body.png');\n" +
        "    background-repeat: no-repeat;\n" +
        "    background-size: 100.09% 100%;\n" +
        "    max-width: 690px;\n" +
        "    margin-top: -120px;\n" +
        "    padding: 20px 55px;\n" +
        "  }\n" +
        "\n" +
        "  body {\n" +
        "    margin: 0 0;\n" +
        "    padding: 0 0;\n" +
        "  }\n" +
        "\n" +
        "  .box img {\n" +
        "    max-width: 690px;\n" +
        "  }\n" +
        "\n" +
        "</style>\n" +
        "<body>\n" +
        "<img src=\"https://qk-cosole-1309456451.cos.ap-hongkong.myqcloud.com/bg/head.png\" alt=\"\"/>\n" +
        "<div class=\"box\">";
    String EXPORT_TEMPLATE_SUFFIX = "</div>\n" +
        "<img src=\"https://qk-cosole-1309456451.cos.ap-hongkong.myqcloud.com/bg/footer.png\" alt=\"\"/>\n" +
        "</body>\n" +
        "</html>";
}
