package com.example.webQEapp.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
     * 入力されたテキスト（URLなど）からQRコードのPNG画像をバイト配列で生成するメソッド
     *
     * @param text  QRコード化したい文字列
     * @param width 生成するQRコード画像の幅（ピクセル）
     * @param height 生成するQRコード画像の高さ（ピクセル）
     * @return PNG画像のバイト配列
     * @throws Exception 生成中の例外発生時
     */
    public byte[] generateQRCodeImage(String text, int width, int height) throws Exception {
        // QRコード生成のオプション設定
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 文字コードはUTF-8を使用
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        // エラー訂正レベルは'M'（中程度）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // マージン（QRコード囲みの余白）は少なめに設定
        hints.put(EncodeHintType.MARGIN, 1);

        // ZXingのMultiFormatWriterでBitMatrix生成（QRコード画像の元となるビットマップ）
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        // BitMatrixをBufferedImageに変換（画像化）
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // BufferedImageをPNG形式のバイト配列に変換
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "PNG", baos);
            return baos.toByteArray();  // バイト配列を返す
        }
    }
}