package com.example.webqrcreateapp.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class QRCodeService {

    /**
     * テキストをQRコード画像として指定パスにPNG保存するメソッド
     *
     * @param text QRコードに変換する文字列（URLなど）
     * @param width 画像の幅（ピクセル）
     * @param height 画像の高さ（ピクセル）
     * @param filePath 画像ファイルの保存パス
     * @throws Exception 例外発生時にスロー
     */
    public void generateQRCodeImageToFile(String text, int width, int height, String filePath) throws Exception {
        // QRコード生成オプションを設定（文字コード・誤り訂正レベル・余白）
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name()); // UTF-8の文字コード指定
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);      // 中レベルの誤り訂正
        hints.put(EncodeHintType.MARGIN, 1);                                     // 周囲の余白（最小限）

        // ZXingのMultiFormatWriterがテキストをQRコードに変換しBitMatrix（2Dビットマップ）を生成
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        // BitMatrixを画像（BufferedImage）に変換
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // 画像を指定ファイルパスにPNG形式で保存
        File outputFile = new File(filePath);
        
        // 親ディレクトリがなければ作成する
        File parentDir = outputFile.getParentFile();
        if (!parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                throw new IOException("ディレクトリの作成に失敗しました: " + parentDir.getAbsolutePath());
            }
        }
        
        ImageIO.write(image, "PNG", outputFile);
    }
}