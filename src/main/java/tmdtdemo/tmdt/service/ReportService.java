package tmdtdemo.tmdt.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tmdtdemo.tmdt.dto.request.ReportRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {
    public void exportReport(String inputFilePath, String outputFilePath, String startDate, String endDate, int totalOrders, double totalRevenue) throws IOException {
        // Đọc file Word mẫu
        try (FileInputStream fis = new FileInputStream(inputFilePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Cập nhật thông tin trong file Word
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        text = text.replace("${date}", LocalDate.now().toString());
                        text = text.replace("${startDate}", startDate);
                        text = text.replace("${endDate}", endDate);
                        text = text.replace("${totalOrders}", String.valueOf(totalOrders));
                        text = text.replace("${totalRevenue}", String.valueOf(totalRevenue));
                        run.setText(text, 0);
                    }
                }
            }
            File outputFile = new File(outputFilePath);
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();  // Tạo thư mục nếu chưa tồn tại
                outputFile.createNewFile();  // Tạo file nếu chưa tồn tại
            }
            // Ghi file Word sau khi cập nhật thông tin
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                document.write(fos);
            }
        }
    }

    public String exportReportDemo(MultipartFile file, int day, int month, int year, String username, String date, Long num_of_orders, Long profit, Long revenue){
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
            //--------- tạo báo cáo----------------//
            // Đọc file Word mẫu
            try (FileInputStream fis = new FileInputStream(tempFilePath.toString());
                 XWPFDocument document = new XWPFDocument(fis)) {

                // Cập nhật thông tin trong file Word
                List<XWPFParagraph> paragraphs = document.getParagraphs();
                for (XWPFParagraph paragraph : paragraphs) {
                    for (XWPFRun run : paragraph.getRuns()) {
                        String text = run.getText(0);
                        if (text != null) {
                            text = text.replace("${username}",username);
                            text = text.replace("${date}", date);
                            text = text.replace("${day}", String.valueOf(day));
                            text = text.replace("${month}", String.valueOf(month));
                            text = text.replace("${year}", String.valueOf(year));
                            text = text.replace("${num_of_orders}", String.valueOf(num_of_orders));
                            text = text.replace("${totalRevenue}", String.valueOf(revenue));
                            text = text.replace("${profit}",String.valueOf(profit));
                            run.setText(text, 0);
                        }
                    }
                }
                File outputFile = new File(outputPath.toString());
                if (!outputFile.exists()) {
                    outputFile.getParentFile().mkdirs();  // Tạo thư mục nếu chưa tồn tại
                    outputFile.createNewFile();  // Tạo file nếu chưa tồn tại
                }
                // Ghi file Word sau khi cập nhật thông tin
                try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                    document.write(fos);
                }
            }
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
