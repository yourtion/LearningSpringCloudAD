package com.yourtion.springcloud.ad;


import com.yourtion.springcloud.ad.service.DumpDataService;
import lombok.var;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yourtion
 */
@SpringBootApplication
public class ExportApplication {


    public static void main(String[] args) {
        var context = SpringApplication.run(ExportApplication.class);

        // 执行导出任务
        context.getBean(DumpDataService.class).dumpAdTableData();

        // 退出
        context.close();
    }
}
