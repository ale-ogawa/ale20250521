package com.example.webQEapp.controller;

import java.io.ByteArrayInputStream;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.webQEapp.service.QRCodeService;

@Controller
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    /**
     * GET /generateQRCode?url=XXX のリクエストでQRコード画像を生成して返す
     * @param url QRコード化したいURLなどの文字列
     * @param response HTTPレスポンスに画像を出力
     * @throws Exception 生成処理での例外
     */
    @GetMapping("/generateQRCode")
    public void generateQRCode(@RequestParam("url") String url, HttpServletResponse response) throws Exception {
        int size = 300; // 生成するQRコードのサイズ（幅と高さ）

        // URL文字列からQRコード画像データを取得
        byte[] qrImage = qrCodeService.generateQRCodeImage(url, size, size);

        // レスポンスのContent-TypeをPNG画像に設定
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        // 画像のバイト配列をレスポンスに書き出す
        IOUtils.copy(new ByteArrayInputStream(qrImage), response.getOutputStream());
    }
}