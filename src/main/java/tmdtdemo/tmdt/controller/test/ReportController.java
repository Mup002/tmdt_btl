package tmdtdemo.tmdt.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tmdtdemo.tmdt.service.ReportService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    @PostMapping("/exportReport")
    public String exportReport(
            @RequestParam("file") MultipartFile file,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam int totalOrders,
            @RequestParam double totalRevenue) {

        // Tạo thư mục tạm thời để lưu file upload
        Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"), "uploads");
        if (!Files.exists(tempDir)) {
            try {
                Files.createDirectories(tempDir);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error creating temporary directory";
            }
        }
        // Đường dẫn file tạm thời
        Path tempFilePath = tempDir.resolve(file.getOriginalFilename());
        try {
            // Lưu file tạm thời

            file.transferTo(tempFilePath.toFile());

            String originalFileName = file.getOriginalFilename();
            String newFileName = originalFileName.substring(0,originalFileName.lastIndexOf('.'))
                    +"_"
                    + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                    +originalFileName.substring(originalFileName.lastIndexOf('.'));

            //duon dan file dau ra
            Path outputPath = tempDir.resolve(newFileName);
            // Gọi service để tạo báo cáo
            reportService.exportReport(tempFilePath.toString(), outputPath.toString(), startDate, endDate, totalOrders, totalRevenue);
            return outputPath.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error exporting report";
        }finally {
            // Xóa file tạm sau khi xử lý xong
            try {
                Files.deleteIfExists(tempFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
