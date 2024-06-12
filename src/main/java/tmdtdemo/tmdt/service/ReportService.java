package tmdtdemo.tmdt.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
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
}
