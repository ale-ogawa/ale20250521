package com.example.webQEapp.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * QRコード生成
 */
public class SimpleQRCodeGenerator {
    public static void main(String[] args) {
        // QRコードにしたい文字列（例：パラメータ付きURL）
        String text = "https://example.com?param=value";

        // 出力ファイルのパス
        String filePath = "qrcode.png";

        // QRコード画像のサイズ（ピクセル）
        int width = 300;
        int height = 300;

        // エンコーディングや誤り訂正レベルの設定
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");             // 文字コード
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // 誤り訂正レベル（中）
        hints.put(EncodeHintType.MARGIN, 1);                          // 余白

        try {
            // QRコードのビットマトリクス生成
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

            // BitMatrixを画像に変換
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // 画像ファイルとして保存（PNG形式）
            ImageIO.write(image, "PNG", new File(filePath));

            System.out.println("QRコード画像を生成しました。ファイル名：" + filePath);
        } catch (WriterException | IOException e) {
            System.err.println("QRコード生成に失敗しました: " + e.getMessage());
        }
    }
}
