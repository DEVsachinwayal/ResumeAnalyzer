package com.resume.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class ResumeParserUtil {

    public static String extractText(MultipartFile file)
            throws IOException {

        String fileName =
                file.getOriginalFilename();

        if (fileName == null) {
            throw new RuntimeException(
                    "Invalid File");
        }

        fileName = fileName.toLowerCase();

        // PDF

        if (fileName.endsWith(".pdf")) {

            try (PDDocument document =
                         PDDocument.load(
                                 file.getInputStream())) {

                PDFTextStripper stripper =
                        new PDFTextStripper();

                return stripper.getText(document);
            }
        }

        // DOCX

        if (fileName.endsWith(".docx")) {

            try (XWPFDocument document =
                         new XWPFDocument(
                                 file.getInputStream())) {

                XWPFWordExtractor extractor =
                        new XWPFWordExtractor(
                                document);

                return extractor.getText();
            }
        }

        // IMAGE OCR

        if (fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg")
                || fileName.endsWith(".png")) {

            try {

                BufferedImage image =
                        ImageIO.read(
                                file.getInputStream());

                ITesseract tesseract =
                        new Tesseract();
                /*
                 * Change path according to your PC
                 */
                tesseract.setDatapath(
                        "C:\\Program Files\\Tesseract-OCR\\tessdata"
                );

               

                

                return tesseract.doOCR(image);

            } catch (Exception e) {

                throw new RuntimeException(
                        "OCR Failed: "
                                + e.getMessage());
            }
        }

        throw new RuntimeException(
                "Unsupported File Format");
    }
}