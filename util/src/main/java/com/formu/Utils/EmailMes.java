package com.formu.Utils;

/**
 * Created by weiqiang
 */
public class EmailMes {

    public static String emailMes(String code,boolean isregister){
        String mmes = isregister?"注册服务":"找回密码服务";
        String mes = "<div class=\"qmbox qm_con_body_content qqmail_webmail_only\" id=\"mailContentContainer\" style=\"\">\n<br/><br/><br/><br/>" +
                "    <style>\n" +
                "        .qmbox body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background: #fff;\n" +
                "            font-size: 14px;\n" +
                "            line-height: 24px;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox div, .qmbox p, .qmbox span, .qmbox img {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox img {\n" +
                "            border: none;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .contaner {\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .title {\n" +
                "            margin: 0 auto;\n" +
                "            background: #CCC repeat-x;\n" +
                "            height: 30px;\n" +
                "            text-align: center;\n" +
                "            font-weight: bold;\n" +
                "            padding-top: 12px;\n" +
                "            font-size: 16px;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .content {\n" +
                "            margin: 4px;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .biaoti {\n" +
                "            padding: 6px;\n" +
                "            color: #000;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .xtop, .qmbox .xbottom {\n" +
                "            display: block;\n" +
                "            font-size: 1px;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .xb1, .qmbox .xb2, .qmbox .xb3, .qmbox .xb4 {\n" +
                "            display: block;\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .xb1, .qmbox .xb2, .qmbox .xb3 {\n" +
                "            height: 1px;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .xb2, .qmbox .xb3, .qmbox .xb4 {\n" +
                "            border-left: 1px solid #BCBCBC;\n" +
                "            border-right: 1px solid #BCBCBC;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .xb1 {\n" +
                "            margin: 0 5px;\n" +
                "            background: #BCBCBC;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .xb2 {\n" +
                "            margin: 0 3px;\n" +
                "            border-width: 0 2px;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .xb3 {\n" +
                "            margin: 0 2px;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .xb4 {\n" +
                "            height: 2px;\n" +
                "            margin: 0 1px;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .xboxcontent {\n" +
                "            display: block;\n" +
                "            border: 0 solid #BCBCBC;\n" +
                "            border-width: 0 1px;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .line {\n" +
                "            margin-top: 6px;\n" +
                "            border-top: 1px dashed #B9B9B9;\n" +
                "            padding: 4px;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .neirong {\n" +
                "            padding: 6px;\n" +
                "            color: #666666;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .foot {\n" +
                "            padding: 6px;\n" +
                "            color: #777;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .font_darkblue {\n" +
                "            color: #006699;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .font_lightblue {\n" +
                "            color: #008BD1;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        .qmbox .font_gray {\n" +
                "            color: #888;\n" +
                "            font-size: 12px;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <div class=\"contaner\">\n" +
                "        <div class=\"title\">Y-Wall</div>\n" +
                "        <div class=\"content\">\n" +
                "            <p class=\"biaoti\"><b>亲爱的用户，你好！</b></p>\n" +
                "            <b class=\"xtop\"><b class=\"xb1\"></b><b class=\"xb2\"></b><b class=\"xb3\"></b><b class=\"xb4\"></b></b>\n" +
                "            <div class=\"xboxcontent\">\n" +
                "                <div class=\"neirong\">\n" +
                "                    <p><b>您正在使用Y—Wall"+ mmes+":</b></p>\n" +
                "                    <p><b>您的验证码为:</b><span class=\"font_lightblue\"><span id=\"yzm\" data=\""+code+"\"\n" +
                "                                                                             onclick=\"return false;\" t=\"7\"\n" +
                "                                                                             style=\"border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1; position: static;\">"+code+"</span></span><br><span\n" +
                "                            class=\"font_gray\">(请输入该验证码完成注册服务，验证码10分钟内有效！)</span></p>\n" +
                "                    <div class=\"line\">如果你未申请该服务，请忽略该邮件。</div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <b class=\"xbottom\"><b class=\"xb4\"></b><b class=\"xb3\"></b><b class=\"xb2\"></b><b class=\"xb1\"></b></b>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <style>\n" +
                "        .qmbox style, .qmbox script, .qmbox head, .qmbox link, .qmbox meta {\n" +
                "            display: none !important;\n" +
                "        }\n" +
                "    </style>\n" +
                "<br/><br/><br/><br/></div>";
        return mes;
    }


}