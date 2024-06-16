package tmdtdemo.tmdt.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tmdtdemo.tmdt.dto.request.ReportRequest;
import tmdtdemo.tmdt.repository.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ResultRevenueRepo resultRevenueRepo;
    private final OrderRepository orderRepository;
    private final ProductSpuRepo productSpuRepo;
    private final OrderSkuRepo orderSkuRepo;
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

    public String exportReportDemo(int month, int year, String username) {
        String filePath = "D:\\BCTK.docx";
        // Tạo thư mục tạm thời để lưu file đầu ra
        Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"), "uploads");
        if (!Files.exists(tempDir)) {
            try {
                Files.createDirectories(tempDir);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error creating temporary directory";
            }
        }

        // Lấy tên file gốc từ đường dẫn file đã cung cấp
        Path sourceFilePath = Paths.get(filePath);
        String originalFileName = sourceFilePath.getFileName().toString();

        // Tạo tên file mới
        String newFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'))
                + "_"
                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + originalFileName.substring(originalFileName.lastIndexOf('.'));

        // Đường dẫn file đầu ra
        Path outputPath = tempDir.resolve(newFileName);

        //--------- tạo báo cáo----------------//
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis)) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormatter.format(new Date());
            // Cập nhật thông tin trong file Word
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        text = text.replace("${username}", username);
                        text = text.replace("${date}",formattedDate);
                        StringBuilder sb= new StringBuilder();
                        sb.append("01/");
                        if(month < 10){
                            sb.append("0");
                        }
                        sb.append(month);
                        sb.append("/");
                        sb.append(year);
                        text = text.replace("${startDate}", sb.toString());
                        text = text.replace("${endDate}", formattedDate);
                        text = text.replace("${totalOrders}",String.valueOf(orderRepository.countOrdersByMonth(month,year)));
                        text = text.replace("${totalRevenue}", String.valueOf(resultRevenueRepo.findResultRevenueByResultMonth(month, year).getRevenue()));
                        text = text.replace("${profit}", String.valueOf(resultRevenueRepo.findResultRevenueByResultMonth(month, year).getProfit()));
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
        } catch (IOException e) {
            e.printStackTrace();
            return "Error exporting report";
        }

        return outputPath.toString();
    }
    public List<String> test(int month,int year){
        return productSpuRepo.findTop10ProductSpuNamesByTotalQuantityAndDate(month, year);
    }

//    public Long test2(Long productSpuId, int month, int year){
//        return orderSkuRepo.sumTotalQuantityByProductSpuIdByDate(productSpuId,month,year);
//    }

}
