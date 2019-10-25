


import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class test {

//    public static void main(String[] args) throws Exception {
//        List<String> warnings = new ArrayList<String>();
//        boolean overwrite = true;
//        File configFile = new File("mbg.xml");
//        ConfigurationParser cp = new ConfigurationParser(warnings);
//        Configuration config = cp.parseConfiguration(configFile);
//        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
//        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
//        myBatisGenerator.generate(null);
//    }
public static void main(String[] args) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date parse = sdf.parse("1999-01-01");
    System.out.println(parse);
    System.out.println(sdf.format(parse));

}
    @Test
    public void mysql() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://140.143.73.74:3306/formu", "root", "123");
        Statement statement = connection.createStatement();
        String[] names = {"蒋溪蓝", "文秀琴", "黎丽玉", "潘鸣玉", "蒋七锦", "顾笑妍", "蔡若彤", "顾清茹", "吴忆灵", "汤慈心", "龙育梅", "吕易蓉", "任半梦", "彭子爱", "沈忆香", "蒋金文", "朱海萍", "贺绮艳", "张思洁", "萧小之", "陈忆彤", "李田田", "周可儿", "孔天荷", "傅怜珊", "秦清涵", "康昭昭", "郑凡旋", "侯希蓉", "钱丝祺"};
        String[] pys = {"jiangxilan", "wenxiuqin", "liliyu", "panmingyu", "jiangqijin", "guxiaoyan", "cairuotong", "guqingru", "wuyiling", "tangcixin", "longyumei", "lüyirong", "renbanmeng", "pengziai", "shenyixiang", "jiangjinwen", "zhuhaiping", "heqiyan", "zhangsijie", "xiaoxiaozhi", "chenyitong", "litiantian", "zhoukeer", "kongtianhe", "fulianshan", "qinqinghan", "kangzhaozhao", "zhengfanxuan", "houxirong", "qiansiqi"};
        for (int i = 0; i < 30; i++) {
            String sql = "INSERT INTO `formu`.`formu_user`" +
                    " (`user_name`, `passwd`, `friends`, `pho`, `article_good`, `email`, `yiban`, `account`)" +
                    " VALUES ('" + names[i] + "', " +
                    "'202cb962ac59075b964b07152d234b70', " +
                    "'', " +
                    "'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570366405712&di=3065c180a67931b0acf277316abfd4c4&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F015d9b56ac5a2d6ac7256cb0ece272.png', " +
                    "'', " +
                    "'" + pys[i] + "@qq.com', " +
                    "'', " +
                    "'" + pys[i] + "')";
            statement.execute(sql);
        }
    }

    @Test
    public void mysql2() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://140.143.73.74:3306/formu", "root", "123");
        Statement statement = connection.createStatement();
        for (int i = 3; i < 32; i++) {

            String sql = "INSERT INTO `formu`.`formu_article` " +
                    "(`category_id`, `user_id`, `title`, `message`, `images`, `good_num`, `create_time`) " +
                    "VALUES (" + (new Random().nextInt(4) + 1) + ", " +
                    "" + (new Random().nextInt(37) + 1) + ", " +
                    "'标题', '内容', '图片', 0, " +
                    "'2019-" + (new Random().nextInt(9) + 1) + "-" + (new Random().nextInt(28) + 1) + " 15:08:03')";
            statement.execute(sql);
        }

    }

    @Test
    public void mysql3() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://140.143.73.74:3306/formu", "root", "123");
        Statement statement =null;
        statement     = connection.createStatement();

        String sql = "SELECT  images FROM formu_article order by article_id";
        ResultSet rs = statement.executeQuery(sql);
        String url;
        List<String> list = new ArrayList<>();
        // 展开结果集数据库
        while (rs.next()) {
            // 通过字段检索
            list.add(rs.getString("images"));
        }

        // 完成后关闭
        rs.close();
        statement.close();
        for (int i = 1; i < 32; i++) {
            statement = connection.createStatement();
            url = StringUtils.substringBefore(list.get(i-1), "\n") + "?";
            sql = "UPDATE formu_article  SET images ='"+url+"' WHERE article_id = " + i;
            statement.executeUpdate(sql);
            System.out.println(i+"  "+url);
            statement.close();
        }

    }
}
